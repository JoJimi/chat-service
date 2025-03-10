package org.example.chatservice.global.oauth2.custom;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.chatservice.domain.member.dtos.MemberDto;
import org.example.chatservice.domain.member.entity.Member;
import org.example.chatservice.domain.member.repository.MemberRepository;
import org.example.chatservice.type.RoleType;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;

@Slf4j
@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepository.findByName(username).get();

        if(RoleType.fromCode(member.getRole()) != RoleType.CONSULTANT){
            try {
                throw new AccessDeniedException("상담사가 아닙니다.");
            } catch (AccessDeniedException e) {
                throw new RuntimeException(e);
            }
        }

        return new CustomUserDetails(member);
    }

    public MemberDto saveMember(MemberDto memberDto){
        Member member = MemberDto.to(memberDto);
        member.updatePassword(memberDto.password(), memberDto.confirmPassword(), passwordEncoder);

        memberRepository.save(member);

        return MemberDto.from(member);
    }
}
