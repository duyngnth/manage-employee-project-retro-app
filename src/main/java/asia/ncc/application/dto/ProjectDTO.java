package asia.ncc.application.dto;

import lombok.Data;

@Data
public class ProjectDTO {
    private int id;
    private String name;
    private String description;
    private int status;
}
