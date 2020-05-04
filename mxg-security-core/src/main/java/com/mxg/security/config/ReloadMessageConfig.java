package com.mxg.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

/**
 * @author jiangxiao
 * @Title: ReloadMessageConfig
 * @Package
 * @Description: 更改spring-security-core.jar 中默认获取的英文文件，改为中文 。加载认证信息
 * @date 2020/3/1721:15
 */
@Configuration
public class ReloadMessageConfig {

    // 加载中文提示信息
    @Bean
    public ReloadableResourceBundleMessageSource messageSource(){
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        // 注意 ： .properties 不要加到后面  他会自动拼上
        messageSource.setBasename("classpath:org/springframework/security/messages_zh_CN");
        return messageSource;
    }

}
