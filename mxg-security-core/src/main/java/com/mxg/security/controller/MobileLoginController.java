package com.mxg.security.controller;

import com.mxg.base.result.MxgResult;
import com.mxg.security.authentication.mobile.SmsSend;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @author jiangxiao
 * @Title: MobileLoginController
 * @Package
 * @Description: 用户切换手机验证码登录页登录
 * @date 2020/4/315:44
 */
@Controller
public class MobileLoginController {

    Logger LOG = LoggerFactory.getLogger(getClass());

    public static final String SESSION_MOBILE_KEY = "SESSION_MOBILE_KEY";

    @Autowired
    SmsSend smsSend;

    /**
     * 跳转手机验证码登录
     * @return
     */
    @RequestMapping("/mobile/page")
    public String mobileLogin(){
        return "login-mobile";
    }


    @ResponseBody
    @RequestMapping("/code/mobile")
    public MxgResult sendMobileCode(HttpServletRequest request){
        // 随机生成 验证码
        String code = RandomStringUtils.randomNumeric(4);
        LOG.info("存入session mobile code ：" + code);
        // 将验证码保存到session
        request.getSession().setAttribute(SESSION_MOBILE_KEY , code);
        // 发送验证码到用户手机上
        String mobile = request.getParameter("mobile");
        smsSend.sendSms(mobile,code);

        return MxgResult.ok();
    }

}
