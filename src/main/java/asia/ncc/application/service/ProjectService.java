package asia.ncc.application.service;

import asia.ncc.application.dto.ProjectDTO;
import asia.ncc.application.entity.Project;
import asia.ncc.application.exception.EntityNotFoundException;
import asia.ncc.application.exception.ProjectException;
import asia.ncc.application.repository.ProjectRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private ModelMapper modelMapper;

    public ProjectDTO get(int id) throws EntityNotFoundException {
        Project project = projectRepository.findById(id).orElse(null);
        if (project == null)
            throw new EntityNotFoundException("Project not found");
        return modelMapper.map(project, ProjectDTO.class);
    }

    public List<ProjectDTO> list(Pageable pageable) {
        List<Project> projects = projectRepository.findAll(pageable).toList();
        List<ProjectDTO> projectDTOs = new ArrayList<>();
        for (Project project : projects)
            projectDTOs.add(modelMapper.map(project, ProjectDTO.class));
        return projectDTOs;
    }

    public ProjectDTO add(ProjectDTO projectDTO) throws ProjectException {
        // Check if project code is existed
        Project project = projectRepository.findByCode(projectDTO.getCode());
        if (project != null)
            throw new ProjectException("Duplicated project code");
        project = modelMapper.map(projectDTO, Project.class);
        return modelMapper.map(projectRepository.save(project), ProjectDTO.class);
    }

    public ProjectDTO update(int id, ProjectDTO projectDTO)
            throws EntityNotFoundException, ProjectException {
        // Check if project is existed
        Project project = projectRepository.findById(id).orElse(null);
        if (project == null)
            throw new EntityNotFoundException("Project not found");
        // Check if project code is existed
        project = projectRepository.findByCode(projectDTO.getCode());
        if (project != null && project.getId() != id)
            throw new ProjectException("Duplicated project code");
        project = modelMapper.map(projectDTO, Project.class);
        project.setId(id);
        return modelMapper.map(projectRepository.save(project), ProjectDTO.class);
    }

    public boolean delete(int id) throws EntityNotFoundException {
        // Check if project is existed
        Project project = projectRepository.findById(id).orElse(null);
        if (project == null)
            throw new EntityNotFoundException("Project not found");
        projectRepository.delete(project);
        return true;
    }

    public boolean changeStatus(int id, int status) throws EntityNotFoundException {
        Project project = projectRepository.findById(id).orElse(null);
        if (project == null)
            throw new EntityNotFoundException("Project not found");
        project.setStatus(status);
        projectRepository.save(project);
        return true;
    }
}