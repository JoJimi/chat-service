package org.example.chatservice.domain.chatroom.domain;


import jakarta.persistence.*;
import lombok.*;
import org.example.chatservice.domain.member.entity.Member;
import org.example.chatservice.global.entity.BaseEntity;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Chatroom extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String title;

    @OneToMany(mappedBy = "chatroom")
    Set<MemberChatroomMapping> memberChatroomMappingSet;

    @Transient
    Boolean hasNewMessage;

    public void setHasNewMessage(Boolean hasNewMessage) {
        this.hasNewMessage = hasNewMessage;
    }

    public MemberChatroomMapping addMember(Member member) {
        if (this.getMemberChatroomMappingSet() == null) {
            this.memberChatroomMappingSet = new HashSet<>();
        }

        MemberChatroomMapping memberChatroomMapping = MemberChatroomMapping.builder()
                .member(member)
                .chatroom(this)
                .build();

        this.memberChatroomMappingSet.add(memberChatroomMapping);

        return memberChatroomMapping;
    }
}
