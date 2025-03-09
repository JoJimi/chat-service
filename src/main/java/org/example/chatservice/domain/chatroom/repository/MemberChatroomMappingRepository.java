package org.example.chatservice.domain.chatroom.repository;

import org.example.chatservice.domain.chatroom.domain.MemberChatroomMapping;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberChatroomMappingRepository extends JpaRepository<MemberChatroomMapping, Long> {
}
