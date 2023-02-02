package asia.ncc.application.service;

import asia.ncc.application.dto.ProjectDTO;
import asia.ncc.application.entity.Project;
import asia.ncc.application.payload.DeleteResponse;
import asia.ncc.application.repository.ProjectRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private ModelMapper modelMapper;

    public ProjectDTO save(ProjectDTO projectDTO) {
        Project project = projectRepository.findByCode(projectDTO.getCode());
        if (project == null) {
            project = modelMapper.map(projectDTO, Project.class);
        } else {
            project.setName(projectDTO.getName());
            project.setDescription(projectDTO.getDescription());
            project.setStatus(projectDTO.getStatus());
        }
        project = projectRepository.save(project);
        return modelMapper.map(project, ProjectDTO.class);
    }

    public ProjectDTO get(String code) {
        Project project = projectRepository.findByCode(code);
        if (project == null)
            return null;
        return modelMapper.map(project, ProjectDTO.class);
    }

    public DeleteResponse delete(String code) {
        Project project = projectRepository.findByCode(code);
        if (project == null)
            return new DeleteResponse(false, "Project not found");
        projectRepository.delete(project);
        project = projectRepository.findByCode(code);
        if (project == null)
            return new DeleteResponse(true, null);
        return new DeleteResponse(false, "Unexpected error");
    }
}
