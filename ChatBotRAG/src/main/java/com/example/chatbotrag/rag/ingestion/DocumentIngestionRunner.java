package com.example.chatbotrag.rag.ingestion;

import java.util.List;
import java.util.stream.Collectors;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.springframework.ai.document.Document;
import org.springframework.ai.transformer.splitter.TextSplitter;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;




@Component
public class DocumentIngestionRunner implements CommandLineRunner {

    private final VectorStore vectorStore;

    public DocumentIngestionRunner(VectorStore vectorStore){
        this.vectorStore=vectorStore;
    }

    @Override
    public void run(String... args) {
        try (var doc = new XWPFDocument(
                new ClassPathResource("docs/Car_loan.docx").getInputStream())) {

            String text = doc.getParagraphs().stream()
                    .map(XWPFParagraph::getText)
                    .filter(s -> !s.isBlank())
                    .collect(Collectors.joining("\n"));

			TextSplitter splitter = new TokenTextSplitter();

            List<Document> documents = splitter.apply(
                 List.of(new Document(text)));
            
            vectorStore.add(documents);	
            
            System.out.println(
                    "Successfully loaded "
                    + documents.size()
                    + " chunks into VectorStore");	
                		
            
        } catch (Exception e) {
             e.printStackTrace();
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
Extracts paragraph text.
Uses TokenTextSplitter to chunk the content.
Creates Spring AI Document objects.
Generates embeddings through the configured VectorStore.
Stores them in PGVector.
*/