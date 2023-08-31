package com.socialmediablogapi.Controller;

import io.javalin.Javalin;
import io.javalin.http.Context;
import com.socialmediablogapi.Model.Account;
import com.socialmediablogapi.Model.Message;
import com.socialmediablogapi.Service.AccountService;
import com.socialmediablogapi.Service.MessageService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import com.fasterxml.jackson.core.JsonProcessingException;


/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */

    private AccountService accountService;
    private MessageService messageService;
    private ObjectMapper mapper;

    public SocialMediaController(){

    }

    public SocialMediaController(AccountService accountService, MessageService messageService){
        this.accountService = accountService;
        this.messageService = messageService;
        this.mapper = new ObjectMapper();
    }

    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.post("/register", this::registerAccountHandler);
        app.post("/login", this::loginAccountHandler);
        app.post("/messages", this::createMessageHandler);
        app.get("/messages", this::getAllMessagesHandler);
        app.get("/messages/{message_id}", this::getMessageByIdHandler);
        app.delete("/messages/{message_id}", this::deleteMessageByIdHandler);
        app.patch("/messages/{message_id}", this::updateMessageByIdHandler);
        app.get("/accounts/{account_id}", this::getAllMessagesByAccountIdHandler);

        private void registerAccountHandler(Context ctx) throws JsonProcessingException {
            Account account = mapper.readValue(ctx.body(), Account.class);
            Account registerAccount = accountService.registerAccount(account);
            if(registerAccount == null){
                ctx.status(400);
            } else {
                ctx.json(registerAccount);
            }
        }

        app.post("/input", ctx -> {
            ctx.status(201);
        });

        app.start();
        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void exampleHandler(Context context) {
        context.json("sample text");
    }


}