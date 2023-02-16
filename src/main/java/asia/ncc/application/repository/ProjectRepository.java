package asia.ncc.application.repository;

import asia.ncc.application.entity.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends PagingAndSortingRepository<Project, Integer> {
    public boolean existsById(int id);
    public Project findByCode(String code);
    public Page<Project> findAll(Pageable pageable);
}
