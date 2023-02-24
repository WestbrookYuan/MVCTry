package com.yty.bank.web;

import com.yty.bank.exceptions.MoneyNotEnoughException;
import com.yty.bank.service.AccountService;
import com.yty.bank.service.impl.AccountServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {"/transfer"})
public class AccountServlet extends HttpServlet {
    private AccountService accountService = new AccountServiceImpl();
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String fromActno = request.getParameter("fromActno");
        String toActno = request.getParameter("toActno");
        Double money = Double.parseDouble(request.getParameter("money"));


        try {
            accountService.transfer(fromActno, toActno, money);
            response.sendRedirect(request.getContextPath()+"/success.jsp");
        }
        catch (MoneyNotEnoughException moneyNotEnoughException){
            response.sendRedirect(request.getContextPath()+"/moneynotenough.jsp");
        }
        catch (Exception e){
            response.sendRedirect(request.getContextPath()+"/error.jsp");
        }

    }
}
