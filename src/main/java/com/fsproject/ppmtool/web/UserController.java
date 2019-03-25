package com.fsproject.ppmtool.web;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fsproject.ppmtool.domain.User;
import com.fsproject.ppmtool.payload.JWTLoginSuccessResponse;
import com.fsproject.ppmtool.payload.LoginRequest;
import com.fsproject.ppmtool.security.JwtTokenProvider;
import com.fsproject.ppmtool.security.SecurityConstants;
import com.fsproject.ppmtool.services.UserService;
import com.fsproject.ppmtool.services.ValidationErrorMapService;
import com.fsproject.ppmtool.validator.UserValidator;

@RestController
@RequestMapping("/api/users")
public class UserController
{
	@Autowired
	private UserService userService;
	
	@Autowired
	private ValidationErrorMapService validationErrorMapService;
	
	@Autowired
	private UserValidator userValidator;
	
	@Autowired
	private JwtTokenProvider tokenProvider;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@PostMapping("/login")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest, BindingResult result)
	{		
		ResponseEntity<?> errorMap = validationErrorMapService.ValidationService(result);
		if(errorMap != null)
		{
			return errorMap;
		}
		
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String token = SecurityConstants.TOKEN_PREFIX + tokenProvider.generateToken(authentication);
		
		return ResponseEntity.ok(new JWTLoginSuccessResponse(true, token));
	}
	
	@PostMapping("/register")
	public ResponseEntity<?> registerUser(@Valid @RequestBody User user, BindingResult result)
	{
		userValidator.validate(user, result);
		
		ResponseEntity<?> errorMap = validationErrorMapService.ValidationService(result);
		if(errorMap != null)
		{
			return errorMap;
		}
		
		User newUser = userService.saveUser(user);
		
		return new ResponseEntity<User>(newUser, HttpStatus.CREATED);
	}
}
