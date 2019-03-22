package com.fsproject.ppmtool.web;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fsproject.ppmtool.domain.Project;
import com.fsproject.ppmtool.exception.ProjectIdException;
import com.fsproject.ppmtool.repositories.ProjectRepository;
import com.fsproject.ppmtool.services.ProjectService;
import com.fsproject.ppmtool.services.ValidationErrorMapService;

@RestController
@RequestMapping("/api/project")
@CrossOrigin
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
	
	@GetMapping("/{projectId}")
	public ResponseEntity<?> getProjectById(@PathVariable String projectId)
	{
		Project project = projectService.findByProjectIdentifier(projectId.toUpperCase());
		
		if(project == null)
		{
			throw new ProjectIdException("Project identifier '" + projectId.toUpperCase() + "' does not exist.");
		}
		
		return new ResponseEntity<Project>(project, HttpStatus.OK);
	}
	
	@GetMapping("/all")
	public Iterable<Project> findAllProjects()
	{
		return projectService.findAllProjects();
	}
	
	@DeleteMapping("/{projectId}")
	public ResponseEntity<?> deleteProjectById(@PathVariable String projectId)
	{
		projectService.deleteProjectByIdentifier(projectId.toUpperCase());
		
		return new ResponseEntity<String>("Project with ID " + projectId.toUpperCase() + " was successfully deleted.", HttpStatus.OK);
	}
}
