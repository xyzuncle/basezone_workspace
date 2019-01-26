package com.kq.common.handle;



import com.kq.common.exception.BaseException;
import com.kq.common.exception.UserException.UserInvaildException;
import com.kq.common.msg.BaseResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by ace on 2017/9/8.
 */
@ControllerAdvice("com.kq.auth")
@ResponseBody
public class GlobalExceptionHandler {
    private Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(BaseException.class)
    public BaseResponse baseExceptionHandler(HttpServletResponse response, BaseException ex) {
        logger.error(ex.getMessage(),ex);
        response.setStatus(500);
        return new BaseResponse(ex.getStatus(), ex.getMessage());
    }

   /* @ExceptionHandler(Exception.class)
    public BaseResponse otherExceptionHandler(HttpServletResponse response, Exception ex) {
        response.setStatus(500);
        logger.error(ex.getMessage(),ex);
        return new BaseResponse(CommonConstants.EX_OTHER_CODE, ex.getMessage());
    }*/

  /*  @ExceptionHandler(ClientTokenException.class)
    public BaseResponse clientTokenExceptionHandler(HttpServletResponse response, ClientTokenException ex) {
        response.setStatus(403);
        logger.error(ex.getMessage(),ex);
        return new BaseResponse(ex.getStatus(), ex.getMessage());
    }*/

    @ExceptionHandler(UserInvaildException.class)
    public BaseResponse userInvaildExceptionHandler(HttpServletResponse response, UserInvaildException ex) {
        response.setStatus(401);
        logger.error(ex.getMessage(),ex);
        return new BaseResponse(ex.getStatus(), ex.getMessage());
    }
}
