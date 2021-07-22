package com.beitie.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
@Component
public class TransactionUtil {
    @Autowired
    private  ConnectionUtil connectionUtil;
    public void beginTransaction(){
        try {
            connectionUtil.getThreadLocalConnection().setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void commitTransaction(){
        try {
            connectionUtil.getThreadLocalConnection().commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public  void rollbackTransaction(){
        try {
            connectionUtil.getThreadLocalConnection().rollback();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void releaseTransaction(){
        try {
            connectionUtil.getThreadLocalConnection().close();
            connectionUtil.remove();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
