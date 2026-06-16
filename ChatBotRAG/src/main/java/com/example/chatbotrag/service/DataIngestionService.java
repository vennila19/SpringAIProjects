package com.example.chatbotrag.service;

import org.springframework.ai.document.Document;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@Service
public class DataIngestionService {

    private final VectorStore vectorStore;

    @Value("${app.rag.chunk-size}")
    private int chunkSize;

    @Value("${app.rag.chunk-overlap}")
    private int chunkOverlap;

    public DataIngestionService(VectorStore vectorStore) {
        this.vectorStore = vectorStore;
    }

    /**
     * Reads a text file, splits it into semantic chunks, and stores it into PGVector.
     */
    public void ingestTextFile(String filePath) throws IOException {
        // 1. Read the text file content
        String content = Files.readString(Path.of(filePath));

        // 2. Wrap it inside a Spring AI Document wrapper
        Document rawDocument = new Document(content);

        // 3. Initialize the Splitter using your custom properties
        TokenTextSplitter splitter = new TokenTextSplitter(chunkSize, chunkOverlap, 1, 10000, true);

        // 4. Fragment the document into structured chunks
        List<Document> chunks = splitter.split(List.of(rawDocument));

        // 5. Send chunks to VectorStore (Automatically creates embeddings via Ollama and saves to Postgres)
        vectorStore.accept(chunks);
        
        System.out.println("✅ Successfully ingested " + chunks.size() + " document chunks into PGVector!");
    }
}