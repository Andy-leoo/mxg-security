package com.mxg.web;

import com.mxg.security.authentication.mobile.SmsSend;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author jiangxiao
 * @Title: MobileSmsCodeSender
 * @Package
 * @Description: smssend实现类
 * @date 2020/4/315:31
 */
public class MobileSmsCodeSender implements SmsSend {

    Logger LOG = LoggerFactory.getLogger(getClass());

    @Override
    public boolean sendSms(String mobile, String content) {
        LOG.info("web应用新短信验证码接口 向手机号 ：" + mobile + "发送验证码 ："+ content);
        return false;
    }
}
