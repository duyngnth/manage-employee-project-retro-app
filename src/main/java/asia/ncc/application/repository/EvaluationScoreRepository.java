package asia.ncc.application.repository;

import asia.ncc.application.entity.EvaluationScore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EvaluationScoreRepository extends JpaRepository<EvaluationScore, Integer> {
}
