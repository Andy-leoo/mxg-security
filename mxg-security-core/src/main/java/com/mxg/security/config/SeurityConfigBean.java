package com.mxg.security.config;

import com.mxg.security.authentication.mobile.SmsCodeSender;
import com.mxg.security.authentication.mobile.SmsSend;
import com.mxg.security.authentication.session.CustomInvalidSessionStrategy;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.session.InvalidSessionStrategy;

/**
 * @author jiangxiao
 * @Title: SeurityConfigBean
 * @Package
 * @Description:
 *
 * 也可以直接在类上加上 @Component 注解，但是不利于应用的扩展，因为短信服务提供商有非常多，实现就
 * 不一样，所以采用下面方式添加到容器
 *
 * 1. 手机验证码发送配置类
 *
 * 2. 注入session失效策略实例
 *
 * @date 2020/4/311:06
 */
@Configuration
public class SeurityConfigBean {


    /**
     * 注入session失效策略实例
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(InvalidSessionStrategy.class)
    public InvalidSessionStrategy invalidSessionStrategy(){
        return new CustomInvalidSessionStrategy();
    }

    /**
     *
     *  @ConditionalOnMissingBean(SmsSend.class)
     *  默认采用SmsCodeSender实例 ，但如果容器中有其他 SmsSend 类型的实例，则当前实例失效
     *
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(SmsSend.class)
    public SmsSend smsSend(){
        return new SmsCodeSender();
    }
}
