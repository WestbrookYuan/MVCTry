package com.yty.bank.dao;

import com.yty.bank.bean.Account;

import java.util.List;

public interface AccountDao {
    int insert(Account account);
    int deleteById(Integer id);
    int update(Account account);
    Account selectByActno(String actno);
    List<Account> selectAll();
}
