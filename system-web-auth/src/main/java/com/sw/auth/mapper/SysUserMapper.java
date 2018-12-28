package com.sw.auth.mapper;

import com.sw.common.entity.SysUser;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

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

    /**
     * 根据用户id获取该用户所在角色下的菜单
     * @param param
     * @return
     */
    List<Map<String, Object>> getUserRoleMenuList(Map<String, String> param);
}