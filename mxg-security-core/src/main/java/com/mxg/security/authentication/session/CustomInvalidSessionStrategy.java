package com.mxg.security.authentication.session;

import com.mxg.base.result.MxgResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.session.InvalidSessionStrategy;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author jiangxiao
 * @Title: CustomInvalidSessionStrategy
 * @Package
 * @Description: session 失效后的处理逻辑
 * @date 2020/5/2915:22
 */
public class CustomInvalidSessionStrategy implements InvalidSessionStrategy {
    Logger LOG = LoggerFactory.getLogger(CustomInvalidSessionStrategy.class);

    @Override
    public void onInvalidSessionDetected(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // session 失效会调用此方法 ，将浏览器的session清除，不关闭浏览器cookie不会被删除，一直请求都提示session失效
        cancelCookie(request,response);
        MxgResult result = MxgResult.build(HttpStatus.UNAUTHORIZED.value(),"登录已超时，请重新登录");
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(result.toJsonString());
    }

    //这里是参考 AbstractRememberMeServices 中的 清除 cancelCookie 方法
    protected void cancelCookie(HttpServletRequest request, HttpServletResponse response) {
        LOG.debug("Cancelling cookie");
        Cookie cookie = new Cookie("JSESSIONID", null);
        cookie.setMaxAge(0);
        cookie.setPath(getCookiePath(request));
        response.addCookie(cookie);
    }

    private String getCookiePath(HttpServletRequest request) {
        String contextPath = request.getContextPath();
        return contextPath.length() > 0 ? contextPath : "/";
    }

}
