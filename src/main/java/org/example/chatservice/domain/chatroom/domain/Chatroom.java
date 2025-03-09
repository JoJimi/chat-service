package org.example.chatservice.domain.chatroom.domain;


import jakarta.persistence.*;
import org.example.chatservice.global.entity.BaseEntity;

import java.util.Set;

@Entity
public class Chatroom extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String title;

    @OneToMany(mappedBy = "chatroom")
    Set<MemberChatroomMapping> memberChatroomMappingSet;

}
