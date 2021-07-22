package com.beitie.dao.impl;

import com.beitie.dao.AccountDao;
import com.beitie.pojo.Account;
import com.beitie.util.ConnectionUtil;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository("accountDao")
public class AccountDaoImpl implements AccountDao {
    @Autowired
    private QueryRunner queryRunner;
    @Autowired
    private ConnectionUtil connectionUtil;
    public List<Account> findAllAccount() {
        List<Account> list =null;
        try {
            list=queryRunner.query(connectionUtil.getThreadLocalConnection(),"select * from account ", new BeanListHandler<Account>(Account.class));
        }catch (Exception e){
            throw new RuntimeException(e);
        }
        return list;
    }

    public Account findByName(String name) {
        Account account = null;
        try {
            account=queryRunner.query(connectionUtil.getThreadLocalConnection(),"select * from account where name = ?",new BeanHandler<Account>(Account.class) ,name);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
        return account;
    }

    public void save(Account account) {
        try {
            queryRunner.insert(connectionUtil.getThreadLocalConnection(),"insert into account(name,money) values(?,?)",new BeanHandler<Account>(Account.class) ,account.getName(),account.getMoney());
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    public void update(Account account) {
        try {
            queryRunner.update(connectionUtil.getThreadLocalConnection(),"update account set name=?,money=? where id = ?",account.getName(),account.getMoney(),account.getId());
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    public void delete(int id) {
        try {
            queryRunner.update(connectionUtil.getThreadLocalConnection(),"delete from account where id =?", id);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
