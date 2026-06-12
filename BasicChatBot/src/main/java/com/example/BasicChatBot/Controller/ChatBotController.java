package com.example.BasicChatBot.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.BasicChatBot.Service.ChatService;

@RestController
public class ChatBotController{

   
    private final ChatService chatService;

     @Autowired
    public ChatBotController(ChatService chatService){
        this.chatService=chatService;
    }
    @GetMapping("/chat")
    public String chat(@RequestParam String question) {
        return chatService.ask(question);
       // return "Welcome to Chatbot " + question;
    }

   
}