package com.mxg.security.authentication.mobile;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author jiangxiao
 * @Title: SmsCodeSender
 * @Package
 * @Description: 短信发送实现类
 * @date 2020/4/212:07
 */
public class SmsCodeSender implements SmsSend {

    Logger LOG = LoggerFactory.getLogger(getClass());

    @Override
    public boolean sendSms(String mobile, String content) {
        String smsContent = String.format("机主你好，您的短信验证码 ：" + content);
        LOG.info("mxg 手机号 " +mobile+smsContent);
        return true ;
    }
}
