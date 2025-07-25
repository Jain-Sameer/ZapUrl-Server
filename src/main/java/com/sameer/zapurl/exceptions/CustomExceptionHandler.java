package com.sameer.zapurl.exceptions;

import com.sameer.zapurl.dtos.APIResponse;
import com.sameer.zapurl.exceptions.customexceptions.EmptyBackHalfException;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class ExceptionHandler {

    @ExceptionHandler(EmptyBackHalfException.class)
    public APIResponse emptyBackhalfHandler(EmptyBackHalfException exception) {
        return new APIResponse(exception.getMessage(), "FieldName : BackHalf");
    }
}
