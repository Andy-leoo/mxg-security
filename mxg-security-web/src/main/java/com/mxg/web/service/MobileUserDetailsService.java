package com.mxg.web.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 * @author jiangxiao
 * @Title: MobileUserDetailsService
 * @Package
 * @Description: 通过手机号获取用户信息
 * @date 2020/4/819:48
 */
@Component("mobileUserDetailsService")
public class MobileUserDetailsService implements UserDetailsService {

    Logger LOG = LoggerFactory.getLogger(getClass());

    @Override
    public UserDetails loadUserByUsername(String mobile) throws UsernameNotFoundException {
        LOG.info("请求手机号："+ mobile);
        // 1. 通过手机号查询用户信息
        // 2. 如果有此用户，则查询用户权限
        // 3. 封装用户信息

        return new User("meng","",true,true,true,true,
                AuthorityUtils.commaSeparatedStringToAuthorityList("ADMIN"));
    }
}
