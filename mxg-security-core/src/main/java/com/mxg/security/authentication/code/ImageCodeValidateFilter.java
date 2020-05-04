package com.mxg.security.authentication.code;

import com.mxg.security.authentication.CustomAuthenticationFailureHandler;
import com.mxg.security.authentication.exception.ValidateCodeException;
import com.mxg.security.controller.CustomLoginController;
import com.mxg.security.properties.SecurityProperties;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author jiangxiao
 * @Title: ImageCodeValidateFilter
 * @Package
 * @Description: 验证码校验过滤器
 *   OncePerRequestFilter  在所有请求调用之前调用一次。
 * @date 2020/3/3115:26
 */
@Component("imageCodeValidateFilter")//添加至容器当中
public class ImageCodeValidateFilter extends OncePerRequestFilter {

    @Autowired
    SecurityProperties securityProperties;

    @Autowired
    CustomAuthenticationFailureHandler customAuthenticationFailureHandler;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        // 如果是POST 方式的登录请求，则校验验证码是否正确。
        if (request.getRequestURI().equals(securityProperties.getAuthentication().getLoginProcessingUrl())
                && request.getMethod().equalsIgnoreCase("post")) {
            try {
                //验证码合法性
                validate(request);
            } catch (AuthenticationException e) {
                //交给失败处理器
                customAuthenticationFailureHandler.onAuthenticationFailure(request,response,e);
                //一定要结束
                return ;
            }
        }

        filterChain.doFilter(request,response);
    }

    private void validate(HttpServletRequest request) throws ValidateCodeException {
        //获取session 存的验证码
        String sessionCode = (String) request.getSession().getAttribute(CustomLoginController.SESSION_KEY);
        //获取用户输入的验证码
        String  code = (String) request.getParameter("code");

        //判断是否正确
        if (StringUtils.isBlank(code)) {
            throw new ValidateCodeException("验证码不能为空");
        }

        if (!code.equalsIgnoreCase(sessionCode)) {
            throw new ValidateCodeException("验证码输入不正确");
        }


    }
}
