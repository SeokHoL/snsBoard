package com.seokho.snsboard.exception.reply;

import com.seokho.snsboard.exception.ClientErrorException;
import org.springframework.http.HttpStatus;

public class ReplyNotFoundException extends ClientErrorException {

    public ReplyNotFoundException(){
        super(HttpStatus.NOT_FOUND, "Reply not found");
    }

    public ReplyNotFoundException(Long postId){
        super(HttpStatus.NOT_FOUND, "Reply with postId" + postId + "not found");
    }

    public ReplyNotFoundException(String message){
        super(HttpStatus.NOT_FOUND, message);
    }
}
