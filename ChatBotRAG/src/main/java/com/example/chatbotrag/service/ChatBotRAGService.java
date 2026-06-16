package com.example.chatbotrag.service;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;

@Service
public class ChatBotRAGService{

 private final ChatClient chatClient;


 public ChatBotRAGService(ChatClient.Builder builder,VectorStore vectorStore){

    this.chatClient=builder.
                    defaultAdvisors(QuestionAnswerAdvisor.builder(vectorStore).build()).
                    build();
   // this.vectorStore=vectorStore;
 }

 public String ask(String question){
    return chatClient.
            prompt().
            user(question).
            call().
            content();
 }

}