package asia.ncc.application;

import asia.ncc.application.entity.Employee;
import asia.ncc.application.entity.Evaluation;
import asia.ncc.application.repository.*;
import asia.ncc.application.service.EvaluationService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import javax.transaction.Transactional;
import java.util.List;

@SpringBootApplication
@RequiredArgsConstructor
public class App implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    private final AssignmentRepository assignmentRepository;
    private final EmployeeRepository employeeRepository;
    private final ProjectRepository projectRepository;
    private final ProjectRoleRepository projectRoleRepository;
    private final EvaluationRepository evaluationRepository;
    private final EvaluationScoreRepository evaluationScoreRepository;
    private final ScoreCriteriaRepository scoreCriteriaRepository;
    private final ModelMapper modelMapper;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
    }
}
