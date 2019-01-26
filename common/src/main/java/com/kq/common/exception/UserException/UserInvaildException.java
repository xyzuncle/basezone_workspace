package com.kq.common.exception.UserException;


import com.kq.common.exception.BaseException;

public class UserInvaildException extends BaseException {

    public UserInvaildException(String message, int status) {
        super(message, status);

    }
}
