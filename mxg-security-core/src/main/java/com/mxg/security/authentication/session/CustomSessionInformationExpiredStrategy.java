package com.mxg.security.authentication.session;

import com.mxg.security.authentication.CustomAuthenticationFailureHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import java.io.IOException;

/**
 * @author jiangxiao
 * @Title: CustomSessionInformationExpiredStrategy
 * @Package
 * @Description: 用户达到一定的session数，调用此方法
 * @date 2020/5/2916:55
 */
public class CustomSessionInformationExpiredStrategy implements SessionInformationExpiredStrategy {

    Logger LOG = LoggerFactory.getLogger(CustomSessionInformationExpiredStrategy.class);

    @Autowired
    CustomAuthenticationFailureHandler customAuthenticationFailureHandler;

    @Override
    public void onExpiredSessionDetected(SessionInformationExpiredEvent event) throws IOException {
        //获取推出的用户信息
        UserDetails userDetail = (UserDetails) event.getSessionInformation().getPrincipal();

        AuthenticationException authenticationException =
                new AuthenticationServiceException(String.format("[%s]用户在另一台电脑登录，您已被迫下线",userDetail.getUsername()));

        //TODO 什么意思
        event.getRequest().setAttribute("toAuthentication",true);

        try {
            customAuthenticationFailureHandler.onAuthenticationFailure(event.getRequest(),event.getResponse(),authenticationException);

        } catch (ServletException e) {

            LOG.info("登出报错");
        }
    }
}
