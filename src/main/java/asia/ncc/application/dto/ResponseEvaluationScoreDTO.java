package asia.ncc.application.dto;

import lombok.Data;

@Data
public class ResponseEvaluationScoreDTO {
    private int id;
    private String name;
    private int maxScore;
    private int weight;
    private int score;
}
