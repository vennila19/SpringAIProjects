package com.example.chatbotrag.controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import com.example.chatbotrag.service.ChatBotRAGService;
import org.springframework.web.bind.annotation.PostMapping; 
import com.example.chatbotrag.service.DataIngestionService; 
import java.io.IOException; 

@RequestMapping("/chat")
@RestController
public class ChatBotRAGController{

    private final ChatBotRAGService service;
    private final DataIngestionService ingestionService;

    public ChatBotRAGController(ChatBotRAGService service){
        this.service=service;
    }
    @GetMapping("/ask")
    public String ask(@RequestParam String message){
        return service.ask(message);
    }

    @GetMapping("/ping")
    public String ping() {
        return "pong";
    }

    /**
     * Endpoint to trigger document reading, chunking, and vector storage.
     * URL: POST http://localhost:8080/chat/ingest?filePath=/path/to/your/file.txt
     */
    @PostMapping("/ingest")
    public String ingestData(@RequestParam String filePath) {
        try {
            ingestionService.ingestTextFile(filePath);
            return "Ingestion successful for file: " + filePath;
        } catch (IOException e) {
            return "Failed to read or ingest file: " + e.getMessage();
        }
    }
}