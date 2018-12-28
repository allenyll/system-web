package com.sw.base.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.sw.base.service.ISysRoleMenuService;
import com.sw.base.service.ISysRoleService;
import com.sw.base.service.ISysUserRoleService;
import com.sw.common.entity.SysMenu;
import com.sw.common.entity.SysRole;
import com.sw.common.entity.SysRoleMenu;
import com.sw.common.entity.SysUserRole;
import com.sw.common.util.DataResponse;
import com.sw.common.util.DateUtil;
import com.sw.common.util.StringUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 权限表 前端控制器
 * </p>
 *
 * @author yu.leilei
 * @since 2018-11-13
 */
@Controller
@RequestMapping("/role")
public class SysRoleController extends BaseController{

    private static final Logger LOGGER = LoggerFactory.getLogger(SysUserController.class);

    @Autowired
    ISysRoleService roleService;

    @Autowired
    ISysRoleMenuService roleMenuService;

    @Autowired
    ISysUserRoleService userRoleService;

    @ResponseBody
    @RequestMapping(value = "page", method = RequestMethod.GET)
    public DataResponse getRoleList(@RequestParam Map<String, Object> params){
        LOGGER.info("============= {开始调用方法：getRoleList(} =============");
        Map<String, Object> result = new HashMap<>();
        LOGGER.info("传入参数=============" + params);
        page = Integer.parseInt(params.get("page").toString());
        limit = Integer.parseInt(params.get("limit").toString());
        String name = params.get("name").toString();

        EntityWrapper<SysRole> wrapper = new EntityWrapper<>();
        wrapper.eq("IS_DELETE", 0);
        if(StringUtil.isNotEmpty(name)){
            wrapper.like("ROLE_NAME", name);
        }
        int start = (page - 1) * limit;
        int total = roleService.selectCount(wrapper);
        List<SysRole> list = roleService.getRoleListByPage(wrapper, start, limit);
        result.put("total", total);
        result.put("roleList", list);
        LOGGER.info("============= {结束调用方法：getRoleList(} =============");
        return DataResponse.success(result);
    }

    @ResponseBody
    @RequestMapping(value = "getRoleList/{id}", method = RequestMethod.GET)
    public DataResponse getAllRole(@PathVariable String id){
        LOGGER.info("============= {开始调用方法：getAllRole(} =============");
        Map<String, Object> result = new HashMap<>();

        // 获取用户拥有的角色
        EntityWrapper<SysUserRole> wrapper = new EntityWrapper<>();
        wrapper.eq("FK_USER_ID", id);

        SysUserRole user = userRoleService.selectOne(wrapper);
        SysRole role;
        if(user != null){
            String roleId = user.getFkRoleId();
            EntityWrapper<SysRole> roleEntityWrapper = new EntityWrapper<>();
            roleEntityWrapper.eq("IS_DELETE", 0);
            roleEntityWrapper.eq("PK_ROLE_ID", roleId);

            role = roleService.selectOne(roleEntityWrapper);
            result.put("userRole", role);
        }else{
            result.put("userRole", "");
        }

        EntityWrapper<SysRole> userWrapper = new EntityWrapper<>();
        userWrapper.eq("IS_DELETE", 0);
        List<SysRole> roles = roleService.selectList(userWrapper);

        result.put("roleList", roles);

        LOGGER.info("============= {结束调用方法：getAllRole(} =============");
        return DataResponse.success(result);
    }


    @ResponseBody
    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public DataResponse get(@PathVariable String id){
        LOGGER.info("==================开始调用 get ================");
        Map<String, Object> result = new HashMap<>();

        EntityWrapper<SysRole> wrapper = new EntityWrapper<>();
        wrapper.eq("IS_DELETE", 0);
        wrapper.eq("PK_ROLE_ID", id);

        SysRole user = roleService.selectOne(wrapper);

        result.put("sysRole", user);

        LOGGER.info("==================结束调用 get ================");

        return DataResponse.success(result);
    }


    @RequestMapping(value = "addRole", method = RequestMethod.POST)
    @ResponseBody
    public DataResponse addRole(@RequestBody SysRole role){
        LOGGER.info("==================开始调用 addRole ================");
        String userId = redisService.get("userId");

        role.setIsDelete(0);
        role.setAddTime(DateUtil.getCurrentDateTime());
        role.setAddUser(userId);
        role.setUpdateTime(DateUtil.getCurrentDateTime());
        role.setUpdateUser(userId);
        roleService.insert(role);

        LOGGER.info("==================结束调用 addRole ================");
        return DataResponse.success();
    }


