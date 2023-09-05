package DAO;

import Model.Message;
import Util.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MessageDAO {

    public List<Message> getAllMessages(){
        Connection connection = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>();
        try {
            String sql = "SELECT * FROM message";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Message message = new Message(
                        rs.getInt("message_id"),
                        rs.getInt("posted_by"),
                        rs.getString("message_text"),
                        rs.getInt("time_posted_epoch"));
                messages.add(message);
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return messages;
    }

    public Message insertMessage(Message message)
    {
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "INSERT INTO message (posted_by, message_text, time_posted_epoch) VALUES (?,?,?)" ;
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setInt(1, message.getPosted_by());
            preparedStatement.setString(2, message.getMessage_text());
            preparedStatement.setLong(3, message.getTime_posted_epoch());
            preparedStatement.executeUpdate();

            ResultSet pkeyResultSet = preparedStatement.getGeneratedKeys();
            if(pkeyResultSet.next()){
                int generated_message_id = (int) pkeyResultSet.getLong(1);
                return new Message(generated_message_id, message.getPosted_by(), message.getMessage_text(), message.getTime_posted_epoch());
            }

        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Message getMessageById(int id) 
    {
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "SELECT * FROM message WHERE message_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                Message message = new Message(
                    rs.getInt("message_id"), 
                    rs.getInt("posted_by"),
                    rs.getString("message_text"), 
                    rs.getLong("time_posted_epoch"));
                return message;
            } 
            else 
            {
                System.out.println("Empty message here");
                return new Message(0, 0, "", 0);
            }
        } catch (SQLException e) {
            System.out.println("Ran into exception here");
            System.out.println(e.getMessage());
        }
        return null; 
    }
    
    public Message updateMessage(int id, Message message)
    {
        Connection connection = ConnectionUtil.getConnection();
        try
        {
            if(getMessageById(id) == null)
            {
                return null; 
            }
            String sql = "UPDATE message SET message_text = ? WHERE message_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,message.getMessage_text());
            preparedStatement.setInt(2,id); 

            preparedStatement.executeUpdate(); 
            return getMessageById(id); 
        }
        catch(SQLException e)
        {
            System.out.println(e.getMessage()); 
        }
        return null;

    }

    public Message deletedMessage(int id)
    {
        Connection connection = ConnectionUtil.getConnection();
        try
        {
            String sql = "DELETE FROM message WHERE message_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,id); 
            preparedStatement.executeUpdate(); 
        }
        catch(SQLException e)
        {
            System.out.println(e.getMessage()); 
        }
        return null;
    }

    public List<Message> getAllMessagesByAccountId(int id)
    {
        Connection connection = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>();
        try {
            String sql = "SELECT * FROM message WHERE posted_by = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);

            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Message message1 = new Message(rs.getInt("message_id"),
                        rs.getInt("posted_by"),
                        rs.getString("message_text"),
                        rs.getInt("time_posted_epoch"));
                messages.add(message1);
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return messages;
    }
}