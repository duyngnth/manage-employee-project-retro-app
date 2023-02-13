package asia.ncc.application.dto;

import lombok.Data;

@Data
public class ResponseAssignmentDTO {
    private Integer id;
    private int employeeId;
    private String employeeUsername;
    private String employeeFirstName;
    private String employeeLastName;
    private int projectId;
    private String projectCode;
    private String projectName;
    private int projectRoleId;
    private String projectRoleName;
}
