package asia.ncc.application.entity;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
public class Evaluation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "evaluation_id")
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "evaluator_id")
    private Employee evaluator;
    @ManyToOne
    @JoinColumn(name = "evaluatee_id")
    private Employee evaluatee;
    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;
    private String comment;
    private LocalDateTime timestamp;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "evaluation", cascade = CascadeType.PERSIST)
    private List<EvaluationScore> scoreList;
}
