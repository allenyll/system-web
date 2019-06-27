package com.sw.base.controller.goods;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.sw.cache.constants.BaseConstants;
import com.sw.cache.util.DataResponse;
import com.sw.common.controller.BaseController;
import com.sw.base.service.impl.goods.GoodsServiceImpl;
import com.sw.common.entity.goods.Goods;
import com.sw.common.entity.goods.GoodsParam;
import com.sw.common.entity.system.File;
import com.sw.common.util.CollectionUtil;
import com.sw.common.util.DateUtil;
import com.sw.common.util.MapUtil;
import com.sw.file.service.impl.FileServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/system-web/goods")
public class GoodsController extends BaseController<GoodsServiceImpl,Goods> {

    private static final Logger LOGGER = LoggerFactory.getLogger(GoodsController.class);

    @Autowired
    FileServiceImpl fileService;

    @Autowired
    GoodsServiceImpl goodsService;

    @Override
    @ResponseBody
    @RequestMapping(value = "page", method = RequestMethod.GET)
    public DataResponse page(@RequestParam Map<String, Object> params){
        Map<String, Object> result = new HashMap<>();

        LOGGER.info("传入参数=============" + params);
        DataResponse dataResponse = super.page(params);
        List<Goods> goodsList = (List<Goods>) dataResponse.get("list");
        if(CollectionUtil.isNotEmpty(goodsList)){
            for(Goods goods:goodsList){
                setFile(goods);
            }
        }
        result.put("total", dataResponse.get("total"));
        result.put("list", goodsList);
        return DataResponse.success(result);
    }

    @ResponseBody
    @RequestMapping(value = "/createGoods", method = RequestMethod.POST)
    public DataResponse createGoods(@RequestBody GoodsParam goodsParam) {
        LOGGER.debug("保存参数：{}", goodsParam);
        Map<String, Object> result = new HashMap<>();

        Goods goods = goodsParam;

        DataResponse dataResponse = super.add(goods);
        // 商品添加失败
        if(dataResponse.get("code").equals(BaseConstants.FAIL)){
            return dataResponse;
        }

        try {
            int count = goodsService.createGoods(goodsParam);
            result.put("count", count);
        } catch (Exception e) {
            LOGGER.error("创建商品失败");
            e.printStackTrace();
        }

        return DataResponse.success(result);
    }

    @ResponseBody
    @RequestMapping(value = "/updateGoods/{id}", method = RequestMethod.POST)
    public DataResponse updateGoods(@PathVariable String id, @RequestBody GoodsParam goodsParam) {
        LOGGER.debug("更新参数：{}", goodsParam);
        Map<String, Object> result = new HashMap<>();

        Goods goods = goodsParam;

        DataResponse dataResponse = super.update(goods);
        // 商品更新失败
        if(dataResponse.get("code").equals(BaseConstants.FAIL)){
            return dataResponse;
        }

        try {
            int count = goodsService.updateGoods(goodsParam);
            result.put("count", count);
        } catch (Exception e) {
            LOGGER.error("创建商品失败");
            e.printStackTrace();
        }

        return DataResponse.success(result);
    }

    @ResponseBody
    @RequestMapping(value = "/updateLabel", method = RequestMethod.POST)
    public DataResponse updateLabel(@RequestBody Map<String, Object> params) {
        LOGGER.debug("保存参数：{}", params);
        Map<String, Object> result = new HashMap<>();
        String goodsId = MapUtil.getString(params, "id");
        String label = MapUtil.getString(params, "label");
        String status = MapUtil.getString(params, "status");

        DataResponse dataResponse = super.get(goodsId);

        Goods goods = (Goods) dataResponse.get("obj");

        if(goods == null){
            return DataResponse.fail("更新失败, 商品不存在");
        }
        if("isUsed".equals(label)){
            goods.setIsUsed(status);
        }else if("isRecom".equals(label)){
            goods.setIsRecom(status);
        }else if("isSpec".equals(label)){
            goods.setIsSpec(status);
        }else if("isBest".equals(label)){
            goods.setIsBest(status);
        }else if("isHot".equals(label)){
            goods.setIsHot(status);
        }else if("isNew".equals(label)){
            goods.setIsNew(status);
        }

        String userId = redisService.get("userId");

        goods.setUpdateTime(DateUtil.getCurrentDateTime());
        goods.setUpdateUser(userId);
        boolean flag = goodsService.updateById(goods);

        if(!flag){
            return DataResponse.fail("更新商品状态失败");
        }

        return DataResponse.success(result);
    }

    @ResponseBody
    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public DataResponse get(@PathVariable String id) {

        Map<String, Object> result = null;

        DataResponse dataResponse = super.get(id);
        Goods goods = (Goods) dataResponse.get("obj");

        if(goods == null) {
            return  DataResponse.fail("获取商品失败");
        }

        try {
            result = goodsService.getGoodsInfo(goods);
        } catch (Exception e) {
            LOGGER.error("赋值异常");
            e.printStackTrace();
        }

        return DataResponse.success(result);
    }

    @ResponseBody
    @RequestMapping(value = "/getGoods", method = RequestMethod.POST)
    public DataResponse getGoods(@RequestBody Map<String, Object> params){
        Map<String, Object> result = new HashMap<>();

        page = MapUtil.getIntValue(params, "page");
        limit = MapUtil.getIntValue(params, "limit");
        String id = MapUtil.getMapValue(params, "categoryId");
        EntityWrapper<Goods> wrapper = new EntityWrapper<>();
        wrapper.eq("IS_DELETE", 0);
        wrapper.eq("FK_CATEGORY_ID", id);

        int total = goodsService.selectCount(wrapper);
        Page<Goods> pages = service.selectPage(new Page<>(page, limit), wrapper);
        List<Goods> list = pages.getRecords();
        if(CollectionUtil.isNotEmpty(list)){
            for (Goods goods: list){
               setFile(goods);
            }
        }

        if(total%limit == 0){
            totalPage = total/limit;
        }else{
            totalPage = total/limit + 1;
        }

        result.put("currentPage", page);
        result.put("totalPage", totalPage);
        result.put("goods", list);

        return DataResponse.success(result);
    }

    @ResponseBody
    @RequestMapping(value = "/getGoodsInfo/{id}", method = RequestMethod.POST)
    public DataResponse getGoodsInfo(@PathVariable String id){
        Map<String, Object> result = new HashMap<>();
        DataResponse data = super.get(id);

        Goods goods = (Goods) data.get("obj");
        if(goods == null){
            return DataResponse.fail("商品不存在");
        }

        setFile(goods);

        try {
            result = goodsService.getGoodsInfo(goods);
        } catch (Exception e) {
            LOGGER.error("赋值异常");
            e.printStackTrace();
        }

        return DataResponse.success(result);
    }

    private void setFile(Goods goods) {
        EntityWrapper<File> fileEntityWrapper = new EntityWrapper<>();
        fileEntityWrapper.eq("IS_DELETE", 0);
        fileEntityWrapper.eq("FILE_TYPE", "SW1801");
        fileEntityWrapper.eq("FK_ID", goods.getPkGoodsId());
        List<File> sysFiles = fileService.selectList(fileEntityWrapper);
        if(CollectionUtil.isNotEmpty(sysFiles)){
            goods.setFileList(sysFiles);
            goods.setFileUrl(sysFiles.get(0).getFileUrl());
        }else{
            goods.setFileUrl(DEFAULT_URL);
        }
    }

}
