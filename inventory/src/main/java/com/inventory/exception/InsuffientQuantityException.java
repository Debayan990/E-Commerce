package com.inventory.exception;

public class InsuffientQuantityException extends RuntimeException{
    public InsuffientQuantityException(String message){
        super(message);
    }
}
