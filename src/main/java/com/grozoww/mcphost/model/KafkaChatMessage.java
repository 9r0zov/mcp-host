package com.grozoww.mcphost.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class KafkaChatMessage {
    private String userId;
    private String chatId;
    private String message;
}

