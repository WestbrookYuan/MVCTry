package com.yty.bank.mvc;

import com.yty.bank.exceptions.AppException;
import com.yty.bank.exceptions.MoneyNotEnoughException;
import com.yty.bank.utils.DBUtil;

import java.sql.Connection;
import java.sql.SQLException;

public class AccountService {

    AccountDao accountDao = new AccountDao();
    Connection conn = null;
    public void transfer(String fromAct, String toAct, Double money) throws MoneyNotEnoughException, AppException {
        try {
            conn = DBUtil.getConnection();
            conn.setAutoCommit(false);

            Account fromAccount = accountDao.selectByActno(fromAct, conn);
            if (fromAccount.getBalance() < money) {
                throw new MoneyNotEnoughException("Not suffcient money");
            }
            Account toAccount = accountDao.selectByActno(toAct, conn);
            fromAccount.setBalance(fromAccount.getBalance() - money);
            toAccount.setBalance(toAccount.getBalance() + money);
            int count = accountDao.update(fromAccount,conn);
//            String s = null;
//            s.toString();
            count += accountDao.update(toAccount, conn);
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
