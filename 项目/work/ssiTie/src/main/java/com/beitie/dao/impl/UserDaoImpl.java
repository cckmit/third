package com.beitie.dao.impl;

import com.beitie.bean.UserInfo;
import com.beitie.dao.UserDao;
import com.ibatis.sqlmap.client.SqlMapClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.List;

/**
 * @author betieforever
 * @description 描述
 * @date 2021/11/21
 */
@Component
public class UserDaoImpl implements UserDao {
    @Autowired
    SqlMapClient sqlMapClient;
    @Override
    public List<UserInfo> findUserList(String statementId) throws SQLException {
        return sqlMapClient.queryForList("T_User_FindUserList");
    }
}
