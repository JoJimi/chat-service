package org.example.chatservice.domain.consultant.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.chatservice.domain.chatroom.dto.ChatroomDto;
import org.example.chatservice.domain.member.dtos.MemberDto;
import org.example.chatservice.domain.consultant.service.ConsultantService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/consultants")
@Controller
public class ConsultantController {
    private final ConsultantService consultantService;

    @ResponseBody
    @PostMapping
    public MemberDto saveMember(@RequestBody MemberDto memberDto){
        return consultantService.saveMember(memberDto);
    }

    @GetMapping
    public String index(){
        return "consultant/index.html";
    }

    @ResponseBody
    @GetMapping("/chats")
    public Page<ChatroomDto> getChatroomPage(Pageable pageable){
        return consultantService.getChatroomPage(pageable);
    }

}
