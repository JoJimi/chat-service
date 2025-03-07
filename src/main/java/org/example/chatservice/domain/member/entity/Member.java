package org.example.chatservice.domain.member.entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.chatservice.domain.member.type.GenderType;

import java.time.LocalDate;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Member {

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


}
