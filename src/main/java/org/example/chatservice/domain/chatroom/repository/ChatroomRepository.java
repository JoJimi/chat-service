package org.example.chatservice.domain.chatroom.repository;

import org.example.chatservice.domain.chatroom.domain.Chatroom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatroomRepository extends JpaRepository<Chatroom, Long> {
}
