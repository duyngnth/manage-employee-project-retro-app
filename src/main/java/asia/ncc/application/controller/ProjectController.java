package asia.ncc.application.controller;

import asia.ncc.application.dto.ProjectDTO;
import asia.ncc.application.entity.Project;
import asia.ncc.application.payload.DeleteResponse;
import asia.ncc.application.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
public class ProjectController {
    @Autowired
    private ProjectService projectService;

    @PostMapping("project")
    public ProjectDTO add(@RequestBody ProjectDTO projectDTO) {
        return projectService.save(projectDTO);
    }

    @GetMapping("project/{code}")
    public ProjectDTO get(@PathVariable String code) {
        return projectService.get(code);
    }

    @PutMapping("project")
    public ProjectDTO update(@RequestBody ProjectDTO projectDTO) {
        return projectService.save(projectDTO);
    }

    @DeleteMapping("project/{code}")
    public DeleteResponse delete(@PathVariable String code) {
        return projectService.delete(code);
    }
}
