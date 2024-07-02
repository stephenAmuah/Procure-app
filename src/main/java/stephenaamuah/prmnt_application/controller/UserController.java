package stephenaamuah.prmnt_application.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import stephenaamuah.prmnt_application.model.AssetStatus;
import stephenaamuah.prmnt_application.model.Item;
import stephenaamuah.prmnt_application.model.User;
import stephenaamuah.prmnt_application.model.UserDetails;
import stephenaamuah.prmnt_application.repository.AccessLogRepository;
import stephenaamuah.prmnt_application.service.ItemService;
import stephenaamuah.prmnt_application.service.UserService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Controller
@Slf4j
@RequestMapping("/procureapp")
public class UserController {

    @Autowired
    ItemService itemService;
    @Autowired
    UserService userService;


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

    @GetMapping("/items/maintenance-date/{startDate}/{endDate}")
    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN') or hasAuthority('SUPER')")
    public String viewItemsMaintenanceDate(@PathVariable LocalDate startDate, @PathVariable LocalDate endDate, Model model, Authentication authentication) {
        List<Item> items = itemService.getItemsByMaintenanceDate(startDate, endDate);
        log.info("All items: {}", items);
        model.addAttribute("loggedInUser", Objects.requireNonNull(authentication.getAuthorities().stream().findFirst().orElse(null)).getAuthority());
        model.addAttribute("items", items);
        return "home";
    }

    @GetMapping("/items/purchased/{startDate}/{endDate}")
    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN') or hasAuthority('SUPER')")
    public String viewItemsPurchased(@PathVariable LocalDate startDate, @PathVariable LocalDate endDate,Model model, Authentication authentication) {
        List<Item> items = itemService.getItemsByDatePurchased(startDate, endDate);
        log.info("All items: {}", items);
        model.addAttribute("loggedInUser", Objects.requireNonNull(authentication.getAuthorities().stream().findFirst().orElse(null)).getAuthority());
        model.addAttribute("items", items);
        return "home";
    }

    @GetMapping("/items/status/assigned")
    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN') or hasAuthority('SUPER')")
    public String viewItemsByAssigned(Model model, Authentication authentication) {
        List<Item> items = itemService.getItemsByStatus(AssetStatus.Assigned);
        log.info("All items: {}", items);
        model.addAttribute("loggedInUser", Objects.requireNonNull(authentication.getAuthorities().stream().findFirst().orElse(null)).getAuthority());
        model.addAttribute("items", items);
        return "home";
    }

    @GetMapping("/items/status/unassigned")
    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN') or hasAuthority('SUPER')")
    public String viewItemsByUnAssigned(Model model, Authentication authentication) {
        List<Item> items = itemService.getItemsByStatus(AssetStatus.Unassigned);
        log.info("All items: {}", items);
        model.addAttribute("loggedInUser", Objects.requireNonNull(authentication.getAuthorities().stream().findFirst().orElse(null)).getAuthority());
        model.addAttribute("items", items);
        return "home";
    }

    @GetMapping("/items/status/maintenance")
    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN') or hasAuthority('SUPER')")
    public String viewItemsByMaintenance(Model model, Authentication authentication) {
        List<Item> items = itemService.getItemsByStatus(AssetStatus.InMaintenance);
        log.info("All items: {}", items);
        model.addAttribute("loggedInUser", Objects.requireNonNull(authentication.getAuthorities().stream().findFirst().orElse(null)).getAuthority());
        model.addAttribute("items", items);
        return "home";
    }

    @GetMapping("/items/status/end-of-life")
    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN') or hasAuthority('SUPER')")
    public String viewItemsByEndOfLife(Model model, Authentication authentication) {
        List<Item> items = itemService.getItemsByStatus(AssetStatus.EndOfLife);
        log.info("All items: {}", items);
        model.addAttribute("loggedInUser", Objects.requireNonNull(authentication.getAuthorities().stream().findFirst().orElse(null)).getAuthority());
        model.addAttribute("items", items);
        return "home";
    }


    @GetMapping("/logout")
    public String logout(Authentication authentication) {
        return "redirect:/procureapp/login";
    }

    @GetMapping("/users")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SUPER')")
    public String getAdminUserDashboard(Model model, Authentication authentication) {

        model.addAttribute("loggedInUserRole", Objects.requireNonNull(authentication.getAuthorities().stream().findFirst().orElse(null)).getAuthority());
        model.addAttribute("users", userService.findAllUsers());
        model.addAttribute("ex_user", new User());
        model.addAttribute("user", new User());
        return "users";
    }

    @PostMapping("/user/update")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SUPER')")
    public String updateUser(@RequestParam("id") int id, @ModelAttribute("user") User user, BindingResult result, Authentication authentication) {
        if (result.hasErrors()) {
            return "users";
        }
        User existingUser = userService.getUserById(id);
        if (existingUser == null) {
            return "redirect:/procureapp/users";
        }
        userService.updateUser(existingUser, user, authentication);

        return "redirect:/procureapp/users";
    }

    @PostMapping("/user/delete")
    @PreAuthorize("hasAuthority('SUPER')")
    public String deleteItem(@RequestParam("id") int id, Authentication authentication) {
        userService.deleteUser(id, authentication);
        return "redirect:/procureapp/users";
    }
}
