package com.sw.base.mapper;

import com.sw.common.entity.Customer;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
  * 会员表 Mapper 接口
 * </p>
 *
 * @author yu.leilei
 * @since 2018-10-22
 */
@Repository("customerMapper")
public interface CustomerMapper extends BaseMapper<Customer> {

}