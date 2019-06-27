package com.sw.common.constants.dict;

import com.sw.common.util.StringUtil;

/**
 * 订单售后字典
 */
public enum OrderStatusDict {

    START("SW0701", "未付款"),
    PAY("SW0702", "已付款"),
    RECEIVE("SW0703", "已收货"),
    APPRAISE("SW0704", "已评价"),
    COMPLETE("SW0705", "已完成"),
    CANCEL("SW0706", "已取消");

    String code;
    String message;

    OrderStatusDict(String code, String message){
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * 根据编码获取编码信息
     * @param code
     * @return
     */
    public static String codeToMessage(String code){
        if(StringUtil.isEmpty(code)){
            return "";
        }else{
            for(OrderStatusDict sexDict : OrderStatusDict.values()){
                if(code.equals(sexDict.getCode())){
                    return sexDict.getMessage();
                }
            }
            return "";
        }
    }
}
