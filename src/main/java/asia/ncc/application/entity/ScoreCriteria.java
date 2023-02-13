package asia.ncc.application.entity;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Entity(name = "score_criteria")
@Data
public class ScoreCriteria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "score_criteria_id")
    private int id;
    private String name;
    @Column(name = "max_score")
    private int maxScore;
    private int weight;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "scoreCriteria")
    @ToString.Exclude
    List<EvaluationScore> scoreList;
}
