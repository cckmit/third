package com.beitie.dao;

import com.beitie.pojo.Account;
import org.springframework.stereotype.Repository;

import java.util.List;
public interface AccountDao {
    List<Account> findAllAccount();
    Account findByName(String name);
    void save(Account account);
    void update(Account account);
    void delete(int id);
}
