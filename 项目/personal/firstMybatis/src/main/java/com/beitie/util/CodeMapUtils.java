package com.beitie.util;

import com.beitie.service.GlossaryService;
import com.beitie.service.impl.GlossaryServiceImpl;
import org.springframework.stereotype.Component;

@Component
public class CodeMapUtils {
    private static GlossaryService glossaryService;

    public static String getNameByCategoryAndCode(int category,short code){
        if(CodeMapUtils.glossaryService==null){
            glossaryService= (GlossaryService)AppContextHolder.getBean("glossaryService");
        }
        return glossaryService.getNameByCodeAndCategory(category,code);
    }
}
