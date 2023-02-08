package asia.ncc.application.controller.api;

import asia.ncc.application.dto.EmployeeDTO;
import asia.ncc.application.exception.EmployeeException;
import asia.ncc.application.exception.EntityNotFoundException;
import asia.ncc.application.payload.ApplicationResponse;
import asia.ncc.application.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Random;

@RestController
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @PostMapping("employee/fetch")
    public ResponseEntity fetch(@RequestBody EmployeeDTO employeeDTO) {
        Random rd = new Random();

        String email = employeeDTO.getEmail();
        employeeDTO.setUsername(email.substring(0, email.length() - 9));
        // Random phone number
        String phone = "09";
        for (int i = 0; i < 8; i++) {
            phone += String.valueOf(rd.nextInt(10));
        }
        employeeDTO.setPhone(phone);
        // Random branch
        String branch[] = {"HN1", "HN2", "HN3", "SG1", "SG2", "DN", "QN", "VINH"};
        employeeDTO.setBranch(branch[rd.nextInt(branch.length)]);
        employeeDTO.setStatus(1);
        String role = "ROLE_";
        if (employeeDTO.getEmail().contains("duy.nguyenthe"))
            role += "ADMIN";
        else
            role += "USER";
        employeeDTO.setRole(role);
        //employeeService.add(employeeDTO);
        System.out.println("Fetch API: " + employeeDTO);

        return ResponseEntity.ok().body(employeeDTO);
    }

    @GetMapping("employees/{username}")
    public ApplicationResponse<EmployeeDTO> get(@PathVariable String username)
            throws EntityNotFoundException {
        EmployeeDTO employeeDTO = employeeService.get(username);
        return ApplicationResponse.succeed(employeeDTO);
    }

    @GetMapping("employees")
    public ApplicationResponse<List<EmployeeDTO>> filter(
            @RequestParam(value = "projectCode", required = false) String projectCode,
            @RequestParam(value = "inputName", required = false) String inputName) {
        List<EmployeeDTO> filterResult = employeeService.filter(projectCode, inputName);
        return ApplicationResponse.succeed(filterResult);
    }

    @PostMapping("employees")
    public ApplicationResponse<EmployeeDTO> add(@RequestBody EmployeeDTO employeeDTO)
            throws EmployeeException {
        employeeDTO = employeeService.add(employeeDTO);
        return ApplicationResponse.succeed(employeeDTO);
    }

    @PutMapping("employees/{username}")
    public ApplicationResponse<EmployeeDTO> update(@PathVariable String username, @RequestBody EmployeeDTO employeeDTO)
            throws EntityNotFoundException {
        employeeDTO = employeeService.update(username, employeeDTO);
        return ApplicationResponse.succeed(employeeDTO);
    }

    @DeleteMapping("employees/{username}")
    public ApplicationResponse delete(@PathVariable String username) throws EntityNotFoundException {
        return new ApplicationResponse(null, employeeService.delete(username), null);
    }

    @PutMapping("employees/{username}/changeStatus/{status}")
    public ApplicationResponse changeStatus(@PathVariable String username, @PathVariable int status)
            throws EntityNotFoundException {
        return new ApplicationResponse(null, employeeService.changeStatus(username, status), null);
    }
}
