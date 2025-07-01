package com.grozoww.mcphost.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.mcp.SyncMcpToolCallbackProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChatClientConfig {

    @Value("${spring.ai.mcp.client.sse.connections.my-weather-mcp.url}")
    private String mcpHost;

    @Bean
    public ChatClient chatClient(ChatModel chatModel,
                                 SyncMcpToolCallbackProvider toolCallbackProvider) {
        System.out.println("CONNECTING TO MCP HOST: " + mcpHost);
        return ChatClient
                .builder(chatModel)
                .defaultToolCallbacks(toolCallbackProvider.getToolCallbacks())
                .build();
    }

}
