package com.example.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Message;
import com.example.exception.ClientErrorException;
import com.example.service.MessageService;

@RestController
@RequestMapping("messages")
public class MessageController {
    private MessageService messageService;

    public MessageController(MessageService messageService){
        this.messageService = messageService;
    }

    @PostMapping
    public ResponseEntity<Message> createMessage(@RequestBody Message message) throws ClientErrorException{
        Message ret = messageService.createMessage(message);
        return ResponseEntity.status(200).body(ret);
    }

    @GetMapping
    public ResponseEntity<List<Message>> getAllMessages(){
        return ResponseEntity.status(200).body(messageService.getAllMessages());
    }

    @GetMapping("{messageId}")
    public ResponseEntity<Message> getMessageByID(@PathVariable int messageId){
        Optional<Message> ret = messageService.getMessageById(messageId);
        if(ret.isEmpty()){
            return ResponseEntity.status(200).body(null);
        }else{
            return ResponseEntity.status(200).body(ret.get());
        }
    }

    @DeleteMapping("{messageId}")
    public ResponseEntity<Integer> deleteMessage(@PathVariable int messageId){
        boolean exists = messageService.deleteMessage(messageId);
        if(exists){
            return ResponseEntity.status(200).body(1);
        }else{
            return ResponseEntity.status(200).body(null);
        }
    }

    @PatchMapping("{messageId}")
    public ResponseEntity<Integer> updateMessage(@PathVariable int messageId, @RequestBody Message message) throws ClientErrorException{
        messageService.updateMessage(messageId, message.getMessageText());
        return ResponseEntity.status(200).body(1);
    }
}
