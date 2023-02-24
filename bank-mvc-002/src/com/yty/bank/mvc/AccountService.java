package com.yty.bank.mvc;

import com.yty.bank.exceptions.AppException;
import com.yty.bank.exceptions.MoneyNotEnoughException;

public class AccountService {

    AccountDao accountDao = new AccountDao();
    public void transfer(String fromAct, String toAct, Double money) throws MoneyNotEnoughException, AppException {
        Account fromAccount = accountDao.selectByActno(fromAct);
        if (fromAccount.getBalance() < money) {
            throw new MoneyNotEnoughException("Not suffcient money");
        }
        Account toAccount = accountDao.selectByActno(toAct);
        fromAccount.setBalance(fromAccount.getBalance() - money);
        toAccount.setBalance(toAccount.getBalance() + money);
        int count = accountDao.update(fromAccount);
        String s = null;
        s.toString();
        count += accountDao.update(toAccount);
        if (count != 2){
            throw new AppException("transfer exception");
        }

    }
}
