package com.sw.base.service.impl;

import com.sw.common.entity.SysUserRole;
import com.sw.base.mapper.SysUserRoleMapper;
import com.sw.base.service.ISysUserRoleService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户权限表 服务实现类
 * </p>
 *
 * @author yu.leilei
 * @since 2018-11-13
 */
@Service("userRoleService")
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleMapper, SysUserRole> implements ISysUserRoleService {
	
}
