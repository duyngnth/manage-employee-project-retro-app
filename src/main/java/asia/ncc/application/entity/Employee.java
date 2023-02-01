package asia.ncc.application.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employee_id")
    private int id;
    private String username;
    private String password;
    private String firstname;
    private String lastname;
    private String email;
    private String phone;
    private String branch;
    private int status;
    private String role;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "employee")
    @ToString.Exclude
    private List<ProjectAssignment> assignments;

    public String getFullname() {
        return lastname + " " + firstname;
    }
}
