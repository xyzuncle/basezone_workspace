package com.kq.auth.handle;


import com.google.common.reflect.TypeToken;

import com.google.gson.Gson;
import com.kq.common.exception.BaseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.HttpMediaTypeException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpStatusCodeException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@ControllerAdvice
public class GlobalDefaultExceptionHandler {

  private Gson gson = new Gson();
  private static Type mapType = new TypeToken<Map<String, Object>>() {
  }.getType();

  private static final Logger logger = LoggerFactory.getLogger(GlobalDefaultExceptionHandler.class);

  //处理系统内置的Exception
  @ExceptionHandler(Throwable.class)
  public ResponseEntity<Map<String, Object>> exception(HttpServletRequest request, Throwable ex) {
    return handleError(request, INTERNAL_SERVER_ERROR, ex);
  }

  @ExceptionHandler({HttpRequestMethodNotSupportedException.class, HttpMediaTypeException.class})
  public ResponseEntity<Map<String, Object>> badRequest(HttpServletRequest request,
                                                        ServletException ex) {
    return handleError(request, BAD_REQUEST, ex);
  }

  @ExceptionHandler(HttpStatusCodeException.class)
  public ResponseEntity<Map<String, Object>> restTemplateException(HttpServletRequest request,
                                                                   HttpStatusCodeException ex) {
    return handleError(request, ex.getStatusCode(), ex);
  }

  @ExceptionHandler(AccessDeniedException.class)
  public ResponseEntity<Map<String, Object>> accessDeny(HttpServletRequest request,
                                                        AccessDeniedException ex) {
    return handleError(request, FORBIDDEN, ex);
  }

  //处理自定义Exception
  @ExceptionHandler({BaseException.class})
  public ResponseEntity<Map<String, Object>> badRequest(HttpServletRequest request, BaseException ex) {
    return handleError(request, ex);
  }


  private ResponseEntity<Map<String, Object>> handleError(HttpServletRequest request,
                                                          BaseException ex) {
    return handleError(request, ex.getHttpStatus(), ex);
  }


  private ResponseEntity<Map<String, Object>> handleError(HttpServletRequest request,
                                                          HttpStatus status, Throwable ex) {
    String message = ex.getMessage();

    logger.error(message, ex);

    //后期可以接入apollo的日志
  /*  Tracer.logError(ex);*/

    Map<String, Object> errorAttributes = new HashMap<>();
    boolean errorHandled = false;

    if (ex instanceof HttpStatusCodeException) {
      try {
        //try to extract the original error info if it is thrown from apollo programs, e.g. admin service
        errorAttributes = gson.fromJson(((HttpStatusCodeException) ex).getResponseBodyAsString(), mapType);
        status = ((HttpStatusCodeException) ex).getStatusCode();
        errorHandled = true;
      } catch (Throwable th) {
        //ignore
      }
    }

    if (!errorHandled) {
      if(status==null){
          if(ex instanceof  BaseException){
              errorAttributes.put("status",((BaseException) ex).getStatus());
              errorAttributes.put("message", message);
              errorAttributes.put("timestamp",
                      LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
              errorAttributes.put("exception", ex.getClass().getName());
          }
      }
      else{
          errorAttributes.put("status", status.value());
          errorAttributes.put("message", message);
          errorAttributes.put("timestamp",
                  LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
          errorAttributes.put("exception", ex.getClass().getName());
      }


    }

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(APPLICATION_JSON);
    return new ResponseEntity<>(errorAttributes, headers, status);
  }

}
