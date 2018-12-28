package com.sw.common.util;

import com.sw.common.constants.BaseConstants;

import java.util.HashMap;
import java.util.Map;

/**
 * 返回响应工具类
 * @Author: yu.leilei
 * @Date: 下午 1:30 2018/1/16 0016
 */
public class DataResponse extends HashMap<String, Object> {

    private static final long serialVersionUID = 1L;

    @Override
    public DataResponse put(String key, Object value) {
        super.put(key, value);
        return this;
    }

    /**
     * 操作成功
     */
    public DataResponse(){
        put("code", BaseConstants.SUCCESS);
        put("message", "操作成功");
    }

    /**
     * 操作失败
     * @return
     */
    public static DataResponse fail(){
        return fail(BaseConstants.FAIL, "操作失败");
    }

    /**
     * 操作失败，自定义失败信息
     * @param msg
     * @return
     */
    public static DataResponse fail(String msg){
        return fail(BaseConstants.FAIL, msg);
    }

    private static DataResponse fail(String fail, String msg) {
        DataResponse dataResponse = new DataResponse();
        dataResponse.put("code", fail);
        dataResponse.put("message", msg);
        return dataResponse;
    }

    /**
     * 返回成功，自定义成功消息
     * @param msg
     * @return
     */
    public static DataResponse success(String msg){
        DataResponse dataResponse = new DataResponse();
        dataResponse.put("message", msg);
        return dataResponse;
    }

    /**
     * 返回成功
     * @return
     */
    public static DataResponse success(){
        DataResponse dataResponse = new DataResponse();
        return dataResponse;
    }

    /**
     * 返回成功，加入更多的返回内容
     * @param map
     * @return
     */
    public static DataResponse success(Map<String, Object> map){
        DataResponse dataResponse = new DataResponse();
        dataResponse.putAll(map);
        return dataResponse;
    }


}
