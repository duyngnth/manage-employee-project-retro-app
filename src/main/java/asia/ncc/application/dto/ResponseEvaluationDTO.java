package asia.ncc.application.dto;

import lombok.Data;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class ResponseEvaluationDTO {
    private int id;
    private int evaluatorId;
    private String evaluatorName;
    private int evaluateeId;
    private String evaluateeName;
    private int projectId;
    private String projectName;
    private String comment;
    private LocalDateTime timestamp;
    private List<ResponseEvaluationScoreDTO> scoreList;
}
