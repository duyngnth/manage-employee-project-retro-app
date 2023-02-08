package asia.ncc.application.controller.api;

import asia.ncc.application.dto.ProjectDTO;
import asia.ncc.application.exception.EntityNotFoundException;
import asia.ncc.application.exception.ProjectException;
import asia.ncc.application.payload.ApplicationResponse;
import asia.ncc.application.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProjectController {
    @Autowired
    private ProjectService projectService;

    @PostMapping("projects")
    public ApplicationResponse<ProjectDTO> add(@RequestBody ProjectDTO projectDTO)
            throws ProjectException {
        return ApplicationResponse.succeed(projectService.add(projectDTO));
    }

    @GetMapping("projects/{id}")
    public ApplicationResponse<ProjectDTO> get(@PathVariable int id)
            throws EntityNotFoundException {
        return ApplicationResponse.succeed(projectService.get(id));
    }

    @GetMapping("projects")
    public ApplicationResponse<List<ProjectDTO>> list() {
        return ApplicationResponse.succeed(projectService.list());
    }

    @PutMapping("projects/{id}")
    public ApplicationResponse<ProjectDTO> update(@PathVariable int id,
                                                  @RequestBody ProjectDTO projectDTO)
            throws ProjectException, EntityNotFoundException {
        return ApplicationResponse.succeed(projectService.update(id, projectDTO));
    }

    @DeleteMapping("projects/{id}")
    public ApplicationResponse delete(@PathVariable int id)
            throws EntityNotFoundException {
        return new ApplicationResponse(null, projectService.delete(id), null);
    }

    @PutMapping("projects/{id}/changeStatus/{status}")
    public ApplicationResponse changeStatus(@PathVariable int id, @PathVariable int status)
            throws EntityNotFoundException {
        return new ApplicationResponse(null, projectService.changeStatus(id ,status), null);
    }
}
