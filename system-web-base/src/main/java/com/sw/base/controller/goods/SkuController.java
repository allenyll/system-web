package com.sw.base.controller.goods;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.sw.base.service.impl.goods.SpecsServiceImpl;
import com.sw.cache.util.DataResponse;
import com.sw.common.controller.BaseController;
import com.sw.base.service.impl.goods.SkuServiceImpl;
import com.sw.common.entity.Entity;
import com.sw.common.entity.goods.Sku;
import com.sw.common.util.CollectionUtil;
import com.sw.common.util.MapUtil;
import com.sw.common.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/system-web/sku")
public class SkuController extends BaseController<SkuServiceImpl,Sku> {

    private static final Logger LOGGER = LoggerFactory.getLogger(SkuController.class);

    @Autowired
    SkuServiceImpl skuService;

    @Autowired
    SpecsServiceImpl specsService;

    @ResponseBody
    @RequestMapping(value = "/getSkuStockList/{id}", method = RequestMethod.POST)
    private DataResponse getSkuStockList(@PathVariable String id, @RequestBody Map<String, Object> params) {
        Map<String, Object> result = new HashMap<>();

        String keyword = MapUtil.getString(params, "keyword");

        EntityWrapper<Sku> wrapper = new EntityWrapper<>();
        wrapper.eq("FK_GOODS_ID", id);
        wrapper.eq("IS_DELETE", 0);
        if(StringUtil.isNotEmpty(keyword)){
            wrapper.like("SKU_CODE", keyword);
        }

        List<Sku> list = skuService.selectList(wrapper);
        if(CollectionUtil.isNotEmpty(list)) {
            result = dealSkuSpec(list);
        }

        return DataResponse.success(result);
    }

    @ResponseBody
    @RequestMapping(value = "/updateSkuStock/{id}", method = RequestMethod.POST)
    private DataResponse updateSkuStock(@PathVariable String id, @RequestBody List<Sku> stockList) {
        Map<String, Object> result = new HashMap<>();

        if(CollectionUtil.isNotEmpty(stockList)) {
            for(Sku sku:stockList){
                skuService.updateById(sku);
            }
        }

        return DataResponse.success(result);
    }

    /**
     * 处理SKU规格
     * @param list
     */
    private Map<String, Object> dealSkuSpec(List<Sku> list) {
        Map<String, Object> result = new HashMap<>();
        List<String> specList = new ArrayList<>();
        List<Map<String, Object>> specValueList = new ArrayList<>();
        for(Sku sku:list){
            Map<String, Object> map;
            map = sku.toMap();
            String specValue = sku.getSpecValue();
            String[] specValues = specValue.split(";");
            if(specValues.length > 1) {
                for(int i=0; i<specValues.length;i++){
                    String[] split = specValues[i].substring(1, specValues[i].length() - 1).split(",");
                    String _id = split[0];
                    Map<String, Object> spec = specsService.getSpecs(_id);
                    if(spec == null && spec.isEmpty()) {
                        return null;
                    }
                    String name = MapUtil.getString(spec, "specName");
                    if(!specList.contains(name)){
                        specList.add(name);
                    }
                    String _val = split[1];
                    map.put("value"+i, _val);
                }
            }
            specValueList.add(map);
        }
        result.put("specList", specList);
        result.put("stockList", specValueList);
        return result;
    }

    public static void main(String[] args) {
        String s = "[bbc2db0f5953427e9f3ec77f21c5bd0e,白色];[5421b503ca8f460e9cc9498253b68980,37]";


    }

}
