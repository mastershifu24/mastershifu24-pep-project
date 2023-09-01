package Service;

import java.util.ArrayList;

import DAO.AccountDAO;
import Model.Account;

public class AccountService {
    // state
    private AccountDAO accountDAO;

    //constructor
    public void AccountService() {
        this.accountDAO = new AccountDAO();
    }

    // create account
    public Account addAccount(Account account) {
        if(account.getUsername().isBlank() || account.getPassword().length() < 4){
            return null;
        } else {
            return accountDAO.addAccount(account);
        }
        
    }

    // login to account
    public Account getAccount(String username, String password) {
        return accountDAO.getAccount(username, password);
    }

    // get account by id
    public Account getAccountById(int id) {
        return accountDAO.getAccountById(id);
    }

    // get all accounts
    public ArrayList<Account> getAllAccounts() {
        return accountDAO.getAllAccounts();
    }
    
}