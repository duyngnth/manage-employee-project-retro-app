package asia.ncc.application.repository;

import asia.ncc.application.entity.Assignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssignmentRepository extends JpaRepository<Assignment, Integer> {
    public boolean existsByEmployeeIdAndProjectId(int employeeId, int projectId);
    public List<Assignment> findAllByProjectId(int projectId);
    public Assignment findByEmployeeIdAndProjectId(int employeeId, int projectId);
    public Assignment findByProjectIdAndProjectRoleId(int projectId, int projectRoleId);
}
