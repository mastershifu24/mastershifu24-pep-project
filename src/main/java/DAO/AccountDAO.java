package DAO;

import java.sql.*;
import java.util.ArrayList;

import Model.Account;
import Util.ConnectionUtil;

public class AccountDAO {

    // create account
    public Account addAccount(Account account) {
        // open connection
        Connection connection = ConnectionUtil.getConnection();
        try {
        // create statement
        String sql = "INSERT INTO account VALUES(default, ?, ?)";
        PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, account.getUsername());
        ps.setString(2, account.getPassword());

        // generate result set
        ps.executeUpdate();
        ResultSet rs = ps.getGeneratedKeys();

        // return generated account object
        if(rs.next()) {
            int generated_account_id = (int) rs.getLong(1);
            return new Account(generated_account_id, account.getUsername(), account.getPassword());
        }
    } catch (SQLException e) {
        // handle exception
        System.out.println(e.getMessage());
    }
        return null;
    }

    // login to account
    public Account getAccount(String username, String password) {
           // open connection
        Connection connection = ConnectionUtil.getConnection();
        try {
        // create statement
        String sql = "SELECT * FROM account WHERE username = ? AND password = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, username);
        ps.setString(2, password);

        // generate result set
        ResultSet rs = ps.executeQuery();
        // process results
        while(rs.next()) {
            int id = rs.getInt("account_id");
            String usernameResult = rs.getString("username");
            String passwordResult = rs.getString("password");
            return new Account(id, usernameResult, passwordResult);
            
        }

    } catch (SQLException e) {
        // handle exception
        System.out.println(e.getMessage());
    }
        return null;
       
    }

    // get account by id
    public Account getAccountById(int id) {
           // open connection
        Connection con = ConnectionUtil.getConnection();
        try {
        // create statement
        String sql = "SELECT * FROM account WHERE account_id = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, id);

        // generate result set
        ResultSet rs = ps.executeQuery();
        
        // process results
        while(rs.next()) {
            int idResult = rs.getInt("account_id");
            String usernameResult = rs.getString("username");
            String passwordResult = rs.getString("password");
            Account account = new Account(idResult, usernameResult, passwordResult);
            return account;
        }

    } catch (SQLException e) {
        // handle exception
        System.out.println(e.getMessage());
    }
        return null;
       
    }

    // get all accounts
    public ArrayList<Account> getAllAccounts() {
        // open connection
        Connection con = ConnectionUtil.getConnection();
        ArrayList<Account> accounts = new ArrayList<>();
        try {
            // create statement
            String sql = "SELECT * FROM account";
            PreparedStatement ps = con.prepareStatement(sql);
 
             // execute statement
             ResultSet rs = ps.executeQuery();
 
             // process results
             while(rs.next()) {
                Account account = new Account(rs.getInt("account_id"), rs.getString("username"), rs.getString("password"));
                accounts.add(account);
             }
        } catch (Exception e) {
            /// handle exception
            System.out.println(e.getMessage());
        }
        return accounts;
    }
}