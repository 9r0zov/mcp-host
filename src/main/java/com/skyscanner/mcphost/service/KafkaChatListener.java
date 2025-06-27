package com.skyscanner.mcphost.service;

import com.skyscanner.mcphost.model.KafkaChatMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class KafkaChatListener {

    private final ChatbotService chatbotService;
    private final KafkaTemplate<String, KafkaChatMessage> kafkaTemplate;

    @KafkaListener(
            topics = "chat-messages",
            groupId = "mcp-host-group",
            containerFactory = "kafkaChatMessageListenerContainerFactory")
    public void listen(KafkaChatMessage message) {
        log.info("Received chat message {}", message);
        String response = chatbotService.chat(message.getMessage());
        KafkaChatMessage responseMessage = new KafkaChatMessage(message.getChatId(),
                                                                message.getUserId(),
                                                                response);

        log.info("Received response from LLM: {}.\n Sending message to the queue", responseMessage);

        kafkaTemplate.send("chat-responses", responseMessage);
    }
}
