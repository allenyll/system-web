package com.sw.file.service.impl;

import org.springframework.stereotype.Service;

import com.sw.common.entity.system.File;
import com.sw.file.mapper.FileMapper;
import com.sw.common.service.BaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 文件相关信息
 *
 * @author allenyll
 * @email 806141743@qq.com
 * @date 2019-03-26 21:28:23
 */
@Service("fileService")
public class FileServiceImpl extends BaseService<FileMapper,File> {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileServiceImpl.class);
}