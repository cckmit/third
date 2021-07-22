package com.beitie.util.resolver;

import com.beitie.util.exception.CustomException;
import com.beitie.util.exception.ResultCode;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CustomResolver implements HandlerExceptionResolver {
    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        CustomException customException=null;
        if(ex instanceof CustomException){
            customException=(CustomException) ex;
        }else{
            customException=new CustomException(ResultCode.UNKNOWN_ERROR);
        }
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.addObject("exception", customException.getResultCode());
        modelAndView.setViewName("error");
        return modelAndView;
    }
}
