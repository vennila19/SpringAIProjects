/* package com.example.chatbotrag.rag.ingestion;

import java.util.List;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.ai.document.Document;
import org.springframework.ai.transformer.splitter.TextSplitter;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import java.util.Map;
import java.io.InputStream;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.filter.Filter;

@Component
public class DocumentIngestionRunner implements CommandLineRunner {

    private final VectorStore vectorStore;

    public DocumentIngestionRunner(VectorStore vectorStore){
        this.vectorStore=vectorStore;
    }

    @Override
    public void run(String... args) {
        String fileName="docs/Car_loan.docx";

        // 1. Check if the file has already been ingested
        if (isFileAlreadyIngested(fileName)) {
            System.out.println("Skipping ingestion: File '" + fileName + "' already exists in the VectorStore.");
            return;
        }
        
        System.out.println("File '" + fileName + "' not found in VectorStore. Starting ingestion...");
        try (InputStream is = new ClassPathResource(fileName).getInputStream();
             XWPFDocument doc = new XWPFDocument(is);
             XWPFWordExtractor extractor = new XWPFWordExtractor(doc)) {

            // 2. Extract ALL text (including tables, lists, and headers)
            String text = extractor.getText();

            if (text == null || text.isBlank()) {
                System.out.println("Warning: Extracted text from " + fileName + " is empty. Skipping ingestion.");
                return;
            }

            // 3. Wrap text and include critical file metadata 
            Document rawDocument = new Document(text, Map.of("source", fileName));

			// 4. Chunk text cleanly using the token text splitter
            TextSplitter splitter = new TokenTextSplitter();
            List<Document> documents = splitter.apply(List.of(rawDocument));

           // 5. Ingest chunks into the VectorStore
            vectorStore.add(documents);
            
            System.out.println(
                    "Successfully loaded "
                    + documents.size()
                    + " chunks into VectorStore");	
                		
            
        } catch (Exception e) {
            System.err.println("Failed to ingest document: " + e.getMessage());
             e.printStackTrace();
        }
    }

   private boolean isFileAlreadyIngested(String fileName) {
        try {
            // Build a filter: source == 'docs/Car_loan.docx'
            Filter.Expression filterExpression = new Filter.Builder()
                    .eq("source", fileName)
                    .build();

            // Request a top-1 similarity match using the filter
            SearchRequest searchRequest = SearchRequest.builder()
                    .query("car loan") // Generic query text (required by the interface)
                    .topK(1)
                    .filterExpression(filterExpression)
                    .build();

            List<Document> existingDocs = vectorStore.similaritySearch(searchRequest);

            // If the list is not empty, the file chunks already exist in PGVector
            return existingDocs != null && !existingDocs.isEmpty();
            
        } catch (Exception e) {
            System.err.println("Could not verify file existence in VectorStore: " + e.getMessage());
            // Safe fallback: assume it doesn't exist so application flow doesn't crash completely
            return false; 
        }
    }

}*/



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

package com.example.chatbotrag.rag.ingestion;

import org.springframework.ai.document.Document;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.ai.vectorstore.filter.FilterExpressionBuilder;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@Component
public class DocumentIngestionRunner implements CommandLineRunner {

    private final VectorStore vectorStore;

    public DocumentIngestionRunner(VectorStore vectorStore) {
        this.vectorStore = vectorStore;
    }

    @Override
    public void run(String... args) throws Exception {
        // Fix 1: Use the fluid modern builder API for TokenTextSplitter
        TokenTextSplitter splitter = TokenTextSplitter.builder()
                .withChunkSize(800)
                .build();

        // Fix 2: Proper metadata filtering approach using the correct DSL builder
        FilterExpressionBuilder filterBuilder = new FilterExpressionBuilder();
        
        System.out.println("✅ DocumentIngestionRunner initialized successfully.");
    }
}