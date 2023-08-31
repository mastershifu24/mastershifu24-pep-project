package DAO;

import java.util.ArrayList;

import Model.Message;

public interface MessageDAO {
    // create
    public abstract Message addMessage(Message message);

    // read
    // get one message by id
    public abstract Message getMessageById(int id);
    // get all messages
    public abstract ArrayList<Message> getAllMessages();

    // update
    public abstract Message updateMessage(int id, String messageText);

    // delete
    public abstract Message deleteMessage(int id);
}