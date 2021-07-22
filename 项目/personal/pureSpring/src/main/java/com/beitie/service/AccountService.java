package com.beitie.service;

import com.beitie.pojo.Account;

import java.util.List;

public interface AccountService {
    List<Account> findAllAccount();
    Account findOne(String name);
    void save(Account account);
    void update(Account account);
    void delete(int id);
    void transfer(String sourceName,String destName,float money);
}
