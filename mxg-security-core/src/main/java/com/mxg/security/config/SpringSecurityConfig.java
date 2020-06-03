package com.mxg.security.config;

import com.mxg.security.authentication.code.ImageCodeValidateFilter;
import com.mxg.security.authentication.mobile.MobileAuthenticationConfig;
import com.mxg.security.authentication.mobile.MobileAuthenticationFailter;
import com.mxg.security.authentication.mobile.MobileValidateFilter;
import com.mxg.security.properties.SecurityProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.session.InvalidSessionStrategy;
import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;

import javax.sql.DataSource;

/**
 *
 */
@Configuration //标示为配置类
@EnableWebSecurity //启动springsecurity过滤器链功能
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
    Logger LOG  = LoggerFactory.getLogger(getClass());

    @Autowired
    private SecurityProperties properties;

    @Autowired
    private UserDetailsService customUserDetailsService;

    @Autowired
    private AuthenticationSuccessHandler customAuthenticationSuccessHandler;

    @Autowired
    private AuthenticationFailureHandler customAuthenticationFailureHandler;

    @Autowired
    private ImageCodeValidateFilter imageCodeValidateFilter;

    @Autowired
    private MobileValidateFilter mobileValidateFilter;

    @Autowired
    private MobileAuthenticationConfig mobileAuthenticationConfig;

    @Autowired
    DataSource dataSource;

    @Bean
    public PasswordEncoder passwordEncoder(){
        //设置加密方式
        return new BCryptPasswordEncoder();
    }

    //记住我功能
    @Bean
    public JdbcTokenRepositoryImpl jdbcTokenRepository(){
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(dataSource);
        //是否自动创建表， 可以手动创建，这里 选择true 是 自动创建 但是只会需要将代码注释 要不然会报错。
//        jdbcTokenRepository.setCreateTableOnStartup(true);
        return jdbcTokenRepository;
    }

    // ctrl + o 重写

    /**
　　* @Description:
        身份认证过滤器
        1. 认证信息提供方式（用户名，密码，当前用户的资源权限）
        2. 可采的内存存储方式，也可采用数据库方式等。
　　* @param ${tags}
　　* @return ${return_type}
　　* @throws
　　* @author jiangxiao
　　* @date 2020/3/14 10:53
　　*/
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//          用户信息存储在内存中
//        String password = passwordEncoder().encode("1234");
//        LOG.info("明文加密后密码 ：" + password);
        //用户信息存储在内存中
//        auth.inMemoryAuthentication()
//                .withUser("andy")//账号
//                .password(password)//密码
//                .authorities("ADMIN");//权限标识
        auth.userDetailsService(customUserDetailsService);
    }


    /**
     * 注入session失败策略
     */
    @Autowired
    private InvalidSessionStrategy invalidSessionStrategy;

    @Autowired
    private SessionInformationExpiredStrategy sessionInformationExpiredStrategy;
    /**
    　　* @Description:
            资源权限配置（过滤器链）
            1. 拦截哪些资源
            2. 资源所对应的角色权限
            3. 定义认证方式 httpbasic  httpform
            4. 定制登录页面，登录请求地址，错误处理方式
            5. 自定义 springsecurity 过滤器等
    　　* @param ${tags}
    　　* @return ${return_type}
    　　* @throws
    　　* @author jiangxiao
    　　* @date 2020/3/14 10:51
    　　*/
    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        http.httpBasic()
        http.addFilterBefore(mobileValidateFilter,UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(imageCodeValidateFilter, UsernamePasswordAuthenticationFilter.class)
                .formLogin() //表单认证
                .loginPage(properties.getAuthentication().getLoginPage()) // 交给 /login/page 响应认证登录页面
                .loginProcessingUrl(properties.getAuthentication().getLoginProcessingUrl())// 登录表单提交处理Url 默认是/login
                .usernameParameter(properties.getAuthentication().getUsernameParameter())// 默认用户名属性名是username
                .passwordParameter(properties.getAuthentication().getPasswordParameter())// 默认密码属性名是password
                .successHandler(customAuthenticationSuccessHandler) //认证成功处理器
                .failureHandler(customAuthenticationFailureHandler) //认证失败处理器
//                .loginPage("/login/page") // 交给 /login/page 响应认证登录页面
//                .loginProcessingUrl("/login/form")// 登录表单提交处理Url 默认是/login
//                .usernameParameter("name")// 默认用户名属性名是username
//                .passwordParameter("pwd")// 默认密码属性名是password

                .and()
                .authorizeRequests()//认证请求
                .antMatchers(properties.getAuthentication().getLoginPage(),
                            properties.getAuthentication().getImageCodeUrl(),
                            properties.getAuthentication().getMobileCodeUrl(),
                            properties.getAuthentication().getMobilePage()).permitAll() //放行跳转认证请求
                .anyRequest().authenticated() //所有进入应用的http请求都要进行认证
                .and()
                .rememberMe() //记住我功能
                .tokenRepository(jdbcTokenRepository()) //保存登录信息
                .tokenValiditySeconds(60*60*24*7) //记住我有效时长
                .and()
                .sessionManagement()
                .invalidSessionStrategy(invalidSessionStrategy) // session 失败后处理逻辑
                .maximumSessions(1)//用户在系统中的最大的session数
                .expiredSessionStrategy(sessionInformationExpiredStrategy)//如果用户达到最大的session数，则调用此处实现
              //  .maxSessionsPreventsLogin(true)//当用户达到最大session数，则不允许后面进行登录

        ;

        // 将手机相关的配置绑定过滤器链上
        http.apply(mobileAuthenticationConfig);
    }

    /**
    　　* @Description: 释放静态资源
    　　* @param ${tags}
    　　* @return ${return_type}
    　　* @throws
    　　* @author jiangxiao
    　　* @date 2020/3/17 11:37
    　　*/
    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers(properties.getAuthentication().getStaticPaths());
    }
}
