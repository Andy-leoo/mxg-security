package com.mxg.security.authentication.mobile;

import org.apache.commons.lang.StringUtils;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * @author jiangxiao
 * @Title: MobileAuthenticationProvider
 * @Package
 * @Description: 手机认证提供者
 * @date 2020/4/812:49
 */
public class MobileAuthenticationProvider implements AuthenticationProvider {

    UserDetailsService userDetailsService;

    /**
     * 处理认证
     * 1. 通过手机号 去数据库查询用户信息（UserDetailsService）
     * 2. 在重新构建认证信息
     * @param authentication
     * @return
     * @throws AuthenticationException
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        MobileAuthenticationToken mobileAuthenticationToken = (MobileAuthenticationToken) authentication;
        //获取用户收入手机号
        String mobile = (String) mobileAuthenticationToken.getPrincipal();
        //查询数据库
        UserDetails userDetails = userDetailsService.loadUserByUsername(mobile);

        if (userDetails==null) {
            throw new AuthenticationServiceException("手机号未注册！");
        }

        //查询到 用户信息 ，认证通过  需要重构 MobileAuthenticationToken
        MobileAuthenticationToken authenticationToken = new MobileAuthenticationToken(userDetails,userDetails.getAuthorities());

        authenticationToken.setDetails(mobileAuthenticationToken.getDetails());


        return authenticationToken;
    }

    /**
     * 通过此方法 来判断采用哪个AuthenticationProvider
     * @param authentication
     * @return
     */
    @Override
    public boolean supports(Class<?> authentication) {
        return MobileAuthenticationToken.class.isAssignableFrom(authentication);
    }

    /**
     * 注入 MobileUserDetailsService
     * @param userDetailsService
     */
    public void setUserDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }
}
