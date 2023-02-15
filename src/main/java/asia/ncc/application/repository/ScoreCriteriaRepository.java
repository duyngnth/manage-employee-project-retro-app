package asia.ncc.application.repository;

import asia.ncc.application.entity.ScoreCriteria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScoreCriteriaRepository extends JpaRepository<ScoreCriteria, Integer> {
    public boolean existsById(int id);
}
