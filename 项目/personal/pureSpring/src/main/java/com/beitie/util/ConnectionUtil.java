package com.beitie.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
@Component
public class ConnectionUtil {
    //创建一个当前线程的ThreadLocal
    private ThreadLocal<Connection> t=new ThreadLocal<Connection>();
    @Autowired
    private DataSource dataSource;

    /**
     *
     * @return  获得当前线程的连接
     */
    public Connection getThreadLocalConnection(){
        Connection connection=null;
        if(t.get() == null){
            try {
                connection=dataSource.getConnection();
                t.set(connection);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }else{
            connection = t.get();
        }
        return connection;
    }

    /**
     *  解除线程与连接之间的绑定
     */
    public void remove(){
        t.remove();
    }
}
