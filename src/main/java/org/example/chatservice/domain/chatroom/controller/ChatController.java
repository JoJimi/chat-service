package org.example.chatservice.domain.chatroom.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.chatservice.domain.chatroom.domain.Chatroom;
import org.example.chatservice.domain.chatroom.service.ChatService;
import org.example.chatservice.global.oauth2.custom.CustomOAuth2User;
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
    public Chatroom createChatroom(@AuthenticationPrincipal CustomOAuth2User user,
                                   @RequestParam String title){
        return chatService.createChatroom(user.getMember(), title);
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
    public List<Chatroom> getChatroomList(@AuthenticationPrincipal CustomOAuth2User user){
        return chatService.getChatroomList(user.getMember());
    }

}
