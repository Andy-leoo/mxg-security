package com.mxg.security.config;

import com.mxg.security.authentication.mobile.SmsCodeSender;
import com.mxg.security.authentication.mobile.SmsSend;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author jiangxiao
 * @Title: SeurityConfigBean
 * @Package
 * @Description: 手机验证码发送配置类
 *
 * 也可以直接在类上加上 @Component 注解，但是不利于应用的扩展，因为短信服务提供商有非常多，实现就
 * 不一样，所以采用下面方式添加到容器
 * @date 2020/4/311:06
 */
@Configuration
public class SeurityConfigBean {

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
