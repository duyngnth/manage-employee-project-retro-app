package asia.ncc.application.controller;

import asia.ncc.application.dto.EmployeeDTO;
import asia.ncc.application.entity.Employee;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DraftController {
    @RequestMapping("/fetchAPI")
    public String fetchAPI() {
        return "fetchAPI";
    }
}
