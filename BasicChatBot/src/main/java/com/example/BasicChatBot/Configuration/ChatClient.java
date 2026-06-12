import org.springframework.ai.chat.client.ChatClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
Class AiConfig{

    @Bean
    ChatClient chatClient(chatClient.Builder builder){
        return builder.build();
    }
}