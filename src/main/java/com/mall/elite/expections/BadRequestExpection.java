package com.mall.elite.expections;

import org.springframework.http.HttpStatus;

public class BadRequestExpection extends RuntimeException{
    private final HttpStatus STATUS;

    public BadRequestExpection(String message) {
        super(message);
        STATUS = HttpStatus.BAD_REQUEST;
    }
}
