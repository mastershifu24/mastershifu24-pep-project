package Service;

import java.util.ArrayList;

import Model.Message;

public interface MessageService {
    // Create
    public abstract Message addMessage(Message message);

    // Read
    // get message by id
    public abstract Message getMessageById(int id);

    // get all messages
    public abstract ArrayList<Message> getAllMessages();

    // get messages by user
    public abstract ArrayList<Message> getAllMessagesByUser(int id);

    // Update
    public abstract Message updateMessage(int id, String messageText);

    // Delete
    public abstract Message deleteMessage(int id);
}