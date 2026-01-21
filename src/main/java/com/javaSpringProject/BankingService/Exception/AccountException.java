package com.javaSpringProject.BankingService.Exception;

public class AccountException extends RuntimeException{
    public AccountException(String message){
        super(message);
    }
}
