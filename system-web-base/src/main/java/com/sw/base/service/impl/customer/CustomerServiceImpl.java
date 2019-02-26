package com.sw.base.service.impl.customer;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.sw.base.mapper.customer.CustomerPointDetailMapper;
import com.sw.base.mapper.customer.CustomerPointMapper;
import com.sw.common.entity.customer.Customer;
import com.sw.base.mapper.customer.CustomerMapper;
import com.sw.base.service.customer.ICustomerService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.sw.common.constants.dict.UserStatus;
import com.sw.common.entity.customer.CustomerPoint;
import com.sw.common.entity.customer.CustomerPointDetail;
import com.sw.common.util.DateUtil;
import com.sw.common.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.CollectionUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

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

    private static final SimpleDateFormat FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Autowired
    CustomerMapper customerMapper;

    @Autowired
    CustomerPointMapper customerPointMapper;

    @Autowired
    CustomerPointDetailMapper customerPointDetailMapper;

    @Override
    @Transactional(rollbackFor=Exception.class)
    public void loginOrRegisterConsumer(Customer customer) {
        EntityWrapper<Customer> wrapper = new EntityWrapper<>();
        wrapper.eq("OPENID", customer.getOpenid());
        wrapper.eq("STATUS", UserStatus.OK.getCode());
        wrapper.eq("IS_DELETE", 0);
        List<Customer> customerList = customerMapper.selectList(wrapper);
        if(CollectionUtils.isEmpty(customerList)){
            String id = StringUtil.getUUID32();
            String date = DateUtil.getCurrentDateTime();
            customer.setPkCustomerId(id);
            customer.setIsDelete(0);
            customer.setStatus(UserStatus.OK.getCode());
            customer.setAddTime(date);
            customer.setUpdateTime(date);
            String gender = "";
            if("1".equals(customer.getGender())){
                gender = "SW0201";
            } else if("0".equals(customer.getGender())){
                gender = "SW0202";
            }
            customer.setGender(gender);
            customer.setOpenid(customer.getOpenid());
            customer.setEmail(customer.getEmail());
            customer.setPhone(customer.getPhone());
            customer.setCountry(customer.getCountry());
            customer.setProvince(customer.getProvince());
            customer.setCity(customer.getCity());
            customer.setAvatarUrl(customer.getAvatarUrl());
            customer.setAddUser("weChat");
            customer.setUpdateTime("weChat");
            customer.setCustomerAccount(customer.getNickName());
            customer.setCustomerName(customer.getNickName());

            // 注册送积分
            CustomerPoint customerPoint = new CustomerPoint();
            customerPoint.setFkCustomerId(id);
            customerPoint.setPoint(5);
            customerPoint.setUsed(0);

            CustomerPointDetail customerPointDetail = new CustomerPointDetail();
            customerPointDetail.setFkCustomerId(id);
            customerPointDetail.setPoint(5);
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.MONTH, 6);
            Date time = calendar.getTime();
            customerPointDetail.setExpiredTime(FORMAT.format(time));
            customerPointDetail.setIsDeleted(0);
            customerPointDetail.setRemark("用户" + customer.getNickName() + "注册赠送5个积分!");

            try {
                customerMapper.insert(customer);
                customerPointMapper.insert(customerPoint);
                customerPointDetailMapper.insert(customerPointDetail);
            } catch (Exception e) {
                e.printStackTrace();
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            }
        }
    }

    @Override
    public int count(Map<String, Object> params) {
        return customerMapper.count(params);
    }

    @Override
    public List<Customer> page(Map<String, Object> params) {
        return customerMapper.page(params);
    }

}
