package asia.ncc.application.dto;

import lombok.Data;

@Data
public class RequestAssignmentDTO {
    private Integer id;
    private int employeeId;
    private int projectId;
    private int projectRoleId;
}
