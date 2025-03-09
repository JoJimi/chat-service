package org.example.chatservice.domain.member.entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.chatservice.domain.chatroom.domain.MemberChatroomMapping;
import org.example.chatservice.global.entity.BaseEntity;
import org.example.chatservice.type.GenderType;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String nickName;
    private String name;
    @Enumerated(EnumType.STRING)
    private GenderType gender;
    private String phoneNumber;
    private LocalDate birthDay;
    private String role;

    @OneToMany(mappedBy = "member")
    Set<MemberChatroomMapping> memberChatroomMappingSet;
}
