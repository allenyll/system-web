package com.sw.file.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.sw.cache.util.DataResponse;
import com.sw.common.controller.BaseController;
import com.sw.common.entity.system.File;
import com.sw.common.util.DateUtil;
import com.sw.common.util.MapUtil;
import com.sw.common.util.StringUtil;
import com.sw.file.service.impl.FileServiceImpl;
import com.sw.file.util.CommFileUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description:  文件管理控制器
 * @Author:       allenyll
 * @Date:         2019/3/25 5:34 PM
 * @Version:      1.0
 */
@Slf4j
@Controller
@Api(value = "文件上传接口")
@RequestMapping("/system-web/file")
public class FileController extends BaseController<FileServiceImpl, File> {

    @Autowired
    CommFileUtil fileUtil;

    @Autowired
    FileServiceImpl fileService;

    @ResponseBody
    @RequestMapping(value = "getFileList", method = RequestMethod.POST)
    public DataResponse getList(@RequestParam Map<String, Object> params) {
        Map<String, Object> result = new HashMap<>();
        EntityWrapper<File> wrapper = new EntityWrapper<>();
        wrapper.eq("IS_DELETE", 0);
        wrapper.eq("FILE_TYPE", MapUtil.getMapValue(params, "type"));
        wrapper.eq("FK_ID",MapUtil.getMapValue(params, "id"));
        List<File> list = service.selectList(wrapper);
        List<Map<String, String>> newList  = new ArrayList<>();
        if(!CollectionUtils.isEmpty(list)){
            for (File file:list){
                Map<String, String> map = new HashMap<>();
                map.put("url", file.getFileUrl());
                map.put("id", file.getPkFileId());
                newList.add(map);
            }
        }

        result.put("list", newList);
        return DataResponse.success(result);
    }

    @ResponseBody
    @RequestMapping(value = "delFile", method = RequestMethod.POST)
    public DataResponse delFile(@RequestParam Map<String, Object> params) {

        String url = MapUtil.getMapValue(params, "url");
        url = url.substring(url.indexOf('/', 7)+1);

        try {
            // TODO 到底要不要删除服务器图片???????
            fileUtil.deleteFile(url);
            return DataResponse.fail("删除服务器图片失败");
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 删除商品图片
        DataResponse dataResponse = super.delete(MapUtil.getMapValue(params, "eq_pk_file_id"), params);

        return dataResponse;
    }

    /**
     * 文件上传
     * @param file
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ApiOperation(value = "upload")
    public DataResponse upload(@RequestParam("file") MultipartFile file, @RequestParam String type, @RequestParam String id) throws Exception{

        String url = null;
        try {
            url = fileUtil.uploadFile(file);
        } catch (IOException e) {
            log.error("上传文件失败!");
            return DataResponse.fail(e.getMessage());
        }

        if(StringUtil.isEmpty(url)){
            return DataResponse.fail("返回文件路径为空");
        }

        if(!"SW1803".equals(type)){
            String userId = redisService.get("userId");
            // 存入数据库
            File sysFile = new File();
            sysFile.setFileType(type);
            sysFile.setFkId(id);
            sysFile.setFileUrl("http://www.allenyll.com:9999/" + url);
            sysFile.setIsDelete(0);
            sysFile.setAddTime(DateUtil.getCurrentDateTime());
            sysFile.setAddUser(userId);
            sysFile.setUpdateTime(DateUtil.getCurrentDateTime());
            sysFile.setUpdateUser(userId);
            fileService.insert(sysFile);
        }

        Map<String,Object> result = new HashMap<>();
        result.put("url", "http://www.allenyll.com:9999/" + url);

        return DataResponse.success(result);
    }

    /**
     * 文件下载
     * @param fileUrl  url 开头从组名开始
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/download", method = RequestMethod.POST)
    public void  download(@RequestParam String fileUrl, HttpServletResponse response) throws Exception{

        byte[] data = fileUtil.download(fileUrl);

        response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode("test.jpg", "UTF-8"));

        // 写出
        ServletOutputStream outputStream = response.getOutputStream();
        IOUtils.write(data, outputStream);
    }
}
