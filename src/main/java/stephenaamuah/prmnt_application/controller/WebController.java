package stephenaamuah.prmnt_application.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import stephenaamuah.prmnt_application.model.UserDetails;
import stephenaamuah.prmnt_application.repository.AccessLogRepository;

import java.time.LocalDateTime;
import java.util.Date;


@Controller
@RequestMapping("/procureapp")
public class WebController {
    @Autowired
    AccessLogRepository accessLogRepository;


    @GetMapping("/login")
    public String getLoginPage() {
        return "login";
    }


    @GetMapping("/afterLogin")
    public String defaultAfterLogin(Authentication authentication) {
        if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ADMIN"))) {
            logAccess(authentication);
            return "redirect:/procureapp/dashboard";
        } else if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("SUPER"))) {
            logAccess(authentication);
            return "redirect:/procureapp/dashboard";
        } else if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("USER"))) {
            logAccess(authentication);
            return "redirect:/procureapp/home";
        }
        return "redirect:/procureapp/login?error=true";
    }

    private void logAccess(Authentication authentication) {
        Object principal = authentication.getPrincipal();
        if (principal instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) principal;
            accessLogRepository.insertAccessLog(userDetails.getFirstName(), userDetails.getSurname(), userDetails.getUsername(), userDetails.getRoles().get(0).getAuthority(),"Login", LocalDateTime.now());
        }
    }
}
