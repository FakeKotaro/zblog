package com.zjw.myblog.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ControllerExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler(Exception.class)
    public ModelAndView exceptionHandle(HttpServletRequest request , Exception e) {
        logger.error("Request URL : {} , Exception : {}" , request.getRequestURI() , e);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("url" , request.getRequestURI());
        modelAndView.addObject("exception" , e);
        modelAndView.setViewName("error/error");
        return modelAndView;
    }
}
