package com.sw.base.controller.customer;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.sw.cache.util.DataResponse;
import com.sw.common.constants.dict.IsOrNoDict;
import com.sw.common.constants.dict.StatusDict;
import com.sw.common.controller.BaseController;
import com.sw.base.service.impl.customer.CustomerAddressServiceImpl;
import com.sw.common.entity.customer.Customer;
import com.sw.common.entity.customer.CustomerAddress;
import com.sw.common.util.CollectionUtil;
import com.sw.common.util.DateUtil;
import com.sw.common.util.MapUtil;
import com.sw.common.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/system-web/customerAddress")
public class CustomerAddressController extends BaseController<CustomerAddressServiceImpl,CustomerAddress> {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerAddressController.class);

    @Autowired
    CustomerAddressServiceImpl customerAddressService;

    @ResponseBody
    @RequestMapping(value = "/setAddress", method = RequestMethod.POST)
    public DataResponse setAddress(@RequestBody Map<String, Object> params){
        String addOrUpdate = MapUtil.getString(params, "addOrUpdate");
        String isDefault = MapUtil.getString(params, "isDefault");
        if("add".equals(addOrUpdate)){
            CustomerAddress  customerAddress = new CustomerAddress();
            setData(customerAddress, params);
            if(IsOrNoDict.YES.getCode().equals(isDefault)){
                setDefault(customerAddress);
            }
            super.add(customerAddress);
        }else if("update".equals(addOrUpdate)){
            String id = MapUtil.getString(params, "id");
            EntityWrapper<CustomerAddress> wrapper = new EntityWrapper<>();
            wrapper.eq("PK_ADDRESS_ID", id);
            wrapper.eq("IS_DELETE", 0);
            CustomerAddress customerAddress = customerAddressService.selectOne(wrapper);
            if(customerAddress == null) {
                return  DataResponse.fail("更新失败, 未找到对应地址");
            }
            String _isDefault = customerAddress.getIsDefault();
            if(!_isDefault.equals(isDefault) && IsOrNoDict.YES.getCode().equals(isDefault)){
                setDefault(customerAddress);
            }
            setData(customerAddress, params);
            super.update(customerAddress);

        }
        return DataResponse.success();
    }

    private void setDefault(CustomerAddress customerAddress) {
        List<CustomerAddress> list = getList(customerAddress);
        if(CollectionUtil.isNotEmpty(list)){
            for(CustomerAddress _customerAddress:list){
                _customerAddress.setIsDefault(IsOrNoDict.NO.getCode());
                _customerAddress.setIsSelect(IsOrNoDict.NO.getCode());
                customerAddressService.updateById(_customerAddress);
            }
        }
    }

    public void setData(CustomerAddress customerAddress, Map<String, Object> params){
        customerAddress.setName(MapUtil.getString(params, "name"));
        customerAddress.setPhone(MapUtil.getString(params, "phone"));
        customerAddress.setFkCustomerId(MapUtil.getString(params, "fkCustomerId"));
        customerAddress.setPostCode(MapUtil.getString(params, "postCode"));
        customerAddress.setStatus(MapUtil.getString(params, "status"));
        customerAddress.setProvince(MapUtil.getString(params, "province"));
        customerAddress.setCity(MapUtil.getString(params, "city"));
        customerAddress.setRegion(MapUtil.getString(params, "region"));
        customerAddress.setDetailAddress(MapUtil.getString(params, "detailAddress"));
        customerAddress.setIsDefault(MapUtil.getString(params, "isDefault"));
        customerAddress.setIsSelect(MapUtil.getString(params, "isSelect"));
    }

    @ResponseBody
    @RequestMapping(value = "/getAddressList", method = RequestMethod.POST)
    public DataResponse getAddressList(@RequestBody Map<String, Object> params){
        String customerId = MapUtil.getString(params, "customerId");
        if(StringUtil.isEmpty(customerId)){
            return DataResponse.fail("用户不能为空");
        }
        EntityWrapper<CustomerAddress> wrapper = new EntityWrapper<>();
        wrapper.eq("FK_CUSTOMER_ID", customerId);
        wrapper.eq("IS_DELETE", 0);
        wrapper.eq("STATUS", StatusDict.START.getCode());
        List<CustomerAddress> list = customerAddressService.selectList(wrapper);
        Map<String, Object> result = new HashMap<>();
        result.put("data", list);
        return DataResponse.success(result);
    }

    @Override
    @ResponseBody
    @RequestMapping(value = "{id}",method = RequestMethod.DELETE)
    public DataResponse delete(@PathVariable String id, @RequestBody Map<String, Object> params){

        String userId = redisService.get("userId");
        LOGGER.info("userId" + userId);

        CustomerAddress obj = customerAddressService.selectById(id);

        EntityWrapper<CustomerAddress> delWrapper = super.mapToWrapper(params);
        obj.setIsDelete(1);
        obj.setUpdateUser(userId);
        obj.setUpdateTime(DateUtil.getCurrentDateTime());
        boolean flag = customerAddressService.update(obj, delWrapper);
        if(!flag){
            return DataResponse.fail("删除失败");
        }

        return DataResponse.success();
    }

    @ResponseBody
    @RequestMapping(value = "/updateAddress/{id}", method = RequestMethod.POST)
    public DataResponse updateAddress(@PathVariable String id){
        if(StringUtil.isEmpty(id)){
            return DataResponse.fail("收货地址不能为空");
        }
        EntityWrapper<CustomerAddress> wrapper = new EntityWrapper<>();
        wrapper.eq("PK_ADDRESS_ID", id);
        wrapper.eq("IS_DELETE", 0);
        wrapper.eq("STATUS", StatusDict.START.getCode());
        CustomerAddress customerAddress = customerAddressService.selectOne(wrapper);
        if(customerAddress == null){
            return DataResponse.fail("获取收货地址异常");
        }

        List<CustomerAddress> list = getList(customerAddress);

        if(CollectionUtil.isNotEmpty(list)){
            for(CustomerAddress _customerAddress:list){
                _customerAddress.setIsSelect(IsOrNoDict.NO.getCode());
                customerAddressService.updateById(_customerAddress);
            }
            customerAddress.setIsSelect(IsOrNoDict.YES.getCode());
            customerAddressService.updateById(customerAddress);
        }

        return DataResponse.success();
    }

    public List<CustomerAddress> getList(CustomerAddress customerAddress){
        EntityWrapper<CustomerAddress> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("FK_CUSTOMER_ID", customerAddress.getFkCustomerId());
        entityWrapper.eq("STATUS", StatusDict.START.getCode());
        entityWrapper.eq("IS_DELETE", 0);
        List<CustomerAddress> list = customerAddressService.selectList(entityWrapper);
        return list;
    }

}
