package com.example.BasicChatBot.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
public class ChatBotController{

    @GetMapping("/chat")
    public String chat(@RequestParam String question) {
        return "Welcome to Chatbot" + question;
    }

   
}