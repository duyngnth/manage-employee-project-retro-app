package asia.ncc.application.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity(name = "eval_score")
@Data
@NoArgsConstructor
@AllArgsConstructor
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
    @JsonIgnore
    private Evaluation evaluation;
    private int score;

    public EvaluationScore(ScoreCriteria scoreCriteria, Evaluation evaluation, int score) {
        this.scoreCriteria = scoreCriteria;
        this.evaluation = evaluation;
        this.score = score;
    }
}
