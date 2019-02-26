package com.sw.base.service.customer;

import com.sw.common.entity.customer.Customer;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author yu.leilei
 * @since 2018-10-22
 */
public interface ICustomerService extends IService<Customer> {

    /**
     * 创建还是注册用户
     * @param customer
     */
    void loginOrRegisterConsumer(Customer customer);

    /**
     * 统计用户数量
     * @param params
     * @return
     */
    int count(Map<String, Object> params);

    /**
     * 分页查询用户
     * @param params
     * @return
     */
    List<Customer> page(Map<String, Object> params);
}
