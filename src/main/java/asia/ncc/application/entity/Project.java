package asia.ncc.application.entity;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "project_id")
    private Integer id;
    private String code;
    private String name;
    private String description;
    private int status;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "project")
    @ToString.Exclude
    private List<Assignment> assignments;
}
