package com.sw.base.mapper;

import com.sw.common.entity.SysRole;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
  * 权限表 Mapper 接口
 * </p>
 *
 * @author yu.leilei
 * @since 2018-11-13
 */
@Repository("roleMapper")
public interface SysRoleMapper extends BaseMapper<SysRole> {

}