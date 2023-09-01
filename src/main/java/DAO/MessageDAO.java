package DAO;

import java.sql.*;
import java.util.ArrayList;

import Model.Message;
import Util.ConnectionUtil;

public class MessageDAO {

    public Message addMessage(Message message) {
        // open connection
        Connection con = ConnectionUtil.getConnection();
        try {
        // create a statement
        String sql = "INSERT INTO message VALUES(default, ?, ?, ?)";
        PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ps.setInt(1, message.getPosted_by());
        ps.setString(2, message.getMessage_text());
        ps.setLong(3, message.getTime_posted_epoch());

        // execute statement
        ps.executeUpdate();
        ResultSet rs = ps.getGeneratedKeys();

        // return generated message object
        if(rs.next()) {
            int generated_message_id = (int) rs.getLong(1);
            Message newMessage = new Message(generated_message_id, message.getPosted_by(), message.getMessage_text(), message.getTime_posted_epoch());
            return newMessage;
        }
    } catch (SQLException e) {
         // handle exception
         System.out.println(e.getMessage());
    }
    return null;
    }

    public Message getMessageById(int id) {
        // open connection
        Connection con = ConnectionUtil.getConnection();
        try {
            // create statement
            String sql = "SELECT * FROM message WHERE message_id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);

            // execute statement
            ResultSet rs = ps.executeQuery();

            // process results
            while(rs.next()) {
                Message message = new Message(rs.getInt("message_id"), rs.getInt("posted_by"), rs.getString("message_text"), rs.getLong("time_posted_epoch"));
                return message;
            }

        } catch (SQLException e) {
            // handle exception
            System.out.println(e.getMessage());
        }
        return null;
    }

    public ArrayList<Message> getAllMessages() {
         // open connection
         Connection con = ConnectionUtil.getConnection();
         ArrayList<Message> messages = new ArrayList<>();
         try {
             // create statement
             String sql = "SELECT * FROM message";
             PreparedStatement ps = con.prepareStatement(sql);
 
             // execute statement
             ResultSet rs = ps.executeQuery();
 
             // process results
             while(rs.next()) {
                 Message message = new Message(rs.getInt("message_id"), rs.getInt("posted_by"), rs.getString("message_text"), rs.getLong("time_posted_epoch"));
                 messages.add(message);
             }
 
         } catch (SQLException e) {
             // handle exception
             System.out.println(e.getMessage());
         }
         return messages;
    }

    public Message updateMessage(int id, String messageText) {
         // open connection
         Connection con = ConnectionUtil.getConnection();
         try {
             // create statement
             String sql = "UPDATE message SET message_text = ? WHERE message_id = ?";
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
             ps.setString(1, messageText);
             ps.setInt(2, id);
 
             // execute statement
             ps.executeUpdate();
 
         } catch (SQLException e) {
             // handle exception
             System.out.println(e.getMessage());
         }
         return null;
    }

    public Message deleteMessage(int id) {
         // open connection
         Connection con = ConnectionUtil.getConnection();
         try {
             // create statement
             String sql = "DELETE FROM message WHERE message_id = ?";
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
             ps.setInt(1, id);
 
             // execute statement
             ps.executeUpdate();
             ResultSet rs = ps.getGeneratedKeys();
 
             // process results
             if(rs.next()) {
                 return new Message(rs.getInt("message_id"), rs.getInt("posted_by"), rs.getString("message_text"), rs.getLong("time_posted_epoch"));
             }
 
         } catch (SQLException e) {
             // handle exception
             System.out.println(e.getMessage());
         }
         return null;
    }
    
}