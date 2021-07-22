package com.beitie;

import com.beitie.pojo.Account;
import com.beitie.service.AccountService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
@ComponentScan
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring-simple.xml","classpath:spring-aop.xml"})
public class AccountTest {
    @Autowired
    @Qualifier("proxyAccountService")
    private AccountService accountService;
    @Test
    public void transferTest(){
        accountService.transfer("aaa","bbb",100f);
    }
    @Test
    public void findByNameTest(){
        Account account=accountService.findOne("aaa");
        System.out.println(account);
    }

    @Test
    public void findAllAccountTest(){
        List<Account> allAccount = accountService.findAllAccount();
        System.out.println(allAccount);
    }
    @Test
    public void deleteAccountTest(){
        accountService.delete(1);
    }
}
