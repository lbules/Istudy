package com.course.server.exception;

/**
 * 自定义异常类
 */
public class ValidatorException extends RuntimeException{

    public ValidatorException(String message) {
        super(message);
    }
}
