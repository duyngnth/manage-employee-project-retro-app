package asia.ncc.application.dto;

import lombok.Data;

@Data
public class ProjectAssignmentDTO {
    private Integer id;
    private String employeeFullname;
    private String projectName;
    private String projectRoleName;
}
