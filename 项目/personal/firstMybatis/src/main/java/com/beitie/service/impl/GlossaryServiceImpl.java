package com.beitie.service.impl;

import com.beitie.bean.Glossary;
import com.beitie.mapper.GlossaryMapper;
import com.beitie.service.GlossaryService;
import org.springframework.beans.factory.annotation.Autowired;

public class GlossaryServiceImpl implements GlossaryService {
    @Autowired
    private GlossaryMapper glossaryMapper;

    public GlossaryMapper getGlossaryMapper() {
        return glossaryMapper;
    }

    public void setGlossaryMapper(GlossaryMapper glossaryMapper) {
        this.glossaryMapper = glossaryMapper;
    }

    @Override
    public String getNameByCodeAndCategory(int category, short code) {
        Glossary glossary=new Glossary();
        glossary.setCategory(category);
        glossary.setCode(code);
        return glossaryMapper.getNameByCodeAndCategory(glossary);
    }

    @Override
    public void eat() {
        System.out.println("我正在吃东西");
    }
}
