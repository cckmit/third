package com.beitie.test.dao;

import java.util.HashMap;

/**
 * @author betieforever
 * @description 描述
 * @date 2021/10/22
 */
public interface UserDao {
    HashMap getUserInfoById(String statementId,String id);
}
