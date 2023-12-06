package com.projectvgr.taskmanagement.exceptionhandling;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestExceptionHandler {
	
	@ExceptionHandler(NoSuchTaskException.class)
	public ResponseEntity<ErrorInfo> noSuchTaskException(NoSuchTaskException ex) {
		ErrorInfo errorInfo = new ErrorInfo(400, ex.getMessage());
		return new ResponseEntity<>(errorInfo,HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(NoSuchUserException.class)
	public ResponseEntity<ErrorInfo> noSuchUserException(NoSuchUserException ex){
		ErrorInfo errorInfo = new ErrorInfo(400, ex.getMessage());
		return new ResponseEntity<>(errorInfo, HttpStatus.BAD_REQUEST);
	}
}
