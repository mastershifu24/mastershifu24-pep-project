package Service;

import java.util.ArrayList;
import java.util.Objects;

import DAO.AccountDAO;
import DAO.AccountDAOimplem;
import DAO.MessageDAO;
import DAO.MessageDAOimplem;
import Model.Account;
import Model.Message;

public class MessageServiceimplements implements MessageService {
    // state
    private MessageDAO messageDAO;
    private AccountDAO accountDAO;

    // constructor 
    public void MessageServiceImpl() {
        this.messageDAO = new MessageDAOimplem();
        this.accountDAO = new AccountDAOimplem();
    }

    // create message
    @Override
    public Message addMessage(Message message) {
        int account_id = message.getPosted_by();
        Account account = accountDAO.getAccountById(account_id);
        if (message.getMessage_text().isBlank() || message.getMessage_text().length() >= 255 || Objects.isNull(account) ) { 
        return null;
        } else {
        Message newMessage = messageDAO.addMessage(message);
       return newMessage;
    } 
    }

    @Override
    public Message getMessageById(int id) {
       return messageDAO.getMessageById(id);
    }

    @Override
    public ArrayList<Message> getAllMessages() {
        return messageDAO.getAllMessages();
    }

    @Override
    public ArrayList<Message> getAllMessagesByUser(int id) {
        ArrayList<Message> allMessages = messageDAO.getAllMessages();
        ArrayList<Message> userMessages = new ArrayList<>();
        for (Message message : allMessages) {
            if(message.getPosted_by() == id) {
                userMessages.add(message);
            }
        }
        return userMessages;
    }

    @Override
    public Message updateMessage(int id, String messageText) {
        Message message = messageDAO.getMessageById(id);
        if (messageText.isBlank() || Objects.isNull(message) || messageText.length() >= 255) { 
            return null;
        } else {
            messageDAO.updateMessage(id, messageText);
        } 
        return messageDAO.getMessageById(id);
       
    }

    @Override
    public Message deleteMessage(int id) {
        Message message = messageDAO.getMessageById(id);
        if(Objects.isNull(message)) {
            return null;
        } else {
        return messageDAO.deleteMessage(id);
    }
    }
    
}