package stephenaamuah.prmnt_application.controller;


import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/procureapp")
public class AdminController {


    @GetMapping("/dashboard")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String adminHome() {
        return "dashboard";
    }




}
