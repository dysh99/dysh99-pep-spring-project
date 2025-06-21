package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.exception.ClientErrorException;
import com.example.exception.DuplicateUsernameException;
import com.example.exception.UnauthorizedException;
import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;

@Service
public class AccountService {
    AccountRepository accountRepository;
    MessageRepository messageRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository, MessageRepository messageRepository){
        this.accountRepository = accountRepository;
        this.messageRepository = messageRepository;
    }

    public Account register(String username, String password) throws ClientErrorException, DuplicateUsernameException{
        if(username.isEmpty()) {
            System.out.println("Username empty");
            throw new ClientErrorException("Username is empty");
        }
        if(password.length() < 4){
            System.out.println("Password too short");
            throw new ClientErrorException("Password is too short");
        }
        List<Account> accounts = accountRepository.findAll();
        for(Account account:accounts){
            if(account.getUsername().equals(username)) throw new DuplicateUsernameException("This username is already taken");
        }

        
        return accountRepository.save(new Account(username, password));
    }

    public Account login(String username, String password) throws UnauthorizedException{
        List<Account> accounts = accountRepository.findAll();
        for(Account account:accounts){
            if(account.getUsername().equals(username) && account.getPassword().equals(password)) return account; 
        }
        throw new UnauthorizedException("No account exists with this username and password");
    }
}
