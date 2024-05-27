package stephenaamuah.prmnt_application.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import stephenaamuah.prmnt_application.model.Item;
import stephenaamuah.prmnt_application.service.ItemService;

import java.util.List;

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
    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN') or hasAuthority('SUPER_ADMIN')")
    public String viewItems(Model model) {
        List<Item> items = itemService.getAllItems();
        log.info("Retrieved items: {}", items);
        model.addAttribute("items", items);
        return "home";
    }



    @GetMapping("/logout")
    public String logout() {
        return "redirect:/procureapp/login";
    }
}
