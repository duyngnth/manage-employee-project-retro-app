package asia.ncc.application.controller.api;

import asia.ncc.application.dto.RequestAssignmentDTO;
import asia.ncc.application.dto.ResponseAssignmentDTO;
import asia.ncc.application.exception.AssignmentException;
import asia.ncc.application.exception.EntityNotFoundException;
import asia.ncc.application.payload.ApplicationResponse;
import asia.ncc.application.service.AssignmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/assignments")
public class AssignmentController {
    @Autowired
    private AssignmentService assignmentService;

    @PostMapping
    public ApplicationResponse<ResponseAssignmentDTO> add(@RequestBody RequestAssignmentDTO assignmentDTO)
            throws AssignmentException {
        return ApplicationResponse.succeed(assignmentService.add(assignmentDTO));
    }

    @GetMapping("/{id}")
    public ApplicationResponse<ResponseAssignmentDTO> get(@PathVariable int id)
            throws EntityNotFoundException {
        return ApplicationResponse.succeed(assignmentService.get(id));
    }

    @GetMapping("/project/{projectId}")
    public ApplicationResponse<List<ResponseAssignmentDTO>> listByProject(@PathVariable int projectId)
            throws EntityNotFoundException {
        return ApplicationResponse.succeed(assignmentService.list(projectId));
    }

    @PutMapping("/role")
    public ApplicationResponse<ResponseAssignmentDTO> changeStatus(
            @RequestBody RequestAssignmentDTO assignmentDTO
    ) throws EntityNotFoundException {
        return ApplicationResponse.succeed(assignmentService.changeRole(assignmentDTO));
    }

    @DeleteMapping
    public ApplicationResponse delete(@RequestBody RequestAssignmentDTO assignmentDTO)
            throws EntityNotFoundException {
        return new ApplicationResponse(null, assignmentService.delete(assignmentDTO), null);
    }
}
