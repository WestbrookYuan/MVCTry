package com.yty.bank.service;

import com.yty.bank.exceptions.AppException;
import com.yty.bank.exceptions.MoneyNotEnoughException;

public interface AccountService {
    void transfer(String fromAct, String toAct, Double money) throws MoneyNotEnoughException, AppException;
}
