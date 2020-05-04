package com.mxg.security.authentication.mobile;

/**
 * @author jiangxiao
 * @Title: smssend
 * @Package
 * @Description: 发送短信接口
 * @date 2020/4/212:03
 */
public interface SmsSend {

    boolean sendSms(String mobile , String content);
}
