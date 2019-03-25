package com.fsproject.ppmtool.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fsproject.ppmtool.domain.Backlog;
import com.fsproject.ppmtool.domain.Project;
import com.fsproject.ppmtool.domain.User;
import com.fsproject.ppmtool.exception.ProjectIdException;
import com.fsproject.ppmtool.exception.ProjectNotFoundException;
import com.fsproject.ppmtool.repositories.BacklogRepository;
import com.fsproject.ppmtool.repositories.ProjectRepository;
import com.fsproject.ppmtool.repositories.UserRepository;

@Service
public class ProjectService
{
	@Autowired
	private ProjectRepository projectRepository;
	
	@Autowired
	private BacklogRepository backlogRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	public Project saveOrUpdateProject(Project project, String username)
	{
		if(project.getId() != null)
		{
			Project existingProject = projectRepository.findByProjectIdentifier(project.getProjectIdentifier());
			if(existingProject != null && (!existingProject.getProjectLeader().equals(username))) {
				throw new ProjectNotFoundException("Project not found in your account");
			}
			if(existingProject == null)
			{
				throw new ProjectNotFoundException("Project with ID: '" + project.getId() + "' cannot be updated because it does not exist.");
			}
		}
		
		try
		{
			User user = userRepository.findUserByUsername(username);
			
			project.setUser(user);
			project.setProjectLeader(user.getUsername());			
			project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
			
			if(project.getId() == null)
			{
				Backlog backlog = new Backlog();
				project.setBacklog(backlog);
			}
			
			else
			{
				project.setBacklog(backlogRepository.findByProjectIdentifier(project.getProjectIdentifier()));
			}
			
			return projectRepository.save(project);
		}
		catch (Exception e)
		{
			throw new ProjectIdException("Project identifier '" + project.getProjectIdentifier().toUpperCase() + "' already exists.");
		}
	}
	
	public Project findByProjectIdentifier(String projectId, String username)
	{
		Project project = projectRepository.findByProjectIdentifier(projectId.toUpperCase());
		
		if(project == null)
		{
			throw new ProjectIdException("Project identifier '" + projectId.toUpperCase() + "' does not exist.");
		}
		
		if(!project.getProjectLeader().equals(username))
		{
			throw new ProjectNotFoundException("Project not found in your account");
		}
		
		return project;
	}
	
	public Iterable<Project> findAllProjects(String username)
	{
		return projectRepository.findAllByProjectLeader(username);
	}
	
	public void deleteProjectByIdentifier(String projectId, String username)
	{
		projectRepository.delete(findByProjectIdentifier(projectId, username));
	}
}
