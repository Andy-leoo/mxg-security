package com.mxg.security.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author jiangxiao
 * @Title: SecurityPropercities
 * @Package
 * @Description: 读取yml文件自定义属性
 * @date 2020/3/2011:22
 */
@Component
@ConfigurationProperties(prefix = "mxg.security")
public class SecurityProperties {

    //将application。yml 中的属性绑定到此对象中   authentication 别名一定要相同
    private AuthenticationProperties authentication;

    public void setAuthentication(AuthenticationProperties authentication) {
        this.authentication = authentication;
    }

    public AuthenticationProperties getAuthentication() {
        return authentication;
    }
}
