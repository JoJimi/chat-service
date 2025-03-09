package org.example.chatservice.domain.chatroom.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.chatservice.domain.chatroom.domain.Chatroom;
import org.example.chatservice.domain.chatroom.domain.MemberChatroomMapping;
import org.example.chatservice.domain.chatroom.repository.ChatroomRepository;
import org.example.chatservice.domain.chatroom.repository.MemberChatroomMappingRepository;
import org.example.chatservice.domain.member.entity.Member;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class ChatService {

    private ChatroomRepository chatroomRepository;
    private MemberChatroomMappingRepository memberChatroomMappingRepository;

    // chatroom 만드는 메서드
    public Chatroom createChatroom(Member member, String title){
        Chatroom chatroom = Chatroom.builder()
                .title(title)
                .build();

        chatroom = chatroomRepository.save(chatroom);

        MemberChatroomMapping memberChatroomMapping = MemberChatroomMapping.builder()
                .member(member)
                .chatroom(chatroom)
                .build();

        memberChatroomMapping = memberChatroomMappingRepository.save(memberChatroomMapping);

        return chatroom;
    }

    /**
        멤버가 채팅방에 참여하고 있는지 확인하고
        1. 이미 참여한 채팅방이면 false 반환
        2. 채팅방에 참여되도록 하는 로직 실행 후 true 반환
     */
    public Boolean joinChatroom(Member member, Long chatroomId){
        if(memberChatroomMappingRepository.existsByMemberIdAndChatroomId(member.getId(), chatroomId)){
            log.info("이미 참여한 채팅방입니다.");
            return false;
        }

        // 해당 채팅방이 없으면 NoSuchElementException 예외 발생
        Chatroom chatroom = chatroomRepository.findById(chatroomId).get();

        MemberChatroomMapping memberChatroomMapping = MemberChatroomMapping.builder()
                .member(member)
                .chatroom(chatroom)
                .build();

        memberChatroomMapping = memberChatroomMappingRepository.save(memberChatroomMapping);

        return true;
    }

    // 채팅방에서 빠져나오는 메서드
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
                .map(MemberChatroomMapping::getChatroom)
                .toList();
    }
}
