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
    int id;
    String username;
    String password;
    String firstname;
    String lastname;
    String email;
    String phone;
    String branch;
    int status;
    String role;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "employee")
    @ToString.Exclude
    List<ProjectAssignment> assignments;

    public String getFullname() {
        return lastname + " " + firstname;
    }
}
