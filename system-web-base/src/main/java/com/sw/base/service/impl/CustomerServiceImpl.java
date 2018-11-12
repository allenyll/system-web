package com.sw.base.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.sw.common.entity.Customer;
import com.sw.base.mapper.CustomerMapper;
import com.sw.base.service.ICustomerService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.sw.common.constants.dict.UserStatus;
import com.sw.common.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author yu.leilei
 * @since 2018-10-22
 */
@Service("customerService")
public class CustomerServiceImpl extends ServiceImpl<CustomerMapper, Customer> implements ICustomerService {

    @Autowired
    CustomerMapper customerMapper;

    @Override
    public void loginOrRegisterConsumer(Customer customer) {
        EntityWrapper<Customer> wrapper = new EntityWrapper<>();
        wrapper.eq("OPENID", customer.getOpenid());
        wrapper.eq("STATUS", UserStatus.OK.getCode());
        wrapper.eq("IS_DELETE", 0);
        List<Customer> customerList = customerMapper.selectList(wrapper);
        if(CollectionUtils.isEmpty(customerList)){
            customer.setIsDelete(0);
            customer.setStatus(UserStatus.OK.getCode());
            customer.setAddTime(DateUtil.getCurrentDateTime());
            customer.setUpdateTime(DateUtil.getCurrentDateTime());
            customer.setGender(customer.getGender());
            customer.setOpenid(customer.getOpenid());
            customer.setEmail(customer.getEmail());
            customer.setPhone(customer.getPhone());
            customer.setCountry(customer.getCountry());
            customer.setProvince(customer.getProvince());
            customer.setCity(customer.getCity());
            customer.setAvatarUrl(customer.getAvatarUrl());
            customerMapper.insert(customer);
        }
    }
}
