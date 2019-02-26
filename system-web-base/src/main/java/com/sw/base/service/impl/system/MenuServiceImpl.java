package com.sw.base.service.impl.system;

import com.sw.base.mapper.system.MenuMapper;
import com.sw.common.entity.system.Menu;
import com.sw.common.service.BaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("menuService")
@Transactional(rollbackFor = Exception.class)
public class MenuServiceImpl extends BaseService<MenuMapper, Menu>{

}
