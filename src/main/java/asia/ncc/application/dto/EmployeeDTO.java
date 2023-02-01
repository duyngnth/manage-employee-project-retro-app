package asia.ncc.application.dto;

import lombok.Data;

@Data
public class EmployeeDTO {
    String username;
    String firstName;
    String lastName;
    String email;
    String phone;
    String branch;
    int status;
    String role;
}
