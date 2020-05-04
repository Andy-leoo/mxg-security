package com.mxg.security.controller;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * @author jiangxiao
 * @Title: CustomLoginController
 * @Package controller
 * @Description: 使用用户自定义认证页面
 * @date 2020/3/1711:02
 */
@Controller
public class CustomLoginController {

    Logger LOG = LoggerFactory.getLogger(getClass());

    public static final String SESSION_KEY = "SESSION_KEY_IMAGE_CODE";

    @Autowired
    private DefaultKaptcha defaultKaptcha ;

    /**
    　　* @Description: 前往认证登录页面
    　　* @param ${tags}
    　　* @return ${return_type}
    　　* @throws
    　　* @author jiangxiao
    　　* @date 2020/3/17 11:04
    　　*/
    @RequestMapping("/login/page")
    public String login(HttpServletRequest request , HttpServletResponse response){
        // 响应 登录页面  classpath ： /templates/login.html
        return "login";
    }

    /**
     * 获取验证码
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping("/code/image")
    public void imageCode(HttpServletRequest request,HttpServletResponse response) throws IOException {
        //1. 获取验证码
        String code = defaultKaptcha.createText();
        LOG.info("获取的验证码 ：" + code);
        //2. 将验证码 放入session
        request.getSession().setAttribute(SESSION_KEY,code);
        //3. 获取验证码图片
        BufferedImage image = defaultKaptcha.createImage(code);
        //4. 将验证码图片通过IO 写出去
        ServletOutputStream outputStream = response.getOutputStream();
        ImageIO.write(image,"jpg",outputStream);

    }


}
