package asia.ncc.application.entity;

import lombok.Data;

import javax.persistence.*;

@Entity(name = "project_assignment")
@Data
public class ProjectAssignment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "project_assignment_id")
    private int id;
    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;
    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;
    @ManyToOne
    @JoinColumn(name = "project_role_id")
    private ProjectRole projectRole;
}
