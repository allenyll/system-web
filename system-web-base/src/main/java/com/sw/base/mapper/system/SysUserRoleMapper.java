package com.sw.base.mapper.system;

import com.sw.common.entity.system.SysUserRole;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
  * 用户权限表 Mapper 接口
 * </p>
 *
 * @author yu.leilei
 * @since 2018-11-13
 */
@Repository("sysUserRoleMapper")
public interface SysUserRoleMapper extends BaseMapper<SysUserRole> {

}