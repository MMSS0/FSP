package com.fsproject.ppmtool.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.fsproject.ppmtool.domain.User;
import com.fsproject.ppmtool.exception.NonUniqueUsernameException;
import com.fsproject.ppmtool.repositories.UserRepository;

@Service
public class UserService
{
	 @Autowired
	 private UserRepository userRepository;
	 
	 @Autowired
	 private BCryptPasswordEncoder bCrypt;
	 
	 public User saveUser(User newUser)
	 {
//		 if(userRepository.findUserByUsername(newUser.getUsername()) != null)
//		 {
//			 throw new NonUniqueUsernameException("Username '" + newUser.getUsername() + "' already exists.");
//		 }
		 try
		 {
			 newUser.setPassword(bCrypt.encode(newUser.getPassword()));
			 newUser.setUsername(newUser.getUsername());
			 newUser.setConfirmPassword("");
			 return userRepository.save(newUser);
		 }
		 catch (Exception e)
		 {
			 throw new NonUniqueUsernameException("Username '" + newUser.getUsername() + "' already exists.");
		 }
	 }
}
