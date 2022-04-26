package com.mall.elite.expections;

import com.mall.elite.dto.response.ExpectionResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.function.EntityResponse;

import java.util.Date;

@RestControllerAdvice
@Slf4j
public class GlobalExpectionHandler {
    @ExceptionHandler(NotFoundExpection.class)
    public ResponseEntity<ExpectionResponseDTO> handleNotFoundExpection(NotFoundExpection in, WebRequest request){
        ExpectionResponseDTO expectionResponse = new ExpectionResponseDTO(
                in.getSTATUS().value(), new Date(), in.getMessage(), request.getDescription(false)
        );
        return new ResponseEntity<>(expectionResponse, in.getSTATUS());
    }
    @ExceptionHandler(BadRequestExpection.class)
    public ResponseEntity<ExpectionResponseDTO> handleBadRequestExpection(NotFoundExpection in, WebRequest request){
        ExpectionResponseDTO expectionResponseDTO = new ExpectionResponseDTO(
                in.getSTATUS().value(), new Date(), in.getMessage(), request.getDescription(false)
        );
        return new ResponseEntity<>(expectionResponseDTO, in.getSTATUS());
    }
}
