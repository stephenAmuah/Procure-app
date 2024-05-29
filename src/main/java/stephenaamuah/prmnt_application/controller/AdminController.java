package stephenaamuah.prmnt_application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import stephenaamuah.prmnt_application.model.Item;
import stephenaamuah.prmnt_application.model.User;
import stephenaamuah.prmnt_application.service.ItemService;
import stephenaamuah.prmnt_application.service.UserService;

import java.util.Objects;


@Controller
@RequestMapping("/procureapp")
public class AdminController {

    @Autowired
    private ItemService itemService;

    @Autowired
    private UserService userService;

    @GetMapping("/add-user")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String getAddUserPage(@ModelAttribute("user") User user) {
        return "add-user";
    }

    @PostMapping("/add-user")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String addUser(@ModelAttribute("user") User user) {
        userService.addUser(user);
        return "redirect:/procureapp/add-user?success";
    }

    @GetMapping("/dashboard")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SUPER_ADMIN')")
    public String getAdminDashboard(Model model, Authentication authentication) {
        model.addAttribute("items", itemService.getAllItems());
        model.addAttribute("loggedInUserRole", Objects.requireNonNull(authentication.getAuthorities().stream().findFirst().orElse(null)).getAuthority());
        model.addAttribute("item", new Item());
        return "dashboard";
    }


    @GetMapping("/items/add")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SUPER_ADMIN')")
    public String getAddItem(Model model, Item item) {
        model.addAttribute("item", item);
        return "dashboard";
    }


    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SUPER_ADMIN')")
    @PostMapping("/items/add")
    public String addItem(@ModelAttribute("item") Item item) {
        itemService.saveItem(item);
        return "redirect:/procureapp/dashboard";
    }

    @PostMapping("/items/update")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SUPER_ADMIN')")
    public String updateItem(@RequestParam("id") Long id, @ModelAttribute("item") Item item, BindingResult result) {
        if (result.hasErrors()) {
            return "dashboard";
        }
        Item existingItem = itemService.getItemById(id);
        if (existingItem == null) {
            return "redirect:/procureapp/items";
        }

        existingItem.setName(item.getName());
        existingItem.setDescription(item.getDescription());
        existingItem.setQuantity(item.getQuantity());
        itemService.updateItem(existingItem);

        return "redirect:/procureapp/dashboard";
    }

    @PostMapping("/items/delete")
    @PreAuthorize("hasAuthority('SUPER_ADMIN')")
    public String deleteItem(@RequestParam("id") Long id) {
        itemService.deleteItem(id);
        return "redirect:/procureapp/dashboard";
    }
}
