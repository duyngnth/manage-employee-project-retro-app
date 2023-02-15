package asia.ncc.application.repository;

import asia.ncc.application.entity.Evaluation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

@Repository
public interface EvaluationRepository extends PagingAndSortingRepository<Evaluation, Integer> {

    @Query(value = "SELECT * FROM evaluation\n" +
            "WHERE (:evaluatorId IS NULL " +
            "OR evaluator_id = :evaluatorId)\n" +
            "AND (:evaluateeId IS NULL " +
            "OR evaluatee_id = :evaluateeId);",
            nativeQuery = true)
    public List<Evaluation> filter(@Param("evaluatorId") Integer evaluatorId,
                                   @Param("evaluateeId") Integer evaluateeId);
}
