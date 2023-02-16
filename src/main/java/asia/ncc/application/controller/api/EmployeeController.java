package asia.ncc.application.controller.api;

import asia.ncc.application.dto.EmployeeDTO;
import asia.ncc.application.exception.EmployeeException;
import asia.ncc.application.exception.EntityNotFoundException;
import asia.ncc.application.payload.ApplicationResponse;
import asia.ncc.application.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("api/employees")
@RequiredArgsConstructor
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;
    @Value("${page.size.default}")
    private final int DEFAULT_PAGE_SIZE;

    @PostMapping("/fetch")
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

    @GetMapping("/{id}")
    public ApplicationResponse<EmployeeDTO> get(@PathVariable int id)
            throws EntityNotFoundException {
        EmployeeDTO employeeDTO = employeeService.get(id);
        return ApplicationResponse.succeed(employeeDTO);
    }

    @GetMapping
    public ApplicationResponse<List<EmployeeDTO>> filter(
            @RequestParam(value = "projectCode", required = false) String projectCode,
            @RequestParam(value = "inputName", required = false) String inputName,
            @RequestParam(value = "pageIndex", required = false, defaultValue = "0") int pageIndex,
            @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        if (pageSize == null)
            pageSize = DEFAULT_PAGE_SIZE;
        Pageable pageable = PageRequest.of(pageIndex, pageSize);
        List<EmployeeDTO> filterResult = employeeService.filter(projectCode, inputName, pageable);
        return ApplicationResponse.succeed(filterResult);
    }

    @PostMapping
    public ApplicationResponse<EmployeeDTO> add(@RequestBody EmployeeDTO employeeDTO)
            throws EmployeeException {
        employeeDTO = employeeService.add(employeeDTO);
        return ApplicationResponse.succeed(employeeDTO);
    }

    @PutMapping
    public ApplicationResponse<EmployeeDTO> update(@RequestBody EmployeeDTO employeeDTO)
            throws EntityNotFoundException {
        employeeDTO = employeeService.update(employeeDTO);
        return ApplicationResponse.succeed(employeeDTO);
    }

    @DeleteMapping("/{id}")
    public ApplicationResponse delete(@PathVariable int id) throws EntityNotFoundException {
        return new ApplicationResponse(null, employeeService.delete(id), null);
    }

    @PutMapping("/{id}/status/{status}")
    public ApplicationResponse<EmployeeDTO> changeStatus(@PathVariable int id, @PathVariable int status)
            throws EntityNotFoundException {
        return ApplicationResponse.succeed(employeeService.changeStatus(id, status));
    }
}
