package com.sy.github.webflux.common;

import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author Sherlock
 * @since 2021/8/31-21:39
 */
@Slf4j
@RestControllerAdvice
public class BusinessExceptionHandler {
    /**
     * 参数校验异常
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Object handleBindException(MethodArgumentNotValidException ex) {
        log.error("MethodArgumentNotValidException", ex);
        final String errMsgBuilder = ex.getBindingResult().getFieldErrors().stream().map(FieldError::getDefaultMessage).collect(Collectors.joining());
        return CommonResult.error(ResultEnum.PARAM_ERROR.getCode(), errMsgBuilder);
    }
    /**
     * 自定义异常
     */
    @ExceptionHandler(BusinessException.class)
    public Object handleBusinessException(BusinessException ex){
        log.error("BusinessException", ex);
        return CommonResult.error(ex.getCode(), ex.getMessage());
    }
    @ExceptionHandler(Exception.class)
    public Object handleException(Exception ex){
        log.error("Exception", ex);
        return CommonResult.error(ResultEnum.UNKNOWN_ERROR.getCode(), ex.getMessage());
    }
}
