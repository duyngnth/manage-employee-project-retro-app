package asia.ncc.application.controller;

import asia.ncc.application.dto.EmployeeDTO;
import asia.ncc.application.entity.Employee;
import asia.ncc.application.payload.DeleteResponse;
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
    EmployeeService employeeService;

    @PostMapping("employee/fetch")
    public ResponseEntity fetch(@RequestBody EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        Random rd = new Random();
        System.out.println(employeeDTO);

        String email = employeeDTO.getEmail();
        employee.setFirstname(employeeDTO.getFirstName());
        employee.setLastname(employeeDTO.getLastName());
        employee.setEmail(email);
        employee.setUsername(email.substring(0, email.length() - 9));
        employee.setPassword("12345");
        // Random phone number
        String phone = "09";
        for (int i = 0; i < 8; i++) {
            phone += String.valueOf(rd.nextInt(10));
        }
        employee.setPhone(phone);
        // Random branch
        String branch[] = {"HN1", "HN2", "HN3", "SG1", "SG2", "DN", "QN", "VINH"};
        employee.setBranch(branch[rd.nextInt(branch.length)]);
        employee.setStatus(1);
        String role = "ROLE_";
        if (employeeDTO.getEmail().contains("duy.nguyenthe"))
            role += "ADMIN";
        else
            role += "USER";
        employee.setRole(role);
        //employeeRepository.save(employee);

        return ResponseEntity.ok().body(employee);
    }

    @GetMapping("employee/{username}")
    public ResponseEntity<EmployeeDTO> get(@PathVariable String username) {
        EmployeeDTO employeeDTO = employeeService.get(username);
        if (employeeDTO == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(employeeDTO);
    }

    @GetMapping("employee")
    public List<EmployeeDTO> filter(@RequestParam(value = "projectCode", required = false) String projectCode,
                                    @RequestParam(value = "inputName", required = false) String inputName) {
        return employeeService.filter(projectCode, inputName);
    }

    @PostMapping("employee")
    public ResponseEntity<EmployeeDTO> add(@RequestBody EmployeeDTO employeeDTO) {
        return ResponseEntity.ok(employeeService.save(employeeDTO));
    }

    @PutMapping("employee")
    public ResponseEntity<EmployeeDTO> update(@RequestBody EmployeeDTO employeeDTO) {
        return ResponseEntity.ok(employeeService.save(employeeDTO));
    }

    @DeleteMapping("employee/{username}")
    public ResponseEntity<DeleteResponse> delete(@PathVariable String username) {
        return ResponseEntity.ok(employeeService.delete(username));
    }

}
