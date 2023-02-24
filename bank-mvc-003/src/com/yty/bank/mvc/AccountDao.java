package com.yty.bank.mvc;

import com.yty.bank.utils.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AccountDao {
    public int insert(Account account, Connection conn){
//        Connection conn = null;
        PreparedStatement ps = null;
        int count = 0;
        try {
//            conn = DBUtil.getConnection();
            String sql = "insert into t_act(actno, balance) values(?, ?)";
            ps = conn.prepareStatement(sql);
            ps.setString(1, account.getActno());
            ps.setDouble(2, account.getBalance());
            count = ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        finally {
            DBUtil.close(null, ps, null);
        }
        return count;

    }
    public int deleteById(Integer id, Connection conn){
//        Connection conn = null;
        PreparedStatement ps = null;
        int count = 0;

        try {
//            conn = DBUtil.getConnection();
            String sql = "delete from t_act where id=?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            count = ps.executeUpdate();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally {
            DBUtil.close(null, ps, null);
        }
        return count;
    }
    public int update(Account account, Connection conn){
//        Connection conn = null;
        PreparedStatement ps = null;
        int count = 0;

        try {
//            conn = DBUtil.getConnection();
            String sql = "Update t_act set balance= ?, actno = ? where id=?";
            ps = conn.prepareStatement(sql);
            ps.setDouble(1, account.getBalance());
            ps.setString(2, account.getActno());
            ps.setInt(3, account.getId());
            count = ps.executeUpdate();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally {
            DBUtil.close(null, ps, null);
        }
        return count;
    }
    public Account selectByActno(String actno, Connection conn){
//        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Account account =null;
        try {
//            conn = DBUtil.getConnection();
            String sql = "select id, balance from t_act where actno=?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, actno);
            rs = ps.executeQuery();
            if(rs.next()){
                Integer id = rs.getInt("id");
                Double balance = rs.getDouble("balance");

                account = new Account();
                account.setId(id);
                account.setActno(actno);
                account.setBalance(balance);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally {
            DBUtil.close(null, ps, rs);
        }

        return account;
    }
    public List<Account> selectAll(Connection conn){
//        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Account> accounts = new ArrayList<>();
        try {
            conn = DBUtil.getConnection();
            String sql = "select id, actno, balance from t_act";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while(rs.next()){
                Integer id = rs.getInt("id");
                String actno = rs.getString("actno");
                Double balance = rs.getDouble("balance");

                Account account = new Account();
                account.setId(id);
                account.setActno(actno);
                account.setBalance(balance);
                accounts.add(account);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally {
            DBUtil.close(null, ps, rs);
        }

        return accounts;
    }
}
