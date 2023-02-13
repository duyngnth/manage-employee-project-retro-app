package asia.ncc.application.service;

import asia.ncc.application.dto.RequestAssignmentDTO;
import asia.ncc.application.dto.ResponseAssignmentDTO;
import asia.ncc.application.entity.Assignment;
import asia.ncc.application.entity.ProjectRole;
import asia.ncc.application.exception.AssignmentException;
import asia.ncc.application.exception.EntityNotFoundException;
import asia.ncc.application.repository.AssignmentRepository;
import asia.ncc.application.repository.EmployeeRepository;
import asia.ncc.application.repository.ProjectRepository;
import asia.ncc.application.repository.ProjectRoleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AssignmentService {
    @Autowired
    private AssignmentRepository assignmentRepository;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private ProjectRoleRepository projectRoleRepository;
    @Autowired
    private ModelMapper modelMapper;

    private Assignment toEntity(RequestAssignmentDTO assignmentDTO) {
        Assignment assignment = new Assignment();
        assignment.setId(assignmentDTO.getId());
        assignment.setEmployee(employeeRepository.findById(assignmentDTO.getEmployeeId()).orElse(null));
        assignment.setProject(projectRepository.findById(assignmentDTO.getProjectId()).orElse(null));
        assignment.setProjectRole(projectRoleRepository.findById(assignmentDTO.getProjectRoleId()).orElse(null));
        return assignment;
    }

    public ResponseAssignmentDTO get(int id) throws EntityNotFoundException {
        Assignment assignment = assignmentRepository.findById(id).orElse(null);
        if (assignment == null)
            throw new EntityNotFoundException("Assignment not found");
        return modelMapper.map(assignment, ResponseAssignmentDTO.class);
    }

    public List<ResponseAssignmentDTO> list(int projectId) throws EntityNotFoundException {
        if (!projectRepository.existsById(projectId))
            throw new EntityNotFoundException("Project not found");
        List<Assignment> assignments = assignmentRepository.findAllByProjectId(projectId);
        return assignments.stream()
                .map(assignment -> modelMapper.map(assignment, ResponseAssignmentDTO.class))
                .collect(Collectors.toList());
    }

    public ResponseAssignmentDTO add(RequestAssignmentDTO assignmentDTO) throws AssignmentException {
        if (assignmentRepository.existsByEmployeeIdAndProjectId(
                assignmentDTO.getEmployeeId(),
                assignmentDTO.getProjectId()
        ))
            throw new AssignmentException("Duplicated assignment");
        if (assignmentDTO.getProjectRoleId() == null)
            assignmentDTO.setProjectRoleId(2);
        else
            if (!projectRoleRepository.findById(assignmentDTO.getProjectRoleId()).isPresent())
                throw new AssignmentException("Invalid project role");
        Assignment assignment = toEntity(assignmentDTO);
        return modelMapper.map(assignmentRepository.save(assignment), ResponseAssignmentDTO.class);
    }

    public ResponseAssignmentDTO changeRole(RequestAssignmentDTO assignmentDTO) throws EntityNotFoundException {
        Assignment assignment = assignmentRepository.findByEmployeeIdAndProjectId(
                assignmentDTO.getEmployeeId(),
                assignmentDTO.getProjectId()
        );
        if (assignment == null)
            throw new EntityNotFoundException("Assignment not found");
        if (assignmentDTO.getProjectRoleId() == 1) {
            assignment.setProjectRole(projectRoleRepository.findById(1).get());
            // Change current PM to DEV
            Assignment currentPM = assignmentRepository.findByProjectIdAndProjectRoleId(
                    assignmentDTO.getProjectId(),
                    1
            );
            if (currentPM != null) {
                currentPM.setProjectRole(projectRoleRepository.findById(2).get());
                assignmentRepository.save(currentPM);
            }
        }
        ProjectRole role = projectRoleRepository.findById(assignmentDTO.getProjectRoleId()).orElse(null);
        if (role == null)
            throw new EntityNotFoundException("ProjectRole not found");
        assignment.setProjectRole(role);
        return modelMapper.map(assignmentRepository.save(assignment), ResponseAssignmentDTO.class);
    }

    public boolean delete(RequestAssignmentDTO assignmentDTO) throws EntityNotFoundException {
        Assignment assignment = assignmentRepository.findByEmployeeIdAndProjectId(
                assignmentDTO.getEmployeeId(),
                assignmentDTO.getProjectId()
        );
        if (assignment == null)
            throw new EntityNotFoundException("Assignment not found");
        assignmentRepository.delete(assignment);
        return true;
    }
}
