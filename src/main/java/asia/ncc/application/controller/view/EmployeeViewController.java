package asia.ncc.application.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class EmployeeViewController {
    @RequestMapping("employee/filter")
    public String employeeList(Model model,
                               @RequestParam(value = "inputName", required = false) String inputName,
                               @RequestParam(value = "projectCode", required = false) String projectCode) {
        model.addAttribute("inputName", inputName);
        model.addAttribute("projectCode", projectCode);
        return "employee/list";
    }
}
