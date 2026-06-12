package com.example.BasicChatBot.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
public class ChatBotController{

    @Autowired
    ChatService chatService;

    ChatBotController(ChatService chatService){
        this.chatService=chatService;
    }
    @GetMapping("/chat")
    public String chat(@RequestParam String question) {
        return chatService.ask(question);
       // return "Welcome to Chatbot " + question;
    }

   
}