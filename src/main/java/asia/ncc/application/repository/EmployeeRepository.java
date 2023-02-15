package asia.ncc.application.repository;

import asia.ncc.application.entity.Employee;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends PagingAndSortingRepository<Employee, Integer> {
    public Employee findByUsername(String username);
    public Employee findByEmail(String email);
    @Query(value = "SELECT * FROM employee \n" +
            "WHERE ((:inputName IS NULL\n" +
            "OR username LIKE :inputName\n" +
            "OR CONCAT(first_name, ' ', last_name) LIKE :inputName \n" +
            "OR CONCAT(last_name, ' ', first_name) LIKE :inputName))\n" +
            "AND (:projectCode IS NULL\n" +
            "OR employee_id IN \n" +
            "(SELECT employee_id FROM assignment \n" +
            "WHERE project_id IN (SELECT project_id FROM project \n" +
            "WHERE code = :projectCode)))",
            nativeQuery = true)
    public List<Employee> filter(@Param("projectCode") String projectCode,
                                 @Param("inputName") String inputName,
                                 Pageable pageable);
}
