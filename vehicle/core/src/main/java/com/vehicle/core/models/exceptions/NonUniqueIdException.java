package com.vehicle.core.models.exceptions;

public class NonUniqueIdException extends RuntimeException{
    public NonUniqueIdException(String message){
        super(message);
    }
}
