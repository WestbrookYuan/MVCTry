package com.yty.bank.web.servlet;

import com.yty.bank.exceptions.AppException;
import com.yty.bank.exceptions.MoneyNotEnoughException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

@WebServlet(urlPatterns = {"/transfer"})
public class AccountTransferServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String fromActno = request.getParameter("fromActno");
        String toActno = request.getParameter("toActno");
        double money = Double.parseDouble(request.getParameter("money"));
        System.out.println(fromActno + toActno + money);

        Connection conn = null;
        PreparedStatement ps = null;
        PreparedStatement ps2 = null;
        PreparedStatement ps3 = null;
        ResultSet rs = null;
        double balance = 0;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mvc?useSSL=false&serverTimezone=UTC", "root", "12345678");
            String sql1= "select id, actno, balance from t_act where actno=?";
            ps = conn.prepareStatement(sql1);
            ps.setString(1, fromActno);
            rs = ps.executeQuery();
            if (rs.next()){
               balance  = rs.getDouble("balance");
            }
            if (balance < money){
                throw new MoneyNotEnoughException("not enough money");
            }
            conn.setAutoCommit(false);
            String sql2 = "update t_act set balance = balance - ? where actno = ?";
            ps2 = conn.prepareStatement(sql2);
            ps2.setDouble(1, money);
            ps2.setString(2, fromActno);
            int count = ps2.executeUpdate();

            String sql3 = "update t_act set balance = balance + ? where actno = ?";
            ps3 = conn.prepareStatement(sql3);
            ps3.setDouble(1, money);
            ps3.setString(2, toActno);
            count += ps3.executeUpdate();

            if (count != 2){
                throw new AppException("app exception");
            }
            conn.commit();
            out.print("转账成功");


        } catch (Exception e) {
            try {
                if (conn != null){
                    conn.rollback();
                }

            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            out.print(e.getMessage());
        }finally {
            if (rs !=null){
                try {
                    rs.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            if(ps != null){
                try {
                    ps.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            if(ps2 != null){
                try {
                    ps2.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            if(ps3 != null){
                try {
                    ps3.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
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
