package com.fsproject.ppmtool.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RestController
public class CustomResponseEntityExceptionHandler extends ResponseEntityExceptionHandler
{
	@ExceptionHandler
	public final ResponseEntity<Object> handleObjectIdException(ProjectIdException e, WebRequest w)
	{
		ProjectIdExceptionResponse exceptionResponse = new ProjectIdExceptionResponse(e.getMessage());
		return new ResponseEntity(exceptionResponse, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler
	public final ResponseEntity<Object> handleProjectNotFoundException(ProjectNotFoundException e, WebRequest w)
	{
		ProjectNotFoundExceptionResponse exceptionResponse = new ProjectNotFoundExceptionResponse(e.getMessage());
		return new ResponseEntity(exceptionResponse, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler
	public final ResponseEntity<Object> handleNonUniqueUsernameException(NonUniqueUsernameException e, WebRequest w)
	{
		NonUniqueUsernameExceptionResponse exceptionResponse = new NonUniqueUsernameExceptionResponse(e.getMessage());
		return new ResponseEntity(exceptionResponse, HttpStatus.BAD_REQUEST);
	}
}
