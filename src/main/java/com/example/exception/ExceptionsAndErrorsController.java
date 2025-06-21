package com.example.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionsAndErrorsController {
    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public @ResponseBody String handleMissingParams(MissingServletRequestParameterException ex){
        return ex.getParameterName() + " is missing in the query paramiters";
    }

    @ExceptionHandler(DuplicateUsernameException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public @ResponseBody void handleDubplicateUsernames(){

    }

    @ExceptionHandler(ClientErrorException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public @ResponseBody void handleClientError(){

    }

    @ExceptionHandler(UnauthorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public @ResponseBody void handleUnauthorizedError(){

    }
}
