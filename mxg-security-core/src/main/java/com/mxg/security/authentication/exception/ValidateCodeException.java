package com.mxg.security.authentication.exception;


import org.springframework.security.core.AuthenticationException;

/**
 * @author jiangxiao
 * @Title: ValidateCodeException
 * @Package
 * @Description: 验证码异常类
 * @date 2020/4/112:05
 */
public class ValidateCodeException extends AuthenticationException {

    public ValidateCodeException(String detail) {
        super(detail);
    }

    public ValidateCodeException(String detail, Throwable ex) {
        super(detail, ex);
    }
}
