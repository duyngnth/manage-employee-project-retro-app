package asia.ncc.application.dto;

import lombok.Data;

@Data
public class RequestEvaluationScoreDTO {
    private Integer id;
    private int scoreCriteriaId;
    private int score;
}