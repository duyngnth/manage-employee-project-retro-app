package asia.ncc.application.entity;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;

@Entity(name = "eval_score")
@Data
public class EvaluationScore {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "eval_score_id")
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "score_criteria_id")
    private ScoreCriteria scoreCriteria;
    @ManyToOne
    @JoinColumn(name = "evaluation_id")
    @ToString.Exclude
    private Evaluation evaluation;
    private int score;
}
