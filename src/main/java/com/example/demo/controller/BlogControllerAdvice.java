package com.example.demo.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice

public class BlogControllerAdvice {
    @ExceptionHandler()
    public String blogControllerAdvice(Exception ex) {
        return "error_page/article_error";
    }
}
