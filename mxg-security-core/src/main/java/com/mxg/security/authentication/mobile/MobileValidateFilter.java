package com.mxg.security.authentication.mobile;

import com.mxg.security.authentication.CustomAuthenticationFailureHandler;
import com.mxg.security.authentication.exception.ValidateCodeException;
import com.mxg.security.controller.MobileLoginController;
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
 * @Title: MobileValidateFilter
 * @Package
 * @Description: 手机验证码校验是否正确  （与ImageCodeValidateFilter 图片验证一样）
 * @date 2020/4/812:04
 */
@Component //放到容器中
public class MobileValidateFilter extends OncePerRequestFilter {

    @Autowired
    CustomAuthenticationFailureHandler customAuthenticationFailureHandler;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //当 页面 请求为 手机验证 uri ,则需要验证
        if ("/mobile/form".equals(request.getRequestURI())
                && "post".equalsIgnoreCase(request.getMethod())) {
            try {
                validate(request);
            }catch (AuthenticationException e){
                customAuthenticationFailureHandler.onAuthenticationFailure(request,response,e);
                return ;
            }

        }
        //非手机验证码登录 ，直接放行
        filterChain.doFilter(request,response);

    }

    /**
     * 校验
     * @param request
     */
    private void validate(HttpServletRequest request) {
        //session 中的验证码
        String sessionCode = (String) request.getSession().getAttribute(MobileLoginController.SESSION_MOBILE_KEY);
        // 输入的验证码
        String inputCode = request.getParameter("code");
        //判断
        if (StringUtils.isBlank(inputCode)) {
            throw new ValidateCodeException("手机验证码不能为空！");
        }

        if (!inputCode.equalsIgnoreCase(sessionCode)) {
            throw new ValidateCodeException("输入手机验证码不正确！");
        }
    }


}
