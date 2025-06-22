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
        if(username.isEmpty()) throw new ClientErrorException("Username is empty");
        if(password.length() < 4) throw new ClientErrorException("Password is too short");
        
        List<Account> accounts = accountRepository.findByUsername(username);
        if(accounts.size() > 0) throw new DuplicateUsernameException("This username is already taken");

        
        return accountRepository.save(new Account(username, password));
    }

    public Account login(String username, String password) throws UnauthorizedException{
        List<Account> accounts = accountRepository.findByUsernameAndPassword(username, password);
        if(accounts.size() == 1) return accounts.get(0);

        throw new UnauthorizedException("No account exists with this username and password");
    }
}
