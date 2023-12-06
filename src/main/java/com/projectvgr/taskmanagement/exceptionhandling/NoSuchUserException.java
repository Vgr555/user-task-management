package com.projectvgr.taskmanagement.exceptionhandling;

public class NoSuchUserException extends RuntimeException{
    public NoSuchUserException(String msg){
        super(msg);
    }
}
