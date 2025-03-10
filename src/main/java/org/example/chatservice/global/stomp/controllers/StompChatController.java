package org.example.chatservice.global.stomp.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.chatservice.domain.chatroom.dto.ChatroomDto;
import org.example.chatservice.domain.chatroom.service.ChatService;
import org.example.chatservice.domain.message.entity.Message;
import org.example.chatservice.global.oauth2.custom.CustomOAuth2User;
import org.example.chatservice.global.stomp.dto.ChatMessage;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;

import java.security.Principal;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Controller
public class StompChatController {

    private final ChatService chatService;
    private final SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/chats/{chatroomId}")
    @SendTo("/sub/chats/{chatroomId}")
    public ChatMessage handleMessage(Principal principal,
                                     @DestinationVariable Long chatroomId,
                                     @Payload Map<String, String> payload) {
        log.info("{} sent {} in {}", principal.getName(), payload, chatroomId);
        CustomOAuth2User user = (CustomOAuth2User) ((AbstractAuthenticationToken) principal).getPrincipal();

        Message message = chatService.saveMessage(user.getMember(), chatroomId, payload.get("message"));

        ChatroomDto chatroomDto = ChatroomDto.from(chatService.getChatroom(chatroomId));
        messagingTemplate.convertAndSend("/sub/chats/updates", chatroomDto);

        return new ChatMessage(principal.getName(), payload.get("message"));
    }
}
