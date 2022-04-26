package com.mall.elite.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@AllArgsConstructor
@Data
public class ExpectionResponseDTO {
    @JsonProperty("status-code")
    private int statusCode;
    private Date date;
    private String message;
    private String details;
}
