package Controller;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;

import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;
import io.javalin.Javalin;
import io.javalin.http.Context;

import java.util.List;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {

    MessageService messageService;
    AccountService accountService;

    public SocialMediaController(){
        this.messageService = new MessageService();
        this.accountService = new AccountService();
    }

    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.post("/register", this::postRegisterHandler);
        app.post("/login", this::postLoginHandler);
        app.get("/messages", this::getAllMessagesHandler);
        app.post("/messages", this::postMessagesHandler);
        app.get("/messages/{message_id}", this::getMessageHandler);
        app.delete("/messages/{message_id}", this::deleteMessageHandler);
        app.patch("/messages/{message_id}", this::patchMessageHandler);
        app.get("/accounts/{account_id}/messages", this::getMessagesByAccountHandler);
        return app;
    }


    //PostRegisterLogin Handlers
    private void postRegisterHandler(Context ctx)throws JsonProcessingException {
        ObjectMapper om = new ObjectMapper();
        Account account = om.readValue(ctx.body(), Account.class);
        Account addedAccount = accountService.addAccount(account);

        if (account.username.isEmpty() == false && account.password.length() >= 4 && addedAccount != null) 
        {
            ctx.json(om.writeValueAsString(addedAccount));
            ctx.status(200);
        }

        else{
            ctx.status(400);
        }
    }

    private void postLoginHandler(Context ctx)throws JsonProcessingException {
        ObjectMapper om = new ObjectMapper();
        Account account = om.readValue(ctx.body(), Account.class);
        Account loginAccount = accountService.loginAccountInfo(account);
        if(loginAccount == null)
        {
            ctx.status(401);
        }

        else
        {
            ctx.json(om.writeValueAsString(loginAccount));
            ctx.status(200);
        }
    }

    //Message Handlers
    private void getAllMessagesHandler(Context ctx)throws JsonProcessingException {
        List<Message> messages = messageService.getAllMessages();
        ctx.json(messages);
    }

    private void postMessagesHandler(Context ctx)throws JsonProcessingException {
        ObjectMapper om = new ObjectMapper();
        Message message = om.readValue(ctx.body(), Message.class);
        Message loginMessage = messageService.insertMessageInfo(message);

        if(loginMessage == null || message.message_text.isEmpty() == true || message.message_text.length() > 255)
        {
            ctx.status(400);
        }

        else
        {
            ctx.json(om.writeValueAsString(loginMessage));
            ctx.status(200);
        }

    }

    private void getMessageHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper om = new ObjectMapper();
        int messageId = Integer.parseInt(ctx.pathParam("message_id"));
        Message message = messageService.getMessageAfterPosting(messageId);
        ctx.json(om.writeValueAsString(message));
        ctx.status(200);
    }

    private void getMessagesByAccountHandler(Context ctx)throws JsonProcessingException {
        List<Message> messages = messageService.getAllMessagesByAccountId(1);
        ctx.json(messages);
    }
    
    private void patchMessageHandler(Context ctx) throws JsonProcessingException
    {
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(ctx.body(), Message.class);
        int updatedMessage = Integer.parseInt(ctx.pathParam("message_id")); 
        Message existingMessage = messageService.updateMessages(updatedMessage, message);
        if(existingMessage != null)
        {
            ctx.json(mapper.writeValueAsString(existingMessage)); 
            ctx.status(200); 
        } 
        else
        {
            ctx.status(400); 
        }
    }

    private void deleteMessageHandler(Context ctx) throws JsonProcessingException
    {
        ObjectMapper mapper = new ObjectMapper();
        int deletedMessageId = Integer.parseInt(ctx.pathParam("message_id")); 
        Message existingMessage = messageService.deleteMessage(deletedMessageId);

        if(existingMessage == null)
        {
            ctx.json("");
        }

        else
        {
            ctx.json(mapper.writeValueAsString(existingMessage)); 
            ctx.status(200);
        }
    }
}