    @RequestMapping(value = "{id}",method = RequestMethod.PUT)
    @ResponseBody
    public DataResponse updateRole(@PathVariable String id, @RequestBody SysRole role){
        LOGGER.info("==================开始调用 updateRole ================");
        String userId = redisService.get("userId");
        LOGGER.info("userId" + userId);

        EntityWrapper<SysRole> wrapper = new EntityWrapper<>();
        wrapper.eq("PK_ROLE_ID", id);
        role.setUpdateTime(DateUtil.getCurrentDateTime());
        role.setUpdateUser(userId);
        roleService.update(role, wrapper);

        LOGGER.info("==================结束调用 updateRole ================");
        return DataResponse.success();
    }


    @RequestMapping(value = "{id}",method = RequestMethod.DELETE)
    @ResponseBody
    public DataResponse deleteRole(@PathVariable String id){
        LOGGER.info("==================开始调用 deleteRole ================");

        String userId = redisService.get("userId");
        LOGGER.info("userId" + userId);

        EntityWrapper<SysRole> wrapper = new EntityWrapper<>();
        wrapper.eq("IS_DELETE", 0);
        wrapper.eq("PK_ROLE_ID", id);

        SysRole role = roleService.selectOne(wrapper);

        EntityWrapper<SysRole> delWrapper = new EntityWrapper<>();
        delWrapper.eq("PK_ROLE_ID", id);
        role.setIsDelete(1);
        role.setUpdateTime(DateUtil.getCurrentDateTime());
        role.setUpdateUser(userId);
        roleService.update(role, delWrapper);

        LOGGER.info("=================结束调用 deleteRole ===============");
        return DataResponse.success();
    }

    @RequestMapping(value = "/setMenus",method = RequestMethod.POST)
    @ResponseBody
    public DataResponse setMenus(@RequestBody Map<String, Object> params){

        LOGGER.info("==================开始调用 setMenus ================");
        LOGGER.info("params"+params);
        // 全删全插配置角色菜单
        // 1、先删除所有该角色拥有的菜单权限
        String roleId = params.get("id").toString();
        EntityWrapper<SysRoleMenu> roleMenuEntityWrapper = new EntityWrapper<>();
        roleMenuEntityWrapper.eq("FK_ROLE_ID", roleId);
        roleMenuService.delete(roleMenuEntityWrapper);

        // 2、重新插入选择的菜单权限
        List<SysRoleMenu> list = new ArrayList<>();
        JSONArray jsonArray = JSONArray.fromObject(params.get("ids"));
        if(jsonArray.size() > 0){
            for(int i=0; i<jsonArray.size(); i++){
                String menuId = jsonArray.getString(i);
                SysRoleMenu sysRoleMenu = new SysRoleMenu();
                sysRoleMenu.setFkRoleId(roleId);
                sysRoleMenu.setFkMenuId(menuId);
                sysRoleMenu.setPkRelationId(StringUtil.getUUID32());
                list.add(sysRoleMenu);
            }
        }
        roleMenuService.insertBatch(list);

        LOGGER.info("==================结束调用 setMenus ================");
        return DataResponse.success();
    }

    @ResponseBody
    @RequestMapping(value = "getRoleMenus/{id}", method = RequestMethod.GET)
    public DataResponse getRoleMenus(@PathVariable String id){
        LOGGER.info("获取角色已选择菜单 id = " + id);
        Map<String, Object> result = new HashMap<>();

        EntityWrapper<SysRoleMenu> wrapper = new EntityWrapper<>();
        wrapper.eq("FK_ROLE_ID", id);

        List<SysRoleMenu> sysRoleMenus = roleMenuService.selectList(wrapper);

        List<String> list = new ArrayList<>();

        if(!CollectionUtils.isEmpty(sysRoleMenus)){
            for(SysRoleMenu roleMenu:sysRoleMenus){
                list.add(roleMenu.getFkMenuId());
            }
        }

        result.put("list", list);
        return DataResponse.success(result);
    }
	
}
