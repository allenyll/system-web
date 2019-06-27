package com.sw.base.mapper.goods;

import com.sw.common.entity.goods.Specs;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.Map;

/**
 * 规格表
 *
 * @author allenyll
 * @email 806141743@qq.com
 * @date 2019-05-13 16:08:41
 */
public interface SpecsMapper extends BaseMapper<Specs> {

    /**
     * 根据规格选项获取规格值
     * @param id
     * @return
     */
    Map<String, Object> getSpecs(String id);
}
