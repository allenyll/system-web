package com.sw.base.controller.system;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.sw.base.controller.BaseController;
import com.sw.base.service.impl.system.MenuServiceImpl;
import com.sw.common.constants.BaseConstants;
import com.sw.common.entity.system.Menu;
import com.sw.common.entity.system.MenuTree;
import com.sw.common.util.DataResponse;
import com.sw.common.util.TreeUtil;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping("/system-web/menu/")
public class MenuController extends BaseController<MenuServiceImpl, Menu> {

    private static final Logger LOG = LoggerFactory.getLogger(MenuController.class);

    /**
     * 获取菜单
     * @param params
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getAllMenu", method = RequestMethod.GET)
    public DataResponse getAllMenu(@RequestParam Map<String, Object> params){
        LOG.info("==============开始调用getAllMenu================");

        Map<String, Object> result = new HashMap<>();
        EntityWrapper<Menu> wrapper = super.mapToWrapper(params);
        List<Menu> menuList = service.selectList(wrapper);
        List<MenuTree> list = getMenuTree(menuList, BaseConstants.MENU_ROOT);
        result.put("menus", list);

        return DataResponse.success(result);
    }

    @Override
    @ResponseBody
    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public DataResponse get(@PathVariable String id){
        Map<String, Object> result = new HashMap<>();

        EntityWrapper<Menu> wrapper = new EntityWrapper<>();
        wrapper.eq("IS_DELETE", 0);
        wrapper.eq("PK_MENU_ID", id);

        Menu sysMenu = service.selectOne(wrapper);

        if("0".equals(id)){
            sysMenu = new Menu();
            sysMenu.setPkMenuId(id);
            sysMenu.setMenuName("顶级节点");
        }else{
            setParentMenu(sysMenu);
        }
        result.put("obj", sysMenu);

        return DataResponse.success(result);
    }

    @ResponseBody
    @RequestMapping(value = "getMenuTree", method = RequestMethod.GET)
    public DataResponse getMenuTree(String type){
        LOG.info("==================开始调用getMenuTree================");
        Map<String, Object> result = new HashMap<>();

        EntityWrapper<Menu> wrapper = new EntityWrapper<>();
        wrapper.eq("IS_DELETE", 0);
        if ("menu".equals(type)) {
            wrapper.eq("MENU_TYPE", "SW0101");
        }

        List<Menu> menuList = service.selectList(wrapper);
        if(!CollectionUtils.isEmpty(menuList)){
            for(Menu menu:menuList){
                setParentMenu(menu);
            }
        }
        Menu topMenu = new Menu();
        topMenu.setPkMenuId("0");
        topMenu.setIsDelete(0);
        topMenu.setMenuName("顶级节点");
        topMenu.setMenuCode("top");
        topMenu.setParentMenuId("top");
        topMenu.setMenuIcon("sw-top");
        menuList.add(topMenu);
        List<MenuTree> menuTrees = getMenuTree(menuList, "top");
        result.put("menuTree", menuTrees);
        LOG.info("==================结束调用getMenuTree================");
        return DataResponse.success(result);
    }

    private List<MenuTree> getMenuTree(List<Menu> menuList, String menuRootId) {
        List<MenuTree> menuTrees = new ArrayList<>();
        MenuTree menuTree;
        if(!CollectionUtils.isEmpty(menuList)){
            for(Menu menu:menuList){
                menuTree = new MenuTree();
                menuTree.setId(menu.getPkMenuId());
                menuTree.setParentId(menu.getParentMenuId());
                menuTree.setCode(menu.getMenuCode());
                menuTree.setName(menu.getMenuName());
                menuTree.setHref(menu.getMenuUrl());
                menuTree.setTitle(menu.getMenuName());
                menuTree.setLabel(menu.getMenuName());
                menuTree.setIcon(menu.getMenuIcon());
                menuTrees.add(menuTree);
            }
        }
        return TreeUtil.build(menuTrees, menuRootId);
    }

    private void setParentMenu(Menu sysMenu) {
        String parentId = sysMenu.getParentMenuId();
        if(parentId.equals(BaseConstants.MENU_ROOT)){
            sysMenu.setParentMenuName("顶级节点");
        }else{
            EntityWrapper<Menu> entityWrapper = new EntityWrapper<>();
            entityWrapper.eq("IS_DELETE", 0);
            entityWrapper.eq("PK_MENU_ID", parentId);
            Menu menu = service.selectOne(entityWrapper);
            if(menu != null){
                sysMenu.setParentMenuName(menu.getMenuName());
            }
        }
    }

}
