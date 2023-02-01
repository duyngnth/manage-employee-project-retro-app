package asia.ncc.application.repository;

import asia.ncc.application.entity.ProjectAssignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectAssignmentRepository extends JpaRepository<ProjectAssignment, Integer> {
}
