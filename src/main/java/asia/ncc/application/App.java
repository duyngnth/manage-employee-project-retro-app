package asia.ncc.application;

import asia.ncc.application.dto.EmployeeDTO;
import asia.ncc.application.dto.ProjectDTO;
import asia.ncc.application.entity.Employee;
import asia.ncc.application.entity.Project;
import asia.ncc.application.repository.EmployeeRepository;
import asia.ncc.application.repository.ProjectAssignmentRepository;
import asia.ncc.application.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Scanner;

@SpringBootApplication
@RequiredArgsConstructor
public class App implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    private final ProjectAssignmentRepository projectAssignmentRepository;
    private final EmployeeRepository employeeRepository;
    private final ProjectRepository projectRepository;
    private final ModelMapper modelMapper;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
    }
}
