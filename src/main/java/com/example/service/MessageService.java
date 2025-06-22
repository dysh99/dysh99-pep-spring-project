package com.example.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.exception.ClientErrorException;
import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;

@Service
public class MessageService {
    AccountRepository accountRepository;
    MessageRepository messageRepository;

    @Autowired
    public MessageService(AccountRepository accountRepository, MessageRepository messageRepository){
        this.accountRepository = accountRepository;
        this.messageRepository = messageRepository;
    }

    public Message createMessage(Message message) throws ClientErrorException{
        if(message.getMessageText().isEmpty()) throw new ClientErrorException("Message is blank");
        if(message.getMessageText().length() > 255) throw new ClientErrorException("Message is too long");

        Optional<Account> account = accountRepository.findById(message.getPostedBy());
        if(account.isEmpty()) throw new ClientErrorException("Posted by doesn't reference a real acconut");

        return messageRepository.save(new Message(message.getPostedBy(), message.getMessageText(), message.getTimePostedEpoch()));
    }

    public List<Message> getAllMessages(){
        return messageRepository.findAll();
    }

    public Optional<Message> getMessageById(int messageId){
        return messageRepository.findById(messageId);
    }

    public boolean deleteMessage(int messageId){
        if(messageRepository.existsById(messageId)){
            messageRepository.deleteById(messageId);
            return true;
        }
        return false;
    }

    public void updateMessage(int messageId, String text) throws ClientErrorException{
        if(text.isEmpty()) throw new ClientErrorException("text is empty");
        if(text.length() > 255) throw new ClientErrorException("text is too long");

        Optional<Message> message = messageRepository.findById(messageId);
        if(message.isEmpty()) throw new ClientErrorException("message doesn't exist");
        
        Message updatedMessage = message.get();
        updatedMessage.setMessageText(text);
    }

    public List<Message> getMessagesByAccount(int accountId){
        return messageRepository.findByPostedBy(accountId);
    }
}
