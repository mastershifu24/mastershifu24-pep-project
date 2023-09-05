package Service;

import Model.Message;
import DAO.MessageDAO;


import java.util.List;

public class MessageService {
    MessageDAO messageDAO;

    public MessageService()
    {
        messageDAO = new MessageDAO();
    }

    public MessageService(MessageDAO messageDAO)
    {
        this.messageDAO = messageDAO;
    }

    public List<Message> getAllMessages()
    {
        return messageDAO.getAllMessages();
    }

    //Warn the user from typing more than 255 characters
    //Based off what the user gives us, we can let messageDAO input the message //
    // service layer = brain //
    public Message insertMessageInfo(Message message)
    {
        return messageDAO.insertMessage(message);
    }

    public Message getMessageAfterPosting(int id)
    {
        return messageDAO.getMessageById(id);
    }

    public List<Message> getAllMessagesByAccountId(int id)
    {
        System.out.println(messageDAO.getAllMessagesByAccountId(id));
        return messageDAO.getAllMessagesByAccountId(id);
    }

    public Message getMessage(int id)
    {
        return messageDAO.getMessageById(id);
    }

    public Message getMessageById(int id) {
        if (messageDAO.getMessageById(id).getMessage_text().isEmpty()) 
        {
            return null;
        } 
        else 
        {
            return messageDAO.getMessageById(id);
        }
    }

    public Message updateMessages(int id, Message message)
    {
        if(getMessageById(id) != null && message.message_text != "" && message.message_text.length() <= 255)
        {
            return messageDAO.updateMessage(id, message); 
        }
        return null;
    }

    public Message deleteMessage(int id)
    {
        if(getMessageById(id) != null)
        {
            return messageDAO.getMessageById(id);
        }
        return null;
    }
}