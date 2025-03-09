package org.example.chatservice.domain.message.repository;

import org.example.chatservice.domain.message.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {
}
