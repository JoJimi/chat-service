package org.example.chatservice.domain.member.entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.chatservice.domain.chatroom.domain.MemberChatroomMapping;
import org.example.chatservice.global.entity.BaseEntity;
import org.example.chatservice.type.GenderType;
import org.springframework.security.crypto.password.PasswordEncoder;

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
    private String password;
    @Enumerated(EnumType.STRING)
    private GenderType gender;
    private String phoneNumber;
    private LocalDate birthDay;
    private String role;

    @OneToMany(mappedBy = "member")
    Set<MemberChatroomMapping> memberChatroomMappingSet;

    public void updatePassword(String password, String confirmedPassword, PasswordEncoder passwordEncoder) {
        if(!password.equals(confirmedPassword)){
            throw new IllegalStateException("패스워드가 일치하지 않습니다.");
        }

        this.password = passwordEncoder.encode(password);
    }
}
