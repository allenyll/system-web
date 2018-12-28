package com.sw.base.mapper;

import com.sw.common.entity.SysUserRole;
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