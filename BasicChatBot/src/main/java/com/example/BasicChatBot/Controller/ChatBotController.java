package com.example.BasicChatBot.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
public class ChatBotController{

    ChatService chatService;

    ChatBotController(ChatService chatService){
        this.chatService=chatService;
    }
    @GetMapping("/chat")
    public String chat(@RequestParam String question) {
        rerurn chatService.ask(question);
       // return "Welcome to Chatbot " + question;
    }

   
}