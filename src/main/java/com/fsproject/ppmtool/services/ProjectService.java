package com.fsproject.ppmtool.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fsproject.ppmtool.domain.Project;
import com.fsproject.ppmtool.exception.ProjectIdException;
import com.fsproject.ppmtool.repositories.ProjectRepository;

@Service
public class ProjectService
{
	@Autowired
	private ProjectRepository projectRepository;
	
	public Project saveOrUpdateProject(Project project)
	{
		try
		{
			project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
			return projectRepository.save(project);
		}
		catch (Exception e)
		{
			throw new ProjectIdException("Project identifier '" + project.getProjectIdentifier().toUpperCase() + "' already exists.");
		}
	}
	
	public Project findByProjectIdentifier(String projectId)
	{
		return projectRepository.findByProjectIdentifier(projectId);
	}
	
	public Iterable<Project> findAllProjects()
	{
		return projectRepository.findAll();
	}
	
	public void deleteProjectByIdentifier(String projectId)
	{
		Project project = projectRepository.findByProjectIdentifier(projectId.toUpperCase());
		
		if(project == null)
		{
			throw new ProjectIdException("Cannot delete project with ID '" + projectId.toUpperCase() + "'. This project does not exist.");
		}
		
		projectRepository.delete(project);
	}
}
