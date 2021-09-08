package org.geosopra;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class Html_controller {

    @RequestMapping("/")
    public String welcome(Model model) {
        return "dashboard";
    }
}
