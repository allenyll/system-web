package com.sw.base.controller.system;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.sw.auth.service.IUserAuthService;
import com.sw.auth.service.impl.UserServiceImpl;
import com.sw.auth.util.JwtUtil;
import com.sw.base.controller.BaseController;
import com.sw.base.service.impl.system.DepotServiceImpl;
import com.sw.base.service.system.ISysUserRoleService;
import com.sw.cache.service.IRedisService;
import com.sw.common.entity.system.*;
import com.sw.common.util.DataResponse;
import com.sw.common.util.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.sf.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping("/system-web/user/")
public class UserController extends BaseController<UserServiceImpl, User> {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired
    UserServiceImpl userService;

    @Autowired
    IUserAuthService userAuthService;

    @Autowired
    ISysUserRoleService userRoleService;

    @Autowired
    DepotServiceImpl depotService;

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
        User user = userAuthService.getSysUser(userName);
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
            List<Map<String, Object>>  menuList = userService.getUserRoleMenuList(param);
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

    @Override
    @ResponseBody
    @RequestMapping(value = "page", method = RequestMethod.GET)
    public DataResponse page(@RequestParam Map<String, Object> params){

        DataResponse dataResponse = super.page(params);
        List<User> list = buildUserList((List<User>) dataResponse.get("list"));
        dataResponse.put("list", list);
        return dataResponse;
    }

    @ResponseBody
    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public DataResponse get(@PathVariable String id){
        DataResponse dataResponse = super.get(id);
        User user = (User) dataResponse.get("obj");
        if(user != null){
            setDepotName(user);
        }
        dataResponse.put("obj", user);
        return dataResponse;
    }

    private List<User>  buildUserList(List<User> list) {
        if(!CollectionUtils.isEmpty(list)){
            for(User user:list){
                setDepotName(user);
            }
        }
        return list;
    }

    public void setDepotName(User user){
        String depotId = user.getFkDepotId();
        EntityWrapper<Depot> depotEntityWrapper = new EntityWrapper<>();
        depotEntityWrapper.eq("IS_DELETE", 0);
        depotEntityWrapper.eq("PK_DEPOT_ID", depotId);

        Depot depot = depotService.selectOne(depotEntityWrapper);

        if(depot != null){
            user.setDepotName(depot.getDepotName());
        }
    }

    @Override
    @RequestMapping(value = "add", method = RequestMethod.POST)
    @ResponseBody
    public DataResponse add(@RequestBody User user){
        LOGGER.info("==================开始调用 addUser ================");

        // 密码加密
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        final String rawPassword = user.getPassword();
        user.setPassword(passwordEncoder.encode(rawPassword));
        user.setLastPasswordResetDate(new Date());

        LOGGER.info("==================结束调用 addUser ================");
        return super.add(user);
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


}
