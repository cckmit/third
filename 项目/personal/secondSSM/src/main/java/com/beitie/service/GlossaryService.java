package com.beitie.service;

import com.beitie.pojo.Glossary;

public interface GlossaryService {
    String getNameByCodeAndCategory(int category,short code);
}
