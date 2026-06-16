package com.example.chatbotrag.service;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.ai.chat.client.advisor.QuestionAnswerAdvisor;


@Service
public class ChatBotRAGService{

 private final ChatClient chatClient;
 private final VectorStore vectorStore;

 public ChatBotRAGService(ChatClient.Builder builder,VectorStore vectorStore){

    this.chatClient=builder.
                    defaultAdvisors(new QuestionAnswerAdvisor(vectorStore)).
                    build();
    this.vectorStore=vectorStore;
 }

 public String ask(String question){
    return chatClient.
            prompt().
            user(question).
            call().
            content();
 }

}