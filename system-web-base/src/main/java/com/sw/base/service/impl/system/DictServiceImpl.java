package com.sw.base.service.impl.system;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.sw.base.mapper.system.DictMapper;
import com.sw.common.service.BaseService;
import com.sw.common.entity.system.Dict;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * <p>
 * 字典表
 服务实现类
 * </p>
 *
 * @author yu.leilei
 * @since 2019-02-15
 */
@Service("dictService")
public class DictServiceImpl extends BaseService<DictMapper, Dict> {

   @Autowired
    DictMapper dictMapper;

    public List<Dict> selectList(EntityWrapper<Dict> wrapper) {
        return dictMapper.selectList(wrapper);
    }
}
