package com.sw.auth.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.sw.common.entity.system.Log;
import org.springframework.stereotype.Repository;

/**
 * <p>
  * 记录日志 Mapper 接口
 * </p>
 *
 * @author yu.leilei
 * @since 2018-12-23
 */
@Repository("logMappers")
public interface LogMapper extends BaseMapper<Log> {

}