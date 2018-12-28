package com.sw.base.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.sw.auth.service.ISysUserService;
import com.sw.auth.service.IUserAuthService;
import com.sw.auth.util.JwtUtil;
import com.sw.base.service.ISysDepotService;
import com.sw.base.service.ISysUserRoleService;
import com.sw.cache.service.IRedisService;
import com.sw.common.constants.dict.SexDict;
import com.sw.common.constants.dict.UserStatus;
import com.sw.common.entity.SysDepot;
import com.sw.common.entity.SysUser;
import com.sw.common.entity.SysUserRole;
import com.sw.common.util.DataResponse;
import com.sw.common.util.DateUtil;
import com.sw.common.util.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.sf.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import java.util.*;


/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author yu.leilei
 * @since 2018-06-12
 */
@Controller
@Api(description = "系统相关用户接口")
@RequestMapping("/user")
public class SysUserController extends BaseController{

    private static final Logger LOGGER = LoggerFactory.getLogger(SysUserController.class);

    @Autowired
    ISysUserService sysUserService;

    @Autowired
    IUserAuthService userAuthService;

    @Autowired
    ISysUserRoleService userRoleService;

    @Autowired
    ISysDepotService depotService;

    @Autowired
    IRedisService redisService;

    @Autowired
    JwtUtil jwtUtil;

    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @ApiOperation(value = "根据token获取当前用户所属角色拥有的菜单", notes = "根据token获取当前用户所属角色拥有的菜单")
    @RequestMapping(value = "getUserInfo", method = RequestMethod.GET)
    @ResponseBody
    public DataResponse getUserInfo(@RequestParam String token){
        LOGGER.info("============= {开始调用方法：getUserInfo} =============");
        LOGGER.info("============= {token："+ token +"} =============");
        Map<String, Object> result = new HashMap<>();
        final String authToken = token.substring(tokenHead.length());
        String userName = jwtUtil.getUsernameFromToken(authToken);
        SysUser user = userAuthService.getSysUser(userName);
        if(user != null){
            String redisUserId = redisService.get("userId");
            if(StringUtil.isEmpty(redisUserId)  ){
                redisService.set("userId", user.getPkUserId());
                redisService.set("account", user.getAccount());
            }
            String userId = user.getPkUserId();
            Map<String, String> param = new HashMap<>();
            param.put("user_id", userId);
            EntityWrapper<SysUserRole> userRoleEntityWrapper = new EntityWrapper<>();
            userRoleEntityWrapper.eq("FK_USER_ID", userId);
            // 根据userId查询用户角色
            List<Map<String, Object>>  menuList = sysUserService.getUserRoleMenuList(param);
            SysUserRole sysUserRole = userRoleService.selectOne(userRoleEntityWrapper);
            result.put("user", user);
            result.put("menus", menuList);
            result.put("roles", sysUserRole);

        }else{
            LOGGER.info("没有查询到用户!");
            return DataResponse.fail("没有查询到该用户！");
        }
        LOGGER.info("============= {结束调用方法：getUserInfo} =============");
        return DataResponse.success(result);
    }

    @ResponseBody
    @RequestMapping(value = "page", method = RequestMethod.GET)
    public DataResponse getUserList(@RequestParam Map<String, Object> params){
        LOGGER.info("============= {开始调用方法：getUserList(} =============");
        Map<String, Object> result = new HashMap<>();
        LOGGER.info("传入参数=============" + params);
        page = Integer.parseInt(params.get("page").toString());
        limit = Integer.parseInt(params.get("limit").toString());
        String name = params.get("name").toString();
        String account = params.get("account").toString();
        String phone = params.get("phone").toString();

        EntityWrapper<SysUser> userWrapper = new EntityWrapper<>();
        userWrapper.eq("IS_DELETE", 0);
        if (StringUtil.isNotEmpty(name)){
            userWrapper.like("USER_NAME", name);
        }
        if (StringUtil.isNotEmpty(account)){
            userWrapper.like("ACCOUNT", account);
        }
        if (StringUtil.isNotEmpty(phone)) {
            userWrapper.like("PHONE", phone);
        }

        int start = (page - 1) * limit;
        int total = sysUserService.selectCount(userWrapper);
        List<SysUser> list = sysUserService.getUserListByPage(userWrapper, start, limit);
        buildUserList(list);
        result.put("total", total);
        result.put("userList", list);
        LOGGER.info("============= {结束调用方法：getUserList(} =============");
        return DataResponse.success(result);
    }

    private void buildUserList(List<SysUser> list) {
        if(!CollectionUtils.isEmpty(list)){
            for(SysUser user:list){
                setDepotName(user);
                user.setRealSex(SexDict.codeToMessage(user.getSex()));
                user.setRealStatus(UserStatus.codeToMessage(user.getStatus()));
            }
        }
    }


