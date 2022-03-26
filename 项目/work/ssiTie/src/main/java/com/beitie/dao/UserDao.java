package com.beitie.dao;

import com.beitie.bean.UserInfo;

import java.sql.SQLException;
import java.util.List;

/**
 * @author betieforever
 * @description 描述
 * @date 2021/11/21
 */
public interface UserDao {
    List<UserInfo> findUserList(String statementId) throws SQLException;
}
