package com.sw.base.service.impl.system;

import com.sw.base.mapper.system.RoleMapper;
import com.sw.common.service.BaseService;
import com.sw.common.entity.system.Role;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("roleService")
@Transactional(rollbackFor = Exception.class)
public class RoleServiceImpl extends BaseService<RoleMapper, Role>{

}
