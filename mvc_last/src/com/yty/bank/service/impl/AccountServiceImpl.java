package com.yty.bank.service.impl;

import com.yty.bank.bean.Account;
import com.yty.bank.dao.AccountDao;
import com.yty.bank.dao.impl.AccountDaoImpl;
import com.yty.bank.exceptions.AppException;
import com.yty.bank.exceptions.MoneyNotEnoughException;
import com.yty.bank.service.AccountService;
import com.yty.bank.utils.DBUtil;

import java.sql.Connection;
import java.sql.SQLException;

public class AccountServiceImpl implements AccountService {

    AccountDao accountDao = new AccountDaoImpl();
    Connection conn = null;
    public void transfer(String fromAct, String toAct, Double money) throws MoneyNotEnoughException, AppException {
        try {
            conn = DBUtil.getConnection();
            conn.setAutoCommit(false);

            Account fromAccount = accountDao.selectByActno(fromAct);
            if (fromAccount.getBalance() < money) {
                throw new MoneyNotEnoughException("Not suffcient money");
            }
            Account toAccount = accountDao.selectByActno(toAct);
            fromAccount.setBalance(fromAccount.getBalance() - money);
            toAccount.setBalance(toAccount.getBalance() + money);
            int count = accountDao.update(fromAccount);
//            String s = null;
////            s.toString();
            count += accountDao.update(toAccount);
            if (count != 2){
                throw new AppException("transfer exception");
            }
            conn.commit();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            if (conn != null){
                try {
                    conn.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }

        }

    }

}
