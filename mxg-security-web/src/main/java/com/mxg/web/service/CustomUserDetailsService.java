package com.mxg.web.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * @author jiangxiao
 * @Title: CustomUserDetailsService
 * @Package
 * @Description: 获取用户身份service
 * @date 2020/3/21 19:04
 */
@Component("customUserDetailsService")
public class CustomUserDetailsService implements UserDetailsService {

    Logger LOG = LoggerFactory.getLogger(CustomUserDetailsService.class);

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LOG.info("请求认证用户 ：" + username);

        //1. 根据请求用户名向数据库中查询用户信息
        if (!"andy".equalsIgnoreCase(username)){ //忽略大小写比对
            throw new UsernameNotFoundException("用户名密码错误！");
        }

        //如果有此信息 ，假设密码为 1234
        String password = passwordEncoder.encode("1234");

        //2. 查询用户权限

        //3. 封装用户信息
        // username用户名,password数据库中的密码,authorities资源权限标识符
        //security 底层会校验是否合法
        return new User(username,password, AuthorityUtils.commaSeparatedStringToAuthorityList("ADMIN"));
    }
}
