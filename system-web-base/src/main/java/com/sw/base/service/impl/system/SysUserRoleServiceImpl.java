package com.sw.base.service.impl.system;

import com.baomidou.mybatisplus.service.IService;
import com.sw.base.service.system.ISysUserRoleService;
import com.sw.common.entity.system.SysUserRole;
import com.sw.base.mapper.system.SysUserRoleMapper;
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
