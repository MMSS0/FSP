package com.fsproject.ppmtool.exception;

public class NonUniqueUsernameExceptionResponse
{
	private String NonUniqueUsername;

    public NonUniqueUsernameExceptionResponse(String nonUniqueUsername) {
        NonUniqueUsername = nonUniqueUsername;
    }

    public String getNonUniqueUsername() {
        return NonUniqueUsername;
    }

    public void setNonUniqueUsername(String nonUniqueUsername) {
    	NonUniqueUsername = nonUniqueUsername;
    }
}
