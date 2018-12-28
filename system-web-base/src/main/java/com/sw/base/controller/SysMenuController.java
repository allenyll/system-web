package com.sw.base.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.sw.auth.annotation.Log;
import com.sw.base.service.ISysMenuService;
import com.sw.cache.service.IRedisService;
import com.sw.common.constants.BaseConstants;
import com.sw.common.entity.MenuTree;
import com.sw.common.entity.SysMenu;
import com.sw.common.util.DataResponse;
import com.sw.common.util.DateUtil;
import com.sw.common.util.StringUtil;
import com.sw.common.util.TreeUtil;
import io.swagger.annotations.Api;
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
 * 菜单管理 前端控制器
 * </p>
 *
 * @author yu.leilei
 * @since 2018-06-12
 */
@Api(value = "菜单相关接口")
@Controller
@RequestMapping("/menu")
public class SysMenuController extends BaseController{

    private static final Logger LOG = LoggerFactory.getLogger(SysMenuController.class);

    @Autowired
    ISysMenuService menuService;

    @Autowired
    IRedisService redisService;

    /**
     * 获取菜单
     * @param name
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getAllMenu", method = RequestMethod.GET)
    public DataResponse getAllMenu(String name){
        LOG.info("==============开始调用getAllMenu================");

        Map<String, Object> result = new HashMap<>();
        EntityWrapper<SysMenu> wrapper = new EntityWrapper<>();
        wrapper.eq("IS_DELETE", 0);
        if(StringUtil.isNotEmpty(name)){
            wrapper.like("MENU_NAME", name);
        }
        List<SysMenu> menuList = menuService.selectList(wrapper);

        List<MenuTree> list = getMenuTree(menuList, BaseConstants.MENU_ROOT);

        result.put("menus", list);

        return DataResponse.success(result);
    }


    @ResponseBody
    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public DataResponse getMenuById(@PathVariable String id){
        LOG.info("================== 开始调用getMenuById================");
        Map<String, Object> result = new HashMap<>();

        EntityWrapper<SysMenu> wrapper = new EntityWrapper<>();
        wrapper.eq("IS_DELETE", 0);
        wrapper.eq("PK_MENU_ID", id);

        SysMenu sysMenu = menuService.selectOne(wrapper);

        if("0".equals(id)){
            sysMenu = new SysMenu();
            sysMenu.setPkMenuId(id);
            sysMenu.setMenuName("顶级节点");
        }else{
            setParentMenu(sysMenu);
        }

        result.put("sysMenu", sysMenu);

        LOG.info("==================结束调用getMenuById================");

        return DataResponse.success(result);
    }

    @RequestMapping(value = "addMenu", method = RequestMethod.POST)
    @ResponseBody
    public DataResponse addMenu(@RequestBody SysMenu menu){
        LOG.info("==================开始调用addMenu================");
        String userId = redisService.get("userId");
        LOG.info("userId" + userId);

        menu.setIsDelete(0);
        menu.setAddTime(DateUtil.getCurrentDateTime());
        menu.setAddUser(userId);
        menu.setUpdateTime(DateUtil.getCurrentDateTime());
        menu.setUpdateUser(userId);
        menuService.insert(menu);

        LOG.info("==================结束调用addMenu================");
        return DataResponse.success();
    }

    @Log("编辑菜单")
    @ResponseBody
    @RequestMapping(value = "{id}",method = RequestMethod.PUT)
    public DataResponse updateMenu(@PathVariable String id, @RequestBody SysMenu menu){
        LOG.info("==================开始调用updateMenu================");
        String userId = redisService.get("userId");
        LOG.info("userId" + userId);
        EntityWrapper<SysMenu> wrapper = new EntityWrapper<>();
        wrapper.eq("PK_MENU_ID", id);
        menu.setUpdateTime(DateUtil.getCurrentDateTime());
        menu.setUpdateUser(userId);
        menuService.update(menu, wrapper);

        LOG.info("==================结束调用updateMenu================");
        return DataResponse.success();
    }

    @RequestMapping(value = "{id}",method = RequestMethod.DELETE)
    @ResponseBody
    public DataResponse deleteMenu(@PathVariable String id){
        LOG.info("==================开始调用deleteMenu================");

        String userId = redisService.get("userId");
        LOG.info("userId" + userId);

        EntityWrapper<SysMenu> wrapper = new EntityWrapper<>();
        wrapper.eq("IS_DELETE", 0);
        wrapper.eq("PK_MENU_ID", id);

        SysMenu menu = menuService.selectOne(wrapper);

        EntityWrapper<SysMenu> delWrapper = new EntityWrapper<>();
        delWrapper.eq("PK_MENU_ID", id);
        menu.setIsDelete(1);
        menu.setUpdateTime(DateUtil.getCurrentDateTime());
        menu.setUpdateUser(userId);
        menuService.update(menu, delWrapper);

        LOG.info("=================结束调用deleteMenu================");
        return DataResponse.success();
    }

    @ResponseBody
    @RequestMapping(value = "getMenuTree", method = RequestMethod.GET)
    public DataResponse getMenuTree(String type){
        LOG.info("==================开始调用getMenuTree================");
        Map<String, Object> result = new HashMap<>();

        EntityWrapper<SysMenu> wrapper = new EntityWrapper<>();
        wrapper.eq("IS_DELETE", 0);
        if ("menu".equals(type)) {
            wrapper.eq("MENU_TYPE", "SW0101");
        }

        List<SysMenu> menuList = menuService.selectList(wrapper);
        if(!CollectionUtils.isEmpty(menuList)){
            for(SysMenu menu:menuList){
                setParentMenu(menu);
            }
        }
        SysMenu topMenu = new SysMenu();
        topMenu.setPkMenuId("0");
        topMenu.setIsDelete(0);
        topMenu.setMenuName("顶级节点");
        topMenu.setMenuCode("top");
        topMenu.setParentMenuId("top");
        menuList.add(topMenu);
        List<MenuTree> menuTrees = getMenuTree(menuList, "top");
        result.put("menuTree", menuTrees);
        LOG.info("==================结束调用getMenuTree================");
        return DataResponse.success(result);
    }

    private List<MenuTree> getMenuTree(List<SysMenu> menuList, String menuRootId) {
        List<MenuTree> menuTrees = new ArrayList<>();
        MenuTree menuTree;
        if(!CollectionUtils.isEmpty(menuList)){
            for(SysMenu menu:menuList){
                menuTree = new MenuTree();
                menuTree.setId(menu.getPkMenuId());
                menuTree.setParentId(menu.getParentMenuId());
                menuTree.setCode(menu.getMenuCode());
                menuTree.setHref(menu.getMenuUrl());
                menuTree.setName(menu.getMenuName());
                menuTree.setTitle(menu.getMenuName());
                menuTree.setLabel(menu.getMenuName());
                menuTree.setIcon(menu.getMenuIcon());
                menuTrees.add(menuTree);
            }
        }
        return TreeUtil.build(menuTrees, menuRootId);
    }

    private void setParentMenu(SysMenu sysMenu) {
        String parentId = sysMenu.getParentMenuId();
        if(parentId.equals(BaseConstants.MENU_ROOT)){
            sysMenu.setParentMenuName("顶级节点");
        }else{
            EntityWrapper<SysMenu> entityWrapper = new EntityWrapper<>();
            entityWrapper.eq("IS_DELETE", 0);
            entityWrapper.eq("PK_MENU_ID", parentId);
            SysMenu menu = menuService.selectOne(entityWrapper);
            if(menu != null){
                sysMenu.setParentMenuName(menu.getMenuName());
            }
        }
    }

}
