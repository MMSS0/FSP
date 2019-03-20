package com.fsproject.ppmtool.services;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

@Service
public class ValidationErrorMapService
{
	public ResponseEntity<?> ValidationService(BindingResult result)
	{
		if(result.hasErrors())
		{
			Map<String, String> errorMap = new HashMap<>();
			result.getFieldErrors().stream().forEach(e -> errorMap.put(e.getField(), e.getDefaultMessage()));
			
			return new ResponseEntity<Map<String, String>>(errorMap, HttpStatus.BAD_REQUEST);
		}
		
		return null;
	}
}
