package com.sw.base.service;

import com.sw.base.entity.Customer;
import com.baomidou.mybatisplus.service.IService;

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
}
