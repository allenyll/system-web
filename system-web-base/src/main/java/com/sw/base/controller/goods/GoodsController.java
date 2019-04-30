package com.sw.base.controller.goods;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.sw.cache.util.DataResponse;
import com.sw.common.controller.BaseController;
import com.sw.base.service.impl.goods.GoodsServiceImpl;
import com.sw.common.entity.goods.Goods;
import com.sw.common.entity.system.File;
import com.sw.common.util.CollectionUtil;
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

    private void setFile(Goods goods) {
        EntityWrapper<File> fileEntityWrapper = new EntityWrapper<>();
        fileEntityWrapper.eq("IS_DELETE", 0);
        fileEntityWrapper.eq("FILE_TYPE", "SW1801");
        fileEntityWrapper.eq("FK_ID", goods.getPkGoodsId());
        List<File> sysFiles = fileService.selectList(fileEntityWrapper);
        if(CollectionUtil.isNotEmpty(sysFiles)){
            goods.setFileUrl(sysFiles.get(0).getFileUrl());
        }else{
            goods.setFileUrl(DEFAULT_URL);
        }
    }

}