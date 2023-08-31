package Service;

import java.util.ArrayList;

import Model.Account;

public interface AccountService {
    // Create
    public abstract Account addAccount(Account account);

    // Read
    // login
    public abstract Account getAccount(String username, String password);
    // get account by id
    public abstract Account getAccountById(int id);
    // get all accounts
    public abstract ArrayList<Account> getAllAccounts();

}