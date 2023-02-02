package asia.ncc.application.service;

import asia.ncc.application.dto.EmployeeDTO;
import asia.ncc.application.entity.Employee;
import asia.ncc.application.payload.DeleteResponse;
import asia.ncc.application.repository.EmployeeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private ModelMapper modelMapper;

    public EmployeeDTO get(String username) {
        Employee employee = employeeRepository.findByUsername(username);
        if (employee == null)
            return null;
        return modelMapper.map(employee, EmployeeDTO.class);
    }

    public List<EmployeeDTO> filter(String projectCode, String inputName) {
        return null;
    }

    public EmployeeDTO save(EmployeeDTO employeeDTO) {
        Employee employee = employeeRepository.findByUsername(employeeDTO.getUsername());
        if (employee == null) {
            employee = modelMapper.map(employeeDTO, Employee.class);
            employee.setPassword("12345");
        } else {
            employee.setFirstname(employeeDTO.getFirstName());
            employee.setLastname(employeeDTO.getLastName());
            employee.setPhone(employeeDTO.getPhone());
            employee.setBranch(employeeDTO.getBranch());
            employee.setStatus(employeeDTO.getStatus());
            employee.setRole(employeeDTO.getRole());
        }
        employee = employeeRepository.save(employee);
        return modelMapper.map(employee, EmployeeDTO.class);
    }

    public DeleteResponse delete(String username) {
        Employee employee = employeeRepository.findByUsername(username);
        if (employee == null)
            return new DeleteResponse(false, "Employee not found");
        employeeRepository.delete(employee);
        employee = employeeRepository.findByUsername(username);
        if (employee == null)
            return new DeleteResponse(true, null);
        else
            return new DeleteResponse(false, "Unexpected error");
    }

    public void changeStatus(String username) {
        Employee employee = employeeRepository.findByUsername(username);
        employee.setStatus(employee.getStatus() ^ 1);
        employeeRepository.save(employee);
    }
}
