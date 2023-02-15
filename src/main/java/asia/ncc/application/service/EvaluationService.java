package asia.ncc.application.service;

import asia.ncc.application.dto.RequestEvaluationDTO;
import asia.ncc.application.dto.ResponseEvaluationDTO;
import asia.ncc.application.dto.ResponseEvaluationScoreDTO;
import asia.ncc.application.entity.*;
import asia.ncc.application.exception.EntityNotFoundException;
import asia.ncc.application.exception.EvaluationException;
import asia.ncc.application.exception.EvaluationScoreException;
import asia.ncc.application.repository.*;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EvaluationService {
    @Autowired
    private EvaluationRepository evaluationRepository;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private AssignmentRepository assignmentRepository;
    @Autowired
    private ScoreCriteriaRepository scoreCriteriaRepository;
    private ModelMapper modelMapper;

    @PostConstruct
    public void configModelMapper() {
        modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setAmbiguityIgnored(true);

        // Custom TypeMap
        TypeMap<EvaluationScore, ResponseEvaluationScoreDTO> typeMapScore
                = modelMapper.createTypeMap(EvaluationScore.class, ResponseEvaluationScoreDTO.class);
        typeMapScore.addMappings(mapper -> {
            mapper.using(ctx -> {
                ScoreCriteria criteria = (ScoreCriteria) ctx.getSource();
                return criteria.getName();
            }).map(EvaluationScore::getScoreCriteria, ResponseEvaluationScoreDTO::setName);

            mapper.using(ctx -> {
                ScoreCriteria criteria = (ScoreCriteria) ctx.getSource();
                return criteria.getMaxScore();
            }).map(EvaluationScore::getScoreCriteria, ResponseEvaluationScoreDTO::setMaxScore);

            mapper.using(ctx -> {
                ScoreCriteria criteria = (ScoreCriteria) ctx.getSource();
                return criteria.getWeight();
            }).map(EvaluationScore::getScoreCriteria, ResponseEvaluationScoreDTO::setWeight);
        });

        TypeMap<Evaluation, ResponseEvaluationDTO> typeMapEvaluation
                = modelMapper.createTypeMap(Evaluation.class, ResponseEvaluationDTO.class);
        typeMapEvaluation.addMappings(mapper -> {
            mapper.using(ctx -> {
                Employee evaluator = (Employee) ctx.getSource();
                return evaluator.getId();
            }).map(Evaluation::getEvaluator, ResponseEvaluationDTO::setEvaluatorId);

            mapper.using(ctx -> {
                Employee evaluator =  (Employee) ctx.getSource();
                String evaluatorName = evaluator.getLastName() + " " + evaluator.getFirstName();
                return evaluatorName;
            }).map(Evaluation::getEvaluator, ResponseEvaluationDTO::setEvaluatorName);

            mapper.using(ctx -> {
                Employee evaluatee = (Employee) ctx.getSource();
                return evaluatee.getId();
            }).map(Evaluation::getEvaluatee, ResponseEvaluationDTO::setEvaluateeId);

            mapper.using(ctx -> {
                Employee evaluatee =  (Employee) ctx.getSource();
                String evaluateeName = evaluatee.getLastName() + " " + evaluatee.getFirstName();
                return evaluateeName;
            }).map(Evaluation::getEvaluatee, ResponseEvaluationDTO::setEvaluateeName);

            mapper.using(ctx -> {
                Project project = (Project) ctx.getSource();
                return project.getId();
            }).map(Evaluation::getProject, ResponseEvaluationDTO::setProjectId);

            mapper.using(ctx -> {
                Project project = (Project) ctx.getSource();
                return project.getName();
            }).map(Evaluation::getProject, ResponseEvaluationDTO::setProjectName);
        });
    }

    private Evaluation toEntity(RequestEvaluationDTO evaluationDTO) throws EvaluationException {
        Evaluation evaluation = new Evaluation();
        evaluation.setEvaluator(employeeRepository.findById(evaluationDTO.getEvaluatorId()).orElse(null));
        evaluation.setEvaluatee(employeeRepository.findById(evaluationDTO.getEvaluateeId()).orElse(null));
        evaluation.setProject(projectRepository.findById(evaluationDTO.getProjectId()).orElse(null));
        evaluation.setComment(evaluationDTO.getComment());
        evaluation.setTimestamp(LocalDateTime.now());
        List<EvaluationScore> scoreList = evaluationDTO.getScoreList().stream()
                .map(s -> new EvaluationScore(
                        s.getId(),
                        scoreCriteriaRepository.findById(s.getScoreCriteriaId()).get(),
                        evaluation,
                        s.getScore()
                )).collect(Collectors.toList());
        evaluation.setScoreList(scoreList);
        return evaluation;
    }

    private void validateEntity(Evaluation evaluation)
            throws EvaluationException, EvaluationScoreException {
        if (evaluation.getEvaluator() == null)
            throw new EvaluationException("Invalid evaluator");
        if (evaluation.getEvaluatee() == null)
            throw new EvaluationException("Invalid evaluatee");
        if (evaluation.getProject() == null)
            throw new EvaluationException("Invalid project");
        for (EvaluationScore es : evaluation.getScoreList())
            if (es.getScore() > es.getScoreCriteria().getMaxScore())
                throw new EvaluationScoreException("Score cannot larger than max score");
    }

    private Evaluation findById(int id) throws EntityNotFoundException {
        Evaluation evaluation = evaluationRepository.findById(id).orElse(null);
        if (evaluation == null)
            throw new EntityNotFoundException("Evaluation not found");
        else
            return evaluation;
    }

    public ResponseEvaluationDTO get(int id) throws EntityNotFoundException {
        return modelMapper.map(findById(id), ResponseEvaluationDTO.class);
    }

    public List<ResponseEvaluationDTO> filter(Integer evaluatorId, Integer evaluateeId) {
        List<Evaluation> evaluations = evaluationRepository.filter(evaluatorId, evaluateeId);
        return evaluations.stream()
                .map(e -> modelMapper.map(e, ResponseEvaluationDTO.class))
                .collect(Collectors.toList());
    }

    public ResponseEvaluationDTO add(RequestEvaluationDTO evaluationDTO)
            throws EvaluationException, EvaluationScoreException {
        Evaluation evaluation = toEntity(evaluationDTO);
        validateEntity(evaluation);
        return modelMapper.map(evaluationRepository.save(evaluation), ResponseEvaluationDTO.class);
    }

    public ResponseEvaluationDTO update(RequestEvaluationDTO evaluationDTO)
            throws EntityNotFoundException, EvaluationException, EvaluationScoreException {
        if (evaluationDTO.getId() == null)
            throw new EvaluationException("Invalid evaluation id");
        Evaluation evaluation = findById(evaluationDTO.getId());
        Evaluation newEvaluation = toEntity(evaluationDTO);
        // Copy editable data from newEvaluation to evaluation
        evaluation.setComment(newEvaluation.getComment());
        evaluation.setScoreList(newEvaluation.getScoreList());
        validateEntity(evaluation);
        return modelMapper.map(evaluationRepository.save(evaluation), ResponseEvaluationDTO.class);
    }
}
