package com.sw.base.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.sw.base.service.ISysDepotService;
import com.sw.common.constants.BaseConstants;
import com.sw.common.entity.DepotTree;
import com.sw.common.entity.SysDepot;
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

import java.util.*;

/**
 * <p>
 * 管理部门 前端控制器
 * </p>
 *
 * @author yu.leilei
 * @since 2018-11-20
 */
@Api(value = "组织相关接口")
@Controller
@RequestMapping("/depot")
public class SysDepotController extends BaseController{

    private static final Logger LOGGER = LoggerFactory.getLogger(SysDepotController.class);

    @Autowired
    ISysDepotService depotService;

    @ResponseBody
    @RequestMapping(value = "getAllDepot", method = RequestMethod.GET)
    public DataResponse getDepotList(String name){
        LOGGER.info("============= {开始调用方法：getDepotList(} =============");
        Map<String, Object> result = new HashMap<>();
        EntityWrapper<SysDepot> wrapper = new EntityWrapper<>();
        wrapper.eq("IS_DELETE", 0);
        if(StringUtil.isNotEmpty(name)){
            wrapper.like("DEPOT_NAME", name);
        }
        List<SysDepot> depots = depotService.selectList(wrapper);

        List<DepotTree> list = getDepotTree(depots, BaseConstants.MENU_ROOT);

        result.put("depots", list);
        LOGGER.info("============= {结束调用方法：getDepotList(} =============");
        return DataResponse.success(result);
    }

    private void buildDepotList(List<SysDepot> list) {
    }

    @ResponseBody
    @RequestMapping(value = "getDepotTree", method = RequestMethod.GET)
    public DataResponse getDepotTree(){
        LOGGER.info("==================开始调用getDepotTree================");
        Map<String, Object> result = new HashMap<>();

        EntityWrapper<SysDepot> wrapper = new EntityWrapper<>();
        wrapper.eq("IS_DELETE", 0);

        List<SysDepot> menuList = depotService.selectList(wrapper);
        if(!CollectionUtils.isEmpty(menuList)){
            for(SysDepot menu:menuList){
                setParentDepot(menu);
            }
        }
        List<DepotTree> trees = getDepotTree(menuList, BaseConstants.MENU_ROOT);

        result.put("depotTree", trees);
        LOGGER.info("==================结束调用getDepotTree================");
        return DataResponse.success(result);
    }

    @ResponseBody
    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public DataResponse get(@PathVariable String id){
        LOGGER.info("==================开始调用 get================");
        Map<String, Object> result = new HashMap<>();

        EntityWrapper<SysDepot> wrapper = new EntityWrapper<>();
        wrapper.eq("IS_DELETE", 0);
        wrapper.eq("PK_DEPOT_ID", id);

        SysDepot depot = depotService.selectOne(wrapper);

        setParentDepot(depot);

        result.put("sysDepot", depot);

        LOGGER.info("==================结束调用 get================");

        return DataResponse.success(result);
    }

    @RequestMapping(value = "addDepot", method = RequestMethod.POST)
    @ResponseBody
    public DataResponse addDepot(@RequestBody SysDepot depot){
        LOGGER.info("==================开始调用 addDepot ================");
        String userId = redisService.get("userId");


        depot.setIsDelete(0);
        depot.setAddTime(DateUtil.getCurrentDateTime());
        depot.setAddUser(userId);
        depot.setUpdateTime(DateUtil.getCurrentDateTime());
        depot.setUpdateUser(userId);
        depotService.insert(depot);

        LOGGER.info("==================结束调用 addDepot ================");
        return DataResponse.success();
    }


    @RequestMapping(value = "{id}",method = RequestMethod.PUT)
    @ResponseBody
    public DataResponse updateDepot(@PathVariable String id, @RequestBody SysDepot depot){
        LOGGER.info("==================开始调用 updateDepot ================");
        String userId = redisService.get("userId");
        LOGGER.info("userId" + userId);

        EntityWrapper<SysDepot> wrapper = new EntityWrapper<>();
        wrapper.eq("PK_DEPOT_ID", id);
        depot.setUpdateTime(DateUtil.getCurrentDateTime());
        depot.setUpdateUser(userId);
        depotService.update(depot, wrapper);

        LOGGER.info("==================结束调用 updateDepot ================");
        return DataResponse.success();
    }


    @RequestMapping(value = "{id}",method = RequestMethod.DELETE)
    @ResponseBody
    public DataResponse deleteDepot(@PathVariable String id){
        LOGGER.info("==================开始调用 deleteDepot ================");

        String userId = redisService.get("userId");
        LOGGER.info("userId" + userId);

        EntityWrapper<SysDepot> wrapper = new EntityWrapper<>();
        wrapper.eq("IS_DELETE", 0);
        wrapper.eq("PK_DEPOT_ID", id);

        SysDepot depot = depotService.selectOne(wrapper);

        EntityWrapper<SysDepot> delWrapper = new EntityWrapper<>();
        delWrapper.eq("PK_DEPOT_ID", id);
        depot.setIsDelete(1);
        depot.setUpdateTime(DateUtil.getCurrentDateTime());
        depot.setUpdateUser(userId);
        depotService.update(depot, delWrapper);

        LOGGER.info("=================结束调用 deleteDepot ===============");
        return DataResponse.success();
    }

    private List<DepotTree> getDepotTree(List<SysDepot> list, String rootId) {
        List<DepotTree> trees = new ArrayList<>();
        DepotTree tree;
        if(!CollectionUtils.isEmpty(list)){
            for(SysDepot obj:list){
                tree = new DepotTree();
                tree.setId(obj.getPkDepotId());
                tree.setParentId(obj.getParentDepotId());
                tree.setCode(obj.getDepotCode());
                tree.setName(obj.getDepotName());
                tree.setTitle(obj.getDepotName());
                tree.setLabel(obj.getDepotName());
                trees.add(tree);
            }
        }
        return TreeUtil.build(trees, rootId);
    }

    private void setParentDepot(SysDepot depot) {

        String parentId = depot.getParentDepotId();
        if(parentId.equals(BaseConstants.MENU_ROOT)){
            depot.setParentDepotName("顶级节点");
        }else{
            EntityWrapper<SysDepot> entityWrapper = new EntityWrapper<>();
            entityWrapper.eq("IS_DELETE", 0);
            entityWrapper.eq("PK_DEPOT_ID", parentId);
            SysDepot sysDepot = depotService.selectOne(entityWrapper);
            if(sysDepot != null){
                depot.setParentDepotName(sysDepot.getDepotName());
            }
        }
    }


}
