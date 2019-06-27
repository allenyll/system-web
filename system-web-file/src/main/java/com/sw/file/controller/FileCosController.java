package com.sw.file.controller;

import com.sw.cache.util.DataResponse;
import com.sw.common.controller.BaseController;
import com.sw.common.entity.system.File;
import com.sw.common.util.CollectionUtil;
import com.sw.common.util.DateUtil;
import com.sw.common.util.MapUtil;
import com.sw.file.service.impl.FileServiceImpl;
import com.sw.file.util.CosFileUtil;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description:  腾讯云COS文件上传下载
 * @Author:       allenyll
 * @Date:         2019-05-24 17:38
 * @Version:      1.0
 */
@Slf4j
@Controller
@Api(value = "腾讯云文件上传接口")
@RequestMapping("/system-web/cosFile")
public class FileCosController extends BaseController<FileServiceImpl, File> {

    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");

    private static final String URL = "https://system-web-1257935390.cos.ap-chengdu.myqcloud.com";

    @Autowired
    FileServiceImpl fileService;

    @ResponseBody
   @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public DataResponse upload(@RequestParam("file") MultipartFile file, @RequestParam String type, @RequestParam String id) throws IOException {
        if(file == null) {
            return DataResponse.fail("上传文件不能为空");
        }

        String fileName = file.getOriginalFilename();
        String preFix = fileName.substring(fileName.lastIndexOf("."));
        Date date = new Date();
        String time = sdf.format(date);
       // 用uuid作为文件名，防止生成的临时文件重复
       final java.io.File excelFile = java.io.File.createTempFile("imagesFile-"+time, preFix);
       /* 将MultipartFile转为File */
       file.transferTo(excelFile);

       Map<String, Object> map = CosFileUtil.uploadFile(excelFile);

       if(map == null && map.isEmpty()){
           return DataResponse.fail("上传失败");
       }

       String url = MapUtil.getString(map, "fileName", "");

       String downloadUrl = MapUtil.getString(map, "url", "");

       if(!"SW1803".equals(type)){
           String userId = redisService.get("userId");
           // 存入数据库
           File sysFile = new File();
           sysFile.setFileType(type);
           sysFile.setFkId(id);
           sysFile.setFileUrl(URL + url);
           sysFile.setIsDelete(0);
           sysFile.setAddTime(DateUtil.getCurrentDateTime());
           sysFile.setUpdateTime(DateUtil.getCurrentDateTime());
           sysFile.setAddUser(userId);
           sysFile.setUpdateUser(userId);
           fileService.insert(sysFile);
       }

       Map<String,Object> result = new HashMap<>();
       result.put("url", URL + url);

       return DataResponse.success(result);
    }


    public static void main(String[] args) {
        Date date = new Date();
        String time = sdf.format(date);
        System.out.println(time);
    }

}
