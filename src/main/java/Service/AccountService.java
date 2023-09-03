package Service;

import java.util.ArrayList;

import DAO.AccountDAO;
import Model.Account;

/*The purpose of a Service class is to contain "business logic" that sits between the web layer (controller) and
//persistence layer (DAO). That means that the Service class performs tasks that aren't done through the web or
SQL: programming tasks like checking that the input is valid, conducting additional security checks, or saving the
 * actions undertaken by the API to a logging file.
 *
 * It's perfectly normal to have Service methods that only contain a single line that calls a DAO method. An
 * application that follows best practices will often have unnecessary code, but this makes the code more
  readable and maintainable in the long run! */

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