    @ResponseBody
    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public DataResponse get(@PathVariable String id){
        LOGGER.info("==================开始调用 get ================");
        Map<String, Object> result = new HashMap<>();

        EntityWrapper<SysUser> wrapper = new EntityWrapper<>();
        wrapper.eq("IS_DELETE", 0);
        wrapper.eq("PK_USER_ID", id);

        SysUser user = sysUserService.selectOne(wrapper);
        if(user != null){
            setDepotName(user);
        }

        result.put("sysUser", user);

        LOGGER.info("==================结束调用 get ================");

        return DataResponse.success(result);
    }


    @RequestMapping(value = "addUser", method = RequestMethod.POST)
    @ResponseBody
    public DataResponse addUser(@RequestBody SysUser user){
        LOGGER.info("==================开始调用 addUser ================");
        String userId = redisService.get("userId");

        // 密码加密
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        final String rawPassword = user.getPassword();
        user.setPassword(passwordEncoder.encode(rawPassword));
        user.setLastPasswordResetDate(new Date());

        user.setIsDelete(0);
        user.setAddTime(DateUtil.getCurrentDateTime());
        user.setAddUser(userId);
        user.setUpdateTime(DateUtil.getCurrentDateTime());
        user.setUpdateUser(userId);
        sysUserService.insert(user);

        LOGGER.info("==================结束调用 addUser ================");
        return DataResponse.success();
    }


    @RequestMapping(value = "{id}",method = RequestMethod.PUT)
    @ResponseBody
    public DataResponse updateUser(@PathVariable String id, @RequestBody SysUser user){
        LOGGER.info("==================开始调用 updateUser ================");
        String userId = redisService.get("userId");
        LOGGER.info("userId" + userId);

        EntityWrapper<SysUser> wrapper = new EntityWrapper<>();
        wrapper.eq("PK_USER_ID", id);
        user.setUpdateTime(DateUtil.getCurrentDateTime());
        user.setUpdateUser(userId);
        sysUserService.update(user, wrapper);

        LOGGER.info("==================结束调用 updateUser ================");
        return DataResponse.success();
    }


    @RequestMapping(value = "{id}",method = RequestMethod.DELETE)
    @ResponseBody
    public DataResponse deleteUser(@PathVariable String id){
        LOGGER.info("==================开始调用 deleteUser ================");

        String userId = redisService.get("userId");
        LOGGER.info("userId" + userId);

        EntityWrapper<SysUser> wrapper = new EntityWrapper<>();
        wrapper.eq("IS_DELETE", 0);
        wrapper.eq("PK_USER_ID", id);

        SysUser user = sysUserService.selectOne(wrapper);

        EntityWrapper<SysUser> delWrapper = new EntityWrapper<>();
        delWrapper.eq("PK_USER_ID", id);
        user.setIsDelete(1);
        user.setUpdateTime(DateUtil.getCurrentDateTime());
        user.setUpdateUser(userId);
        sysUserService.update(user, delWrapper);

        LOGGER.info("=================结束调用 deleteUser ===============");
        return DataResponse.success();
    }

    @RequestMapping(value = "/setRoles",method = RequestMethod.POST)
    @ResponseBody
    public DataResponse setRoles(@RequestBody Map<String, Object> params){

        LOGGER.info("==================开始调用 setRoles ================");
        LOGGER.info("params"+params);
        // 全删全插配置用户角色
        // 1、先删除所有该用户拥有的角色
        String userId = params.get("id").toString();
        EntityWrapper<SysUserRole> userRoleEntityWrapper = new EntityWrapper<>();
        userRoleEntityWrapper.eq("FK_USER_ID", userId);
        userRoleService.delete(userRoleEntityWrapper);

        // 2、重新插入选择的角色权限
        List<SysUserRole> list = new ArrayList<>();
        JSONArray jsonArray = JSONArray.fromObject(params.get("ids"));
        if(jsonArray.size() > 0){
            for(int i=0; i<jsonArray.size(); i++){
                String roleId = jsonArray.getString(i);
                SysUserRole sysUserRole = new SysUserRole();
                sysUserRole.setFkRoleId(roleId);
                sysUserRole.setFkUserId(userId);
                sysUserRole.setPkRelationId(StringUtil.getUUID32());
                list.add(sysUserRole);
            }
        }
        userRoleService.insertBatch(list);

        LOGGER.info("==================结束调用 setRoles ================");
        return DataResponse.success();
    }

    public void setDepotName(SysUser user){
        String depotId = user.getFkDepotId();
        EntityWrapper<SysDepot> depotEntityWrapper = new EntityWrapper<>();
        depotEntityWrapper.eq("IS_DELETE", 0);
        depotEntityWrapper.eq("PK_DEPOT_ID", depotId);

        SysDepot depot = depotService.selectOne(depotEntityWrapper);

        if(depot != null){
            user.setDepotName(depot.getDepotName());
        }
    }


}
