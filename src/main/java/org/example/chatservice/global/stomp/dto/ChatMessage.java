package org.example.chatservice.global.stomp.dto;

public record ChatMessage(
        String sender,
        String message
) {
}
