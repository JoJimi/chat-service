package org.example.chatservice.domain.chatroom.domain;

import jakarta.persistence.*;
import org.example.chatservice.domain.member.entity.Member;

@Entity
public class MemberChatroomMapping {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @JoinColumn(name = "member_id")
    @ManyToOne
    Member member;

    @JoinColumn(name = "chatroom_id")
    @ManyToOne
    Chatroom chatroom;

}
