package com.grozoww.mcphost.service;

import com.grozoww.mcphost.model.KafkaChatMessage;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
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
    @SneakyThrows
    public void listen(KafkaChatMessage message) {
        log.info("Received chat message {}", message);
        String response = chatbotService.chat(message.getMessage());
        KafkaChatMessage responseMessage = new KafkaChatMessage(
                message.getUserId(),
                message.getChatId(),
                response);

        log.info("Received response from LLM: {}.\n Sending message to the queue", responseMessage);

        kafkaTemplate.send("chat-responses", responseMessage);
    }
}
