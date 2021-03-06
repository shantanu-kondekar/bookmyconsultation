package com.upgrad.bookmyconsultation.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class SlotUnavailableException extends RuntimeException {


    public SlotUnavailableException(String message){
        super(String.format(message));
    }


}
