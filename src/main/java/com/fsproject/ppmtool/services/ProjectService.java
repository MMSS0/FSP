package com.fsproject.ppmtool.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fsproject.ppmtool.domain.Project;
import com.fsproject.ppmtool.repositories.ProjectRepository;

@Service
public class ProjectService
{
	@Autowired
	private ProjectRepository projectRepository;
	
	public Project saveOrUpdateProject(Project project)
	{
		//validate, run checks
		
		return projectRepository.save(project);
	}
}
