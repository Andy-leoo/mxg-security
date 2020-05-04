package com.mxg.security.authentication;

import com.alibaba.fastjson.JSON;
import com.mxg.base.result.MxgResult;
import com.mxg.security.properties.LoginResponseType;
import com.mxg.security.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
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
@Component("customAuthenticationSuccessHandler")
//public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
public class CustomAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler{

    @Autowired
    SecurityProperties securityProperties;
    /**
    　　* @Description: 认证成功处理逻辑
                        authentication 封装了用户信息UserDetails 访问IP等
    　　* @param ${tags}
    　　* @return ${return_type}
    　　* @throws
    　　* @author jiangxiao
    　　* @date 2020/3/23 17:24
    　　*/
    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        if (LoginResponseType.JSON.equals(securityProperties.getAuthentication().getLoginType())){
            //当认证成功后，相应JSON 数据给前端。
            MxgResult r = MxgResult.ok("认证成功");
            httpServletResponse.setContentType("application/json;charset=UTF-8");
            httpServletResponse.getWriter().write(r.toJsonString());
        }else {
            ////重定向到上次请求的地址上，引发跳转到认证页面的地址
            logger.info("重定向 "+ JSON.toJSONString(authentication));
            super.onAuthenticationSuccess(httpServletRequest,httpServletResponse,authentication);
        }




    }
}
