package stephenaamuah.prmnt_application.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import stephenaamuah.prmnt_application.model.Item;
import stephenaamuah.prmnt_application.service.ItemService;

import java.util.List;

@Controller
@RequestMapping("/procureapp")
@Slf4j
public class UserController {

    @Autowired
    private ItemService itemService;

    @GetMapping("/home")
    @PreAuthorize("hasAuthority('USER')")
    public String userHomePage() {
        return "home";
    }

    @GetMapping("/items")
    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN') or hasAuthority('SUPER_ADMIN')")
    public String viewItems(Model model) {
        List<Item> items = itemService.getAllItems();
        log.info("All items {}",itemService.getAllItems());
        model.addAttribute("items", items);
        return "home";
    }

    @GetMapping("/logout")
    public String logout() {
        return "redirect:/procureapp/login";
    }
}
