package nichy.demo.Controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping({"/", "/home"})
    public String home(Authentication authentication, Model model) {
        boolean isAuthenticated = (authentication != null && authentication.isAuthenticated());
        model.addAttribute("isAuthenticated", isAuthenticated);

        if (isAuthenticated) {
            model.addAttribute("username", authentication.getName());
        }

        return "home"; // Maps to home.html template
    }
}