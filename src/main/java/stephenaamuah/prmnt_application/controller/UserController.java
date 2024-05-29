package stephenaamuah.prmnt_application.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import stephenaamuah.prmnt_application.model.Item;
import stephenaamuah.prmnt_application.model.User;
import stephenaamuah.prmnt_application.model.UserDetails;
import stephenaamuah.prmnt_application.service.ItemService;

import java.util.List;
import java.util.Objects;

@Controller
@Slf4j
@RequestMapping("/procureapp")
public class UserController {

    private final ItemService itemService;

    @Autowired
    public UserController(ItemService itemService) {
        this.itemService = itemService;
    }


    @GetMapping("/home")
    @PreAuthorize("hasAuthority('USER')")
    public String userHomePage() {
        return "redirect:/procureapp/items";
    }


    @GetMapping("/items")
    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN') or hasAuthority('SUPER')")
    public String viewItems(Model model, Authentication authentication) {
        List<Item> items = itemService.getAllItems();
        log.info("All items: {}", items);
        model.addAttribute("loggedInUser", Objects.requireNonNull(authentication.getAuthorities().stream().findFirst().orElse(null)).getAuthority());
        model.addAttribute("items", items);
        return "home";
    }


    @GetMapping("/logout")
    public String logout() {
        return "redirect:/procureapp/login";
    }
}
