package com.sameer.zapurl.exceptions.customexceptions;

public class AlreadyExistsBackhalf extends RuntimeException{
    public AlreadyExistsBackhalf(String message) {
        super(message);
    }
}
