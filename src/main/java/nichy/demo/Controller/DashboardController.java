package nichy.demo.Controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    @GetMapping("/dashboard")
    public String dashboard(Authentication authentication, Model model) {
        if (authentication != null && authentication.isAuthenticated()) {
            model.addAttribute("username", authentication.getName());
            model.addAttribute("role", authentication.getAuthorities().iterator().next().getAuthority());
        } else {
            model.addAttribute("username", "Guest User");
            model.addAttribute("role", "ROLE_USER");
        }
        return "dashboard";
    }
}