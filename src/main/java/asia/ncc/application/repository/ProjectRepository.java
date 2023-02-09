package asia.ncc.application.repository;

import asia.ncc.application.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Integer> {
    public boolean existsById(int id);
    public Project findByCode(String code);
    public List<Project> findAll();
}
