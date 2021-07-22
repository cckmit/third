package com.beitie.mapper;

import com.beitie.bean.Glossary;

public interface GlossaryMapper {
    String getNameByCodeAndCategory(Glossary glossary);
}
