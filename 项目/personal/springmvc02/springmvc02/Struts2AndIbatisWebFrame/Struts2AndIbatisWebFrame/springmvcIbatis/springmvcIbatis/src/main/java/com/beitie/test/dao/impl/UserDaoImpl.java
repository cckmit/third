package com.beitie.test.dao.impl;

import com.beitie.test.dao.UserDao;
import com.ibatis.sqlmap.client.SqlMapClient;

import java.sql.SQLException;
import java.util.HashMap;

/**
 * @author betieforever
 * @description 描述
 * @date 2021/10/22
 */
public class UserDaoImpl implements UserDao {
    private SqlMapClient sqlMapClient;

    public SqlMapClient getSqlMapClient() {
        return sqlMapClient;
    }

    public void setSqlMapClient(SqlMapClient sqlMapClient) {
        this.sqlMapClient = sqlMapClient;
    }

    public HashMap getUserInfoById(String statementId, String id){
        try {
            return (HashMap) sqlMapClient.queryForObject("query","");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }
}
