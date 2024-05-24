package stephenaamuah.prmnt_application.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import stephenaamuah.prmnt_application.model.Item;
import stephenaamuah.prmnt_application.model.User;
import stephenaamuah.prmnt_application.repository.ItemService;
import stephenaamuah.prmnt_application.service.UserService;

@Controller
@RequestMapping("/procureapp")
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
    public ModelAndView viewItems() {
        return new ModelAndView("home", "items", itemService.getAllItems());
    }

    @GetMapping("/items/add")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SUPER_ADMIN')")
    public ModelAndView getAddItemForm() {
        return new ModelAndView("dashboard", "item", new Item());
    }

    @PostMapping("/items/add")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SUPER_ADMIN')")
    public String addItem(@ModelAttribute("item") Item item) {
        itemService.saveItem(item);
        return "redirect:/procureapp/items";
    }

    @GetMapping("/items/update/{id}")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SUPER_ADMIN')")
    public ModelAndView getEditItemForm(@PathVariable Long id) {
        return new ModelAndView("dashboard", "item", itemService.getItemById(id));
    }




    @GetMapping("/logout")
    public String logout() {
        return "redirect:/procureapp/login";
    }
}
