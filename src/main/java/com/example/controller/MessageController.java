package com.example.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.service.AccountService;
import com.example.service.MessageService;

@RestController("messages")
public class MessageController {
    private MessageService messageService;

    public MessageController(MessageService messageService){
        this.messageService = messageService;
    }
}
