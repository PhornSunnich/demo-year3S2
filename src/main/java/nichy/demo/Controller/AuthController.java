package nichy.demo.Controller;

import nichy.demo.Entity.User;
import nichy.demo.Service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String processRegistration(@ModelAttribute("user") User user, Model model) {
        boolean success = userService.registerUser(user);
        if (!success) {
            model.addAttribute("error", "Email នេះត្រូវបានចុះឈ្មោះរួចហើយ!");
            return "register";
        }
        return "redirect:/login?registered";
    }
}