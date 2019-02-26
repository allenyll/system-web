package com.sw.base.service.impl.system;

import com.sw.base.mapper.system.DepotMapper;
import com.sw.common.service.BaseService;
import com.sw.common.entity.system.Depot;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("depotService")
@Transactional(rollbackFor = Exception.class)
public class DepotServiceImpl extends BaseService<DepotMapper, Depot> {

}
