package com.sameer.zapurl.exceptions;

import com.sameer.zapurl.dtos.APIResponse;
import com.sameer.zapurl.exceptions.customexceptions.AlreadyExistsBackhalf;
import com.sameer.zapurl.exceptions.customexceptions.EmptyBackHalfException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(EmptyBackHalfException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public APIResponse emptyBackhalfHandler(EmptyBackHalfException exception) {
        return new APIResponse(exception.getMessage(), "BackHalf -> null/empty");
    }

    @ExceptionHandler(AlreadyExistsBackhalf.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public APIResponse alreadyExistsBackhalf(AlreadyExistsBackhalf exception) {
        return new APIResponse(exception.getMessage(), "BackHalf -> exists ");
    }
}
