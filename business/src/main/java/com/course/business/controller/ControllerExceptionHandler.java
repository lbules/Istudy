package com.course.business.controller;

import com.course.server.dto.ResponseDto;
import com.course.server.exception.ValidatorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * controller的异常处理
 *
 */
@ControllerAdvice
public class ControllerExceptionHandler {
private static final Logger LOG = LoggerFactory.getLogger(ControllerExceptionHandler.class);


    @ExceptionHandler(value = ValidatorException.class)
    @ResponseBody
    public ResponseDto validatorExceptionHandler(ValidatorException e) {
        ResponseDto responseDto = new ResponseDto();
        responseDto.setSuccess(false);

        //打印日志输出
        LOG.warn(e.getMessage());
//        responseDto.setMessage(e.getMessage()); ,不直接返回信息，暴露参数校验规则
        responseDto.setMessage("请求参数异常！");
        return responseDto;
    }
}
