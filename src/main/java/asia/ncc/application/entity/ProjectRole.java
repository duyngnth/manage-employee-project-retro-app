package asia.ncc.application.entity;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Entity(name = "project_role")
@Data
public class ProjectRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "project_role_id")
    int id;
    String name;
    String description;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "projectRole")
    @ToString.Exclude
    List<ProjectAssignment> assignments;
}
