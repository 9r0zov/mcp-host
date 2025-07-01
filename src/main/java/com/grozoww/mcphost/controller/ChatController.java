package com.grozoww.mcphost.controller;

import com.grozoww.mcphost.model.UserRequest;
import com.grozoww.mcphost.service.ChatbotService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/chat")
@RequiredArgsConstructor
public class ChatController {

    private final ChatbotService chatbotService;

    @PostMapping
    public ResponseEntity<String> askQuestion(@RequestBody UserRequest userRequest) {
        String chatResponse = chatbotService.chat(userRequest.message());
        return ResponseEntity.ok(chatResponse);
    }

}
