package com.mxg.security.authentication.mobile;

import com.mxg.security.authentication.CustomAuthenticationFailureHandler;
import com.mxg.security.authentication.CustomAuthenticationSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

/**
 * @author jiangxiao
 * @Title: MobileAuthenticationConfig
 * @Package
 * @Description: 自定义认证配置，将新建的mobile组件绑定起来。添加到容器中
 * @date 2020/4/920:28
 */
@Component
public class MobileAuthenticationConfig
        extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    @Autowired
    CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;

    @Autowired
    CustomAuthenticationFailureHandler customAuthenticationFailureHandler;

    @Autowired
    UserDetailsService mobileUserDetailsService;

    @Override
    public void configure(HttpSecurity httpSecurity) throws Exception {
        //创建校验手机号过滤器实例
        MobileAuthenticationFailter mobileAuthenticationFailter = new MobileAuthenticationFailter();

        //接收AuthenticationManager 认证管理器
        mobileAuthenticationFailter
                .setAuthenticationManager(httpSecurity.getSharedObject(AuthenticationManager.class));

        //为了实现手机登录也拥有记住我的功能,将RememberMeServices传入
        mobileAuthenticationFailter
                .setRememberMeServices(httpSecurity.getSharedObject(RememberMeServices.class));

        //采用哪个成功，失败处理器
        mobileAuthenticationFailter.setAuthenticationSuccessHandler(customAuthenticationSuccessHandler);
        mobileAuthenticationFailter.setAuthenticationFailureHandler(customAuthenticationFailureHandler);

        //为provider指定明确的 mobileUserDetailsService 来查询用户信息
        MobileAuthenticationProvider provider = new MobileAuthenticationProvider();
        provider.setUserDetailsService(mobileUserDetailsService);

        // 将 provider 绑定到 HttpSecurity 上面， 并且将 手机认证加到 用户名密码认证之后
        httpSecurity.authenticationProvider(provider)
                    .addFilterAfter(mobileAuthenticationFailter, UsernamePasswordAuthenticationFilter.class);


    }
}
