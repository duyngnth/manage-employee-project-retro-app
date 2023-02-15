package asia.ncc.application.dto;

import lombok.Data;

import java.util.List;

@Data
public class RequestEvaluationDTO {
    private Integer id;
    private int evaluatorId;
    private int evaluateeId;
    private int projectId;
    private String comment;
    private List<RequestEvaluationScoreDTO> scoreList;
}
