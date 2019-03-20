package com.fsproject.ppmtool.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fsproject.ppmtool.domain.Project;
import com.fsproject.ppmtool.services.ProjectService;
import com.fsproject.ppmtool.services.ValidationErrorMapService;

@RestController
@RequestMapping("/api/project")
public class ProjectController
{
	@Autowired
	private ProjectService projectService;
	
	@Autowired
	private ValidationErrorMapService validationErrorMapService;
	
	@PostMapping("")
	public ResponseEntity<?> createNewProject(@Valid @RequestBody Project project, BindingResult result)
	{
		ResponseEntity<?> errorMap = validationErrorMapService.ValidationService(result);
		if(errorMap != null)
		{
			return errorMap;
		}
		
		Project proj = projectService.saveOrUpdateProject(project);
		return new ResponseEntity<Project>(project, HttpStatus.CREATED);
	}
}