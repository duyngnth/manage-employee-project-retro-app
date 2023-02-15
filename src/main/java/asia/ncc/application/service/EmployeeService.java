package asia.ncc.application.service;

import asia.ncc.application.dto.EmployeeDTO;
import asia.ncc.application.entity.Employee;
import asia.ncc.application.exception.EmployeeException;
import asia.ncc.application.exception.EntityNotFoundException;
import asia.ncc.application.repository.EmployeeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private ModelMapper modelMapper;

    public EmployeeDTO get(int id) throws EntityNotFoundException {
        Employee employee = employeeRepository.findById(id).orElse(null);
        if (employee == null)
            throw new EntityNotFoundException("Employee not found");
        return modelMapper.map(employee, EmployeeDTO.class);
    }

//    public List<EmployeeDTO> filter(String projectCode, String inputName) {
//        if (inputName != null)
//            inputName = "%" + inputName + "%";
//        List<Employee> filterResults = employeeRepository.filter(projectCode, inputName);
//        List<EmployeeDTO> employeeDTOs = new ArrayList<>();
//        for (Employee employee : filterResults)
//            employeeDTOs.add(modelMapper.map(employee, EmployeeDTO.class));
//        return employeeDTOs;
//    }

    public EmployeeDTO add(EmployeeDTO employeeDTO) throws EmployeeException {
        // Check if username is existed
        Employee employee = employeeRepository.findByUsername(employeeDTO.getUsername());
        if (employee != null)
            throw new EmployeeException("Duplicated username");
        // Check if email is existed
        employee = employeeRepository.findByEmail(employeeDTO.getEmail());
        if (employee != null)
            throw new EmployeeException("Duplicated email");
        employee = modelMapper.map(employeeDTO, Employee.class);
        employee.setPassword("12345");
        return modelMapper.map(employeeRepository.save(employee), EmployeeDTO.class);
    }

    public EmployeeDTO update(EmployeeDTO employeeDTO) throws EntityNotFoundException {
        // Check if username is existed
        Employee employee = employeeRepository.findById(employeeDTO.getId()).orElse(null);
        if (employee == null)
            throw new EntityNotFoundException("Employee not found");
        Employee newEmployee = modelMapper.map(employeeDTO, Employee.class);
        newEmployee.setId(employee.getId());
        newEmployee.setUsername(employee.getUsername());
        newEmployee.setPassword(employee.getPassword());
        newEmployee.setEmail(employee.getEmail());
        return modelMapper.map(employeeRepository.save(newEmployee), EmployeeDTO.class);
    }

    public boolean delete(int id) throws EntityNotFoundException {
        Employee employee = employeeRepository.findById(id).orElse(null);
        if (employee == null)
            throw new EntityNotFoundException("Employee not found");
        employeeRepository.delete(employee);
        return true;
    }

    public EmployeeDTO changeStatus(int id, int status) throws EntityNotFoundException {
        Employee employee = employeeRepository.findById(id).orElse(null);
        if (employee == null)
            throw new EntityNotFoundException("Employee not found");
        employee.setStatus(status);
        return modelMapper.map(employeeRepository.save(employee), EmployeeDTO.class);
    }
}
