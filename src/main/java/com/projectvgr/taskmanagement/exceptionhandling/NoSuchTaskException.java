package com.projectvgr.taskmanagement.exceptionhandling;

public class NoSuchTaskException extends RuntimeException{
	
	public NoSuchTaskException(String msg) {
		super(msg);
	}
}
