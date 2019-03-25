package com.fsproject.ppmtool.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.fsproject.ppmtool.domain.User;

@Component
public class UserValidator implements Validator
{

	@Override
	public boolean supports(Class<?> clazz)
	{
		return User.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors)
	{
		User user = (User) target;
		if(user.getPassword().length() < 6)
		{
			errors.rejectValue("password", "Length", "Password must be at least 6 characters long.");
		}
		
		if(!user.getPassword().equals(user.getConfirmPassword()))
		{
			errors.rejectValue("password", "Match", "Passwords must match.");
		}
	}

}
