package asia.ncc.application.repository;

import asia.ncc.application.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    public Employee findByUsername(String username);
    @Query(value = "SELECT * FROM employee \n" +
            "WHERE ((:input_name IS NULL\n" +
            "OR username LIKE :input_name\n" +
            "OR CONCAT(firstname, ' ', lastname) LIKE :input_name \n" +
            "OR CONCAT(lastname, ' ', firstname) LIKE :input_name))\n" +
            "AND (:project_code IS NULL\n" +
            "OR employee_id IN \n" +
            "(SELECT employee_id FROM project_assignment \n" +
            "WHERE project_id IN (SELECT project_id FROM project \n" +
            "WHERE code = :project_code)))",
            nativeQuery = true)
    public List<Employee> filter(@Param("project_code") String projectCode,
                                 @Param("input_name") String inputName);
}
