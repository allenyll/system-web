package com.sw.base.mapper;

import com.sw.common.entity.SysRoleMenu;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
  * 权限菜单关系 Mapper 接口
 * </p>
 *
 * @author yu.leilei
 * @since 2018-11-13
 */
@Repository("sysRoleMenuMapper")
public interface SysRoleMenuMapper extends BaseMapper<SysRoleMenu> {

}