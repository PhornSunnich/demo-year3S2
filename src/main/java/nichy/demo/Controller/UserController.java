package nichy.demo.Controller;

import nichy.demo.Entity.User;
import nichy.demo.Service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // READ: បង្ហាញបញ្ជី User ទាំងអស់
    @GetMapping
    public String listUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "users/index";
    }

    // CREATE: បង្ហាញ Form បន្ថែម User ថ្មី
    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("user", new User());
        return "users/create";
    }

    // CREATE: ទទួលទិន្នន័យពី Form មក Save
    @PostMapping("/save")
    public String saveUser(@ModelAttribute("user") User user, RedirectAttributes redirectAttributes) {
        boolean created = userService.createUser(user);
        if (!created) {
            redirectAttributes.addFlashAttribute("error", "Email នេះមានរួចហើយ!");
            return "redirect:/users/create"; // បានកែពី /new មក /create
        }
        redirectAttributes.addFlashAttribute("success", "បង្កើតគណនីបានជោគជ័យ!");
        return "redirect:/users";
    }

    // UPDATE: បង្ហាញ Form កែប្រែតាម ID
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        User user = userService.getUserById(id);
        user.setPassword(""); // កុំបង្ហាញ password ចាស់ក្នុង form
        model.addAttribute("user", user);
        return "users/edit";
    }

    // UPDATE: រក្សាទុកការកែប្រែ
    @PostMapping("/update/{id}")
    public String updateUser(
            @PathVariable("id") Long id,
            @ModelAttribute("user") User user,
            RedirectAttributes redirectAttributes) {
        userService.updateUser(id, user);
        redirectAttributes.addFlashAttribute("success", "កែប្រែទិន្នន័យជោគជ័យ!");
        return "redirect:/users";
    }

    // DELETE: លុប User តាម ID
    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        userService.deleteUser(id);
        redirectAttributes.addFlashAttribute("success", "លុបគណនីបានជោគជ័យ!");
        return "redirect:/users";
    }
}