package com.beitie.handler;

import com.beitie.bean.User;
import com.beitie.service.GlossaryService;
import com.beitie.service.UserService;
import com.beitie.service.impl.GlossaryServiceImpl;
import com.beitie.util.AppContextHolder;
import com.beitie.util.CodeMapUtils;
import com.beitie.util.exception.CustomException;
import com.beitie.util.exception.ResultCode;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping(value = "/glossary")
public class GlossaryHandler1 {
        static Logger logger = LogManager.getLogger(GlossaryHandler1.class);
    @RequestMapping("/queryNameByCategoryAndCode")
    public String queryUserListInfo(Integer category,short code) {
        GlossaryService glossaryService=(GlossaryService)AppContextHolder.getBean("glossaryService");
        glossaryService.eat();
        String name=CodeMapUtils.getNameByCategoryAndCode(category,code);
        return name;
    }
}
