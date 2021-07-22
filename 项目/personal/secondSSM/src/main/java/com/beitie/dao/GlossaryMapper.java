package com.beitie.dao;

import com.beitie.pojo.Glossary;

public interface GlossaryMapper {
    String getNameByCodeAndCategory(Glossary glossary);
}
