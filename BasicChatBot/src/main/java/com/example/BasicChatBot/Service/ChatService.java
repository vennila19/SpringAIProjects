package com.example.BasicChatBot.Service;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class ChatService{

    
    private final ChatClient chatClient;

    public ChatService(ChatClient chatClient){
    this.chatClient=chatClient;
    }

    public String ask(String question){
        return chatClient.prompt().
                        user(question).
                        call().
                        content();
                        
    } 
}