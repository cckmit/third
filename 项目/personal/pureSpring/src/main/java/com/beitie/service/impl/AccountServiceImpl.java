package com.beitie.service.impl;

import com.beitie.dao.AccountDao;
import com.beitie.pojo.Account;
import com.beitie.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
@Service("accountService")
public class AccountServiceImpl implements AccountService {
    @Autowired
    private AccountDao accountDao;
    public List<Account> findAllAccount() {
        return accountDao.findAllAccount();
    }

    public Account findOne(String name) {
        return  accountDao.findByName(name);
    }

    public void save(Account account) {

    }

    public void update(Account account) {

    }

    public void delete(int id) {
        System.out.println("删除了数据");
    }

    public void transfer(String sourceName, String destName, float money) {
        Account sourceAcc = accountDao.findByName(sourceName);
        Account destAcc = accountDao.findByName(destName);

        System.out.println(sourceAcc);
        System.out.println(destAcc);
        sourceAcc.setMoney(sourceAcc.getMoney()-money);
        destAcc.setMoney(destAcc.getMoney()+money);
        accountDao.update(sourceAcc);
        accountDao.update(destAcc);
        System.out.println(accountDao.findAllAccount());
    }

}
