package com.example.BasicChatBot.Service;
import com.example.BasicChatBot.client.ChatClient;

@Service
class ChatService{
 
    ChatClient chatClient;

    ChatService(ChatClient chatClient){
    this.chatClient=chatClient;
    }

    public String ask(String question){
        return chatClient.prompt().
                        user(question).
                        call().
                        content();
                        
    } 
}