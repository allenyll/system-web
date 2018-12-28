package com.sw.base.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.sw.base.service.ISysRoleService;
import com.sw.base.service.ISysUserRoleService;
import com.sw.common.entity.SysRole;
import com.sw.common.entity.SysUserRole;
import com.sw.common.util.DataResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 用户权限表 前端控制器
 * </p>
 *
 * @author yu.leilei
 * @since 2018-11-13
 */
@Controller
@RequestMapping("/sysUserRole")
public class SysUserRoleController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SysUserRoleController.class);

    @Autowired
    ISysUserRoleService userRoleService;

    @Autowired
    ISysRoleService roleService;

}
