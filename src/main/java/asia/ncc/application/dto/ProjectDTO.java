package asia.ncc.application.dto;

import lombok.Data;

@Data
public class ProjectDTO {
    private String code;
    private String name;
    private String description;
    private int status;
}
