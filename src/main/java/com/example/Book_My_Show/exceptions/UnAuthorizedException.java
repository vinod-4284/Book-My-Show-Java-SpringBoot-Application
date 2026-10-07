package com.example.Book_My_Show.exceptions;

public class UnAuthorizedException extends RuntimeException{
    public UnAuthorizedException(String message){
       super(message);
    }
}
