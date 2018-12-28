package com.sw.base.service.impl;

import com.sw.common.entity.SysRoleMenu;
import com.sw.base.mapper.SysRoleMenuMapper;
import com.sw.base.service.ISysRoleMenuService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 权限菜单关系 服务实现类
 * </p>
 *
 * @author yu.leilei
 * @since 2018-11-13
 */
@Service("roleMenuService")
public class SysRoleMenuServiceImpl extends ServiceImpl<SysRoleMenuMapper, SysRoleMenu> implements ISysRoleMenuService {
	
}
