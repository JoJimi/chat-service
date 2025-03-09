package org.example.chatservice.domain.chatroom.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.chatservice.domain.chatroom.domain.Chatroom;
import org.example.chatservice.domain.chatroom.dto.ChatroomDto;
import org.example.chatservice.domain.chatroom.service.ChatService;
import org.example.chatservice.domain.message.entity.Message;
import org.example.chatservice.global.oauth2.custom.CustomOAuth2User;
import org.example.chatservice.global.stomp.dto.ChatMessage;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@AllArgsConstructor
@RequestMapping("/chats")
@RestController
public class ChatController {

    private ChatService chatService;

    /**
     * Chatroom 만드는 API
     * @param user
     * @param title
     */
    @PostMapping
    public ChatroomDto createChatroom(@AuthenticationPrincipal CustomOAuth2User user,
                                      @RequestParam String title){

        Chatroom chatroom = chatService.createChatroom(user.getMember(), title);
        return ChatroomDto.from(chatroom);
    }

    /**
     * Chatroom에 참여하는 API
     * 채팅방 db에 insert 하기 때문에 PostMapping이다.
      */
    @PostMapping("/{chatroomId}")
    public Boolean joinChatroom(@AuthenticationPrincipal CustomOAuth2User user,
                                @PathVariable Long chatroomId){
        return chatService.joinChatroom(user.getMember(), chatroomId);
    }

    /**
     * Chatroom를 떠나는 API
      */
    @DeleteMapping("/{chatroomId}")
    public Boolean leaveChatroom(@AuthenticationPrincipal CustomOAuth2User user,
                                 @PathVariable Long chatroomId){
        return chatService.leaveChatroom(user.getMember(), chatroomId);
    }

    /**
     * Chatroom 리스트를 반환해주는 API
     * @param user
     */
    @GetMapping
    public List<ChatroomDto> getChatroomList(@AuthenticationPrincipal CustomOAuth2User user){
        List<Chatroom> chatroomList = chatService.getChatroomList(user.getMember());
        return chatroomList.stream()
                .map(ChatroomDto::from)
                .toList();
    }

    @GetMapping("/{chatroomId}/messages")
    public List<ChatMessage> getMessageList(@PathVariable Long chatroomId){
        List<Message> messageList = chatService.getMessageList(chatroomId);
        return messageList.stream()
                .map(message -> new ChatMessage(message.getMember().getNickName(), message.getText()))
                .toList();
    }
}
