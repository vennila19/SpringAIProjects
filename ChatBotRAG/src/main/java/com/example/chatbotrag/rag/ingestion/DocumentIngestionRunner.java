package com.example.chatbotrag.rag.ingestion;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.ai.document.Document;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.ai.vectorstore.filter.FilterExpressionBuilder;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

@Component
public class DocumentIngestionRunner implements CommandLineRunner {

    private final VectorStore vectorStore;

    public DocumentIngestionRunner(VectorStore vectorStore) {
        this.vectorStore = vectorStore;
    }

    @Override
    public void run(String... args) {
        String fileName = "docs/Car_loan.docx";

        if (isFileAlreadyIngested(fileName)) {
            System.out.println("Skipping ingestion: File '" + fileName + "' already exists in the VectorStore.");
            return;
        }

        System.out.println("File '" + fileName + "' not found in VectorStore. Starting ingestion...");

        try (InputStream is = new ClassPathResource(fileName).getInputStream();
             XWPFDocument doc = new XWPFDocument(is);
             XWPFWordExtractor extractor = new XWPFWordExtractor(doc)) {

            String text = extractor.getText();

            if (text == null || text.isBlank()) {
                System.out.println("Warning: Extracted text from " + fileName + " is empty. Skipping ingestion.");
                return;
            }

            Document rawDocument = new Document(text, Map.of("source", fileName));

            TokenTextSplitter splitter = TokenTextSplitter.builder().build();
            List<Document> documents = splitter.apply(List.of(rawDocument));

            vectorStore.add(documents);

            System.out.println("Successfully loaded " + documents.size() + " chunks into VectorStore");

        } catch (Exception e) {
            System.err.println("Failed to ingest document: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private boolean isFileAlreadyIngested(String fileName) {
        try {
            FilterExpressionBuilder fb = new FilterExpressionBuilder();
            Filter.Expression filterExpression = fb.eq("source", fileName);

            SearchRequest searchRequest = SearchRequest.builder()
                    .query("car loan")
                    .topK(1)
                    .filterExpression(filterExpression)
                    .build();

            List<Document> existingDocs = vectorStore.similaritySearch(searchRequest);
            return existingDocs != null && !existingDocs.isEmpty();

        } catch (Exception e) {
            System.err.println("Could not verify file existence in VectorStore: " + e.getMessage());
            return false;
        }
    }
}

/*If in your actual IDE code there is no extra ) and it compiles successfully, then your actual code is fine and the issue is only in what was pasted into the chat. The rest of the logic looks correct:

Read DOCX
Extract text
Split into chunks
Convert chunks to Document
Store in PGVector via vectorStore.add(documents)
So the only thing I see wrong in the pasted version is that extra closing parenthesis.


As per code

CommandLineRunner.run() executes.
Reads src/main/resources/docs/Car_loan.docx.
Extracts text, headers, and tables.
Uses TokenTextSplitter to chunk the content.
Creates Spring AI Document objects.
Generates embeddings through the configured VectorStore.
Stores them in PGVector.
*/