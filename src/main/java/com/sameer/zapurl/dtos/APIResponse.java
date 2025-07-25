package com.sameer.zapurl.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class APIResponse {
    private String message;
    private String field;
}
