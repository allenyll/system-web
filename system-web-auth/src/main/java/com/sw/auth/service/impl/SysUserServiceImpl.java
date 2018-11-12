package com.sw.auth.service.impl;

import com.sw.auth.mapper.SysUserMapper;
import com.sw.auth.service.ISysUserService;
import com.sw.common.entity.SysUser;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author yu.leilei
 * @since 2018-06-12
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService {
	
}
