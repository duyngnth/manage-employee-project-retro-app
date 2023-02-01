package asia.ncc.application.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class FetchAPIController {
    @RequestMapping("/fetchAPI")
    public String fetchAPI() {
        return "fetchAPI";
    }
}
