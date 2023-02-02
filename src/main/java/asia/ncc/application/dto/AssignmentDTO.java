package asia.ncc.application.dto;

import lombok.Data;

@Data
public class AssignmentDTO {
    private Integer id;
    private String employeeUsername;
    private String employeeFullname;
    private String projectCode;
    private String projectName;
    private int projectRoleId;
    private String projectRoleName;
}
