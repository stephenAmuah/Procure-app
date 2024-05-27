package stephenaamuah.prmnt_application.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import stephenaamuah.prmnt_application.model.User;
import stephenaamuah.prmnt_application.service.UserService;

@Controller
@RequestMapping("/procureapp")
public class WebController {

    @Autowired
    private UserService userService;

    @GetMapping("/success")
    public String defaultAfterLogin(Authentication authentication) {
        if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ADMIN"))) {
            return "redirect:/procureapp/dashboard";
        } else if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("SUPER_ADMIN"))) {
            return "redirect:/procureapp/dashboard";
        } else if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("USER"))) {
            return "redirect:/procureapp/home";
        }
        return "redirect:/procureapp/login?error=true";
    }


    @GetMapping("/login")
    public String getLoginPage() {
        return "login";
    }


    @GetMapping("/signup")
    public String getSignupPage(@ModelAttribute("user") User user) {
        return "sign-up";
    }

    @PostMapping("/signup")
    public String signupUser(@ModelAttribute("user") User user) {
        userService.registerUser(user);
        return "redirect:/procureapp/login";
    }


}
