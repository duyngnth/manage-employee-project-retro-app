package asia.ncc.application.dto;

import lombok.Data;

@Data
public class ResponseAssignmentDTO {
    private Integer id;
    private String employeeUsername;
    private String employeeFirstName;
    private String employeeLastName;
    private String projectCode;
    private String projectName;
    private int projectRoleId;
    private String projectRoleName;
}
