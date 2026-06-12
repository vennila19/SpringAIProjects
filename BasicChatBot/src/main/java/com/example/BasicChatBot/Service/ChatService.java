package com.example.BasicChatBot.Service;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;


@Service
public class ChatService{

    @Autowired    
    ChatClient chatClient;

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