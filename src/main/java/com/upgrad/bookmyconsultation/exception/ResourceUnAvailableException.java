package com.upgrad.bookmyconsultation.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NO_CONTENT)
public class ResourceUnAvailableException extends RuntimeException{

    public ResourceUnAvailableException(String message){
        super(String.format(message));
    }

}
