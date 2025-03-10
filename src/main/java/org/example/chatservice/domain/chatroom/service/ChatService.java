package org.example.chatservice.domain.chatroom.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.chatservice.domain.chatroom.domain.Chatroom;
import org.example.chatservice.domain.chatroom.domain.MemberChatroomMapping;
import org.example.chatservice.domain.chatroom.dto.ChatroomDto;
import org.example.chatservice.domain.chatroom.repository.ChatroomRepository;
import org.example.chatservice.domain.chatroom.repository.MemberChatroomMappingRepository;
import org.example.chatservice.domain.member.entity.Member;
import org.example.chatservice.domain.message.entity.Message;
import org.example.chatservice.domain.message.repository.MessageRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class ChatService {

    private final ChatroomRepository chatroomRepository;
    private final MemberChatroomMappingRepository memberChatroomMappingRepository;
    private final MessageRepository messageRepository;

    // chatroom 만드는 메서드
    public Chatroom createChatroom(Member member, String title){
        Chatroom chatroom = Chatroom.builder()
                .title(title)
                .build();

        chatroom = chatroomRepository.save(chatroom);

        MemberChatroomMapping memberChatroomMapping = chatroom.addMember(member);

        memberChatroomMapping = memberChatroomMappingRepository.save(memberChatroomMapping);

        return chatroom;
    }

    /**
        멤버가 채팅방에 참여하고 있는지 확인하고
        1. 이미 참여한 채팅방이면 false 반환
        2. 채팅방에 참여되도록 하는 로직 실행 후 true 반환
     */
    public Boolean joinChatroom(Member member, Long newChatroomId, Long currentChatroomId){
        if(currentChatroomId != null){
            updateLastCheckedAt(member, currentChatroomId);
        }

        if(memberChatroomMappingRepository.existsByMemberIdAndChatroomId(member.getId(), newChatroomId)){
            log.info("이미 참여한 채팅방입니다.");
            return false;
        }

        // 해당 채팅방이 없으면 NoSuchElementException 예외 발생
        Chatroom chatroom = chatroomRepository.findById(newChatroomId).get();

        MemberChatroomMapping memberChatroomMapping = MemberChatroomMapping.builder()
                .member(member)
                .chatroom(chatroom)
                .build();

        memberChatroomMapping = memberChatroomMappingRepository.save(memberChatroomMapping);

        return true;
    }

    private void updateLastCheckedAt(Member member, Long currentChatroomId){
        MemberChatroomMapping memberChatroomMapping =
                memberChatroomMappingRepository.findByMemberIdAndChatroomId(member.getId(), currentChatroomId).get();

        memberChatroomMapping.updateLastCheckedAt();
    }

    // 채팅방에서 빠져나오는 메서드
    @Transactional
    public Boolean leaveChatroom(Member member, Long chatroomId){
        if(!memberChatroomMappingRepository.existsByMemberIdAndChatroomId(member.getId(), chatroomId)){
            log.info("참여하지 않은 방입니다.");
            return false;
        }

        memberChatroomMappingRepository.deleteByMemberIdAndChatroomId(member.getId(), chatroomId);
        return true;
    }

    // chatroom 리스트를 반환해주는 메서드
    public List<Chatroom> getChatroomList(Member member){
        List<MemberChatroomMapping> memberChatroomMappingList = memberChatroomMappingRepository.findAllByMemberId(member.getId());

        return memberChatroomMappingList.stream()
                .map(memberChatroomMapping -> {
                    Chatroom chatroom = memberChatroomMapping.getChatroom();
                    chatroom.setHasNewMessage(
                            messageRepository.existsByChatroomIdAndCreatedAtAfter(
                            chatroom.getId(), memberChatroomMapping.getLastCheckedAt()));

                    return chatroom;
                })
                .toList();
    }

    public Message saveMessage(Member member, Long chatroomId, String text){
        Chatroom chatroom = chatroomRepository.findById(chatroomId).get();

        Message message = Message.builder()
                .text(text)
                .member(member)
                .chatroom(chatroom)
                .build();

        return messageRepository.save(message);
    }

    public List<Message> getMessageList(Long chatroomId){
        return messageRepository.findAllByChatroomId(chatroomId);
    }

    public Chatroom getChatroom(Long chatroomId) {
        return chatroomRepository.findById(chatroomId).get();
    }
}
