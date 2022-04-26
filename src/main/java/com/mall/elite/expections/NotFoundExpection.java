package com.mall.elite.expections;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class NotFoundExpection extends RuntimeException{
    private final HttpStatus STATUS;


    public NotFoundExpection(String message) {
        super(message);
        STATUS = HttpStatus.NOT_FOUND;
    }
}
