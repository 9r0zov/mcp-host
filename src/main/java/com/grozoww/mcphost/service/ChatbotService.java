package com.grozoww.mcphost.service;

import com.grozoww.mcphost.util.InstaTextUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatbotService {

    private final ChatClient chatClient;

    public String chat(String message) {
        return InstaTextUtils.clean(
                chatClient
                        .prompt()
                        .user(message)
                        .call()
                        .content()
        );
    }

}
