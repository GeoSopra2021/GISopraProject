package org.geosopra;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class Html_controller {

    @RequestMapping("/")
    public String welcome(Model model) {
        
        model.addAttribute("Distanzdurchschnitt", 4.2);
        model.addAttribute("Zeitdurchschnitt", 3);
        model.addAttribute("Geschwindigkeitdurchschnitt", 3);
        model.addAttribute("Kosten_Tretty", 3);
        model.addAttribute("Kosten_Lime", 3);
        model.addAttribute("Kosten_Tier", 3);
        model.addAttribute("CO2_Car", 3);
        model.addAttribute("CO2_Scooter", 3);
        model.addAttribute("CO2_Bicycle", 3);
        model.addAttribute("Ersparnis_Car", 3);
        model.addAttribute("Ersparnis_Scooter", 3);
        model.addAttribute("Distanz_min", 3);
        model.addAttribute("Distanz_max", 3);
        model.addAttribute("Zeit_min", 3);
        model.addAttribute("Zeit_max", 3);
        model.addAttribute("Geschwindigkeit_min", 3);
        model.addAttribute("Geschwindigkeit_max", 3);


        return "dashboard";
    }
}
