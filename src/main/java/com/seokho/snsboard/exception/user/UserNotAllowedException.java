package com.seokho.snsboard.exception.user;

import com.seokho.snsboard.exception.ClientErrorException;
import org.springframework.http.HttpStatus;

public class UserNotAllowedException extends ClientErrorException {

    public UserNotAllowedException() {
        super(HttpStatus.NOT_FOUND, "User not allowed.");
    }


}
