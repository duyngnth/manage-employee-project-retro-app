package asia.ncc.application.controller.api;

import asia.ncc.application.dto.RequestEvaluationDTO;
import asia.ncc.application.dto.ResponseEvaluationDTO;
import asia.ncc.application.entity.Evaluation;
import asia.ncc.application.exception.EntityNotFoundException;
import asia.ncc.application.exception.EvaluationException;
import asia.ncc.application.exception.EvaluationScoreException;
import asia.ncc.application.payload.ApplicationResponse;
import asia.ncc.application.repository.EvaluationRepository;
import asia.ncc.application.service.EvaluationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/evaluations")
public class EvaluationController {
    @Autowired
    private EvaluationService evaluationService;

    @GetMapping("/{id}")
    public ApplicationResponse<ResponseEvaluationDTO> get(@PathVariable int id)
            throws EntityNotFoundException {
        return ApplicationResponse.succeed(evaluationService.get(id));
    }

    @GetMapping
    public ApplicationResponse<List<ResponseEvaluationDTO>> filter(
            @RequestParam(value = "evaluatorId", required = false) Integer evaluatorId,
            @RequestParam(value = "evaluateeId", required = false) Integer evaluateeId,
            @RequestParam(value = "pageIndex", required = false, defaultValue = "0") int pageIndex,
            @RequestParam(value = "pageSize", required = false, defaultValue = "20") int pageSize
    ) {
        Pageable pageable = PageRequest.of(pageIndex, pageSize);
        return ApplicationResponse.succeed(evaluationService.filter(evaluatorId, evaluateeId, pageable));
    }

    @PostMapping
    public ApplicationResponse<ResponseEvaluationDTO> add(
            @RequestBody RequestEvaluationDTO evaluationDTO
    ) throws EvaluationException, EvaluationScoreException {
        return ApplicationResponse.succeed(evaluationService.add(evaluationDTO));
    }

    @PutMapping
    public ApplicationResponse<ResponseEvaluationDTO> update(
            @RequestBody RequestEvaluationDTO evaluationDTO
    ) throws EntityNotFoundException, EvaluationException, EvaluationScoreException {
        return ApplicationResponse.succeed(evaluationService.update(evaluationDTO));
    }
}
