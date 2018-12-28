package com.sw.auth.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.sw.common.entity.SysUser;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author yu.leilei
 * @since 2018-06-12
 */
public interface ISysUserService extends IService<SysUser> {

    /**
     * 根据用户id获取该用户所在角色下的菜单
     * @param param
     * @return
     */
    List<Map<String, Object>> getUserRoleMenuList(Map<String, String> param);

    /**
     * 分页查询用户
     * @param userWrapper
     * @param page
     * @param limit
     * @return
     */
    List<SysUser> getUserListByPage(EntityWrapper<SysUser> userWrapper, int page, int limit);
}
