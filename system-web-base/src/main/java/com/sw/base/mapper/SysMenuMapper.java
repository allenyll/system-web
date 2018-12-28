package com.sw.base.mapper;

import com.sw.common.entity.SysMenu;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
  * 菜单管理 Mapper 接口
 * </p>
 *
 * @author yu.leilei
 * @since 2018-06-12
 */
@Repository("sysMenuMapper")
public interface SysMenuMapper extends BaseMapper<SysMenu> {

}