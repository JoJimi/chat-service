package org.example.chatservice.domain.message.entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.chatservice.domain.chatroom.domain.Chatroom;
import org.example.chatservice.domain.member.entity.Member;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String Text;

    @JoinColumn(name = "member_id")
    @ManyToOne
    Member member;

    @JoinColumn(name = "chatroom_id")
    @ManyToOne
    Chatroom chatroom;
}
