package asia.ncc.application.entity;

import lombok.Data;

import javax.persistence.*;

@Entity(name = "assignment")
@Data
public class Assignment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "assignment_id")
    private Integer id;
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
