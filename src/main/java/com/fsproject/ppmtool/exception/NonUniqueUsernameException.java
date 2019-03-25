package com.fsproject.ppmtool.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class NonUniqueUsernameException extends RuntimeException
{
	public NonUniqueUsernameException(String arg0)
	{
		super(arg0);
		// TODO Auto-generated constructor stub
	}
	
}
