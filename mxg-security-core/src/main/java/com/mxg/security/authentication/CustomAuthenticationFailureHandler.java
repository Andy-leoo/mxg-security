package com.mxg.security.authentication;

import com.mxg.base.result.MxgResult;
import com.mxg.security.properties.LoginResponseType;
import com.mxg.security.properties.SecurityProperties;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author jiangxiao
 * @Title: MxgResult
 * @Package
 * @Description: 认证成功处理器  ， 封装JSON数据。
 * @date 2020/3/2316:35
 */
@Component("customAuthenticationFailureHandler")
//public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {
public class CustomAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    Logger LOG = LoggerFactory.getLogger(getClass());

    @Autowired
    SecurityProperties securityProperties;
        /**
    　　* @Description: 认证失败处理器
    　　* @param ${tags}
    　　* @return ${return_type}
    　　* @throws
    　　* @author jiangxiao
    　　* @date 2020/3/23 21:12
    　　*/
    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        if (LoginResponseType.JSON.equals(securityProperties.getAuthentication().getLoginType())){
            //认证失败， 使用httpstatus 自带状态码。
            MxgResult result = MxgResult.build(HttpStatus.UNAUTHORIZED.value(),e.getMessage());
            httpServletResponse.setContentType("application/json;charset=UTF-8");
            httpServletResponse.getWriter().write(result.toJsonString());
        }else {
            // 重写向回认证页面，注意加上 ?error
//            super.setDefaultFailureUrl(securityProperties.getAuthentication().getLoginPage()+"?error");
            //获取上一次请求路径
            String referer = httpServletRequest.getHeader("Referer");
            LOG.info("上一次获取路径 ："+ referer);
//            String lastUrl = StringUtils.substringBefore(referer, "?");
            Object toAuthentication = httpServletRequest.getAttribute("toAuthentication");
            String lastUrl = toAuthentication != null ? securityProperties.getAuthentication().getLoginPage() :
                            StringUtils.substringBefore(referer, "?");
            LOG.info("url :" +lastUrl);
            super.setDefaultFailureUrl(lastUrl+"?error");
            super.onAuthenticationFailure(httpServletRequest,httpServletResponse,e);
        }


    }
}
