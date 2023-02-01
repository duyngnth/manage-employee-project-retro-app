package asia.ncc.application.dto;

import lombok.Data;

@Data
public class EmployeeDTO {
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String branch;
    private int status;
    private String role;
}
