package com.sw.base.service.impl.customer;

import com.sw.common.entity.customer.CustomerPoint;
import com.sw.base.mapper.customer.CustomerPointMapper;
import com.sw.base.service.customer.ICustomerPointService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yu.leilei
 * @since 2019-01-09
 */
@Service("customerPointService")
public class CustomerPointServiceImpl extends ServiceImpl<CustomerPointMapper, CustomerPoint> implements ICustomerPointService {
	
}
