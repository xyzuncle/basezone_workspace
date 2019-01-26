package com.kq.auth.validate.code.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * 声明认证异常
 */
public class ValidateCodeException extends AuthenticationException {

    public ValidateCodeException(String msg) {
        super(msg);
    }
}