package com.sw.base.controller.system;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.sw.base.service.impl.system.RoleServiceImpl;
import com.sw.base.controller.BaseController;
import com.sw.base.service.impl.system.SysRoleMenuServiceImpl;
import com.sw.base.service.impl.system.SysUserRoleServiceImpl;
import com.sw.common.entity.system.Role;
import com.sw.common.entity.system.SysRoleMenu;
import com.sw.common.entity.system.SysUserRole;
import com.sw.cache.util.DataResponse;
import com.sw.common.util.StringUtil;
import net.sf.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 字典表
 前端控制器
 * </p>
 *
 * @author yu.leilei
 * @since 2019-01-29
 */
@Controller
@RequestMapping("/system-web/role")
public class RoleController  extends BaseController<RoleServiceImpl, Role> {

    private static final Logger LOGGER = LoggerFactory.getLogger(RoleController.class);

    @Autowired
    SysRoleMenuServiceImpl roleMenuService;

    @Autowired
    SysUserRoleServiceImpl userRoleService;

    @ResponseBody
    @RequestMapping(value = "getRoleList/{id}", method = RequestMethod.GET)
    public DataResponse getAllRole(@PathVariable String id){
        LOGGER.info("============= {开始调用方法：getAllRole(} =============");
        Map<String, Object> result = new HashMap<>();

        // 获取用户拥有的角色
        EntityWrapper<SysUserRole> wrapper = new EntityWrapper<>();
        wrapper.eq("FK_USER_ID", id);

        SysUserRole user = userRoleService.selectOne(wrapper);
        Role role;
        if(user != null){
            String roleId = user.getFkRoleId();
            EntityWrapper<Role> roleEntityWrapper = new EntityWrapper<>();
            roleEntityWrapper.eq("IS_DELETE", 0);
            roleEntityWrapper.eq("PK_ROLE_ID", roleId);

            role = service.selectOne(roleEntityWrapper);
            result.put("userRole", role);
        }else{
            result.put("userRole", "");
        }

        EntityWrapper<Role> userWrapper = new EntityWrapper<>();
        userWrapper.eq("IS_DELETE", 0);
        List<Role> roles = service.selectList(userWrapper);

        result.put("roleList", roles);

        LOGGER.info("============= {结束调用方法：getAllRole(} =============");
        return DataResponse.success(result);
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
