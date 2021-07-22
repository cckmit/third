package com.beitie.util.exception;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@ControllerAdvice
public class GlobalRuntimeExceptionResolver {
    private static final Logger log = Logger.getLogger(GlobalRuntimeExceptionResolver.class);
    @ExceptionHandler(Exception.class)
    public ModelAndView handException(Exception e, HttpServletRequest request, HttpServletResponse response){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("exception", BusinessRuntimeException.newInstance(ResultCode.UNKNOWN_ERROR));
        modelAndView.setViewName("error");
        log.error("{} exception"+request.getRequestURI(),e);
        return modelAndView;
    }
    @ExceptionHandler(BusinessRuntimeException.class)
    public ModelAndView handBusinessRuntimeException(BusinessRuntimeException e, HttpServletRequest request, HttpServletResponse response){
        ModelAndView modelAndView= new ModelAndView();
        modelAndView.setViewName("error");
        modelAndView.addObject("exception",e.getResultCode());
        log.error("{} exception"+request.getRequestURI(),e);
        return modelAndView;
    }
}
