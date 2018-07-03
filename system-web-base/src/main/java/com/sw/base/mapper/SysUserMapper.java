package com.sw.base.mapper;

import com.sw.base.entity.SysUser;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
  * 用户表 Mapper 接口
 * </p>
 *
 * @author yu.leilei
 * @since 2018-06-12
 */
@Repository("sysUserMapper")
public interface SysUserMapper extends BaseMapper<SysUser> {

}