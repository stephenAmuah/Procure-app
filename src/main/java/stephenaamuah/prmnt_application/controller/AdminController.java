package stephenaamuah.prmnt_application.controller;

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
import stephenaamuah.prmnt_application.service.ItemService;
import stephenaamuah.prmnt_application.service.UserService;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;


@Controller
@RequestMapping("/procureapp")
public class AdminController {

    @Autowired
    private ItemService itemService;

    @Autowired
    private UserService userService;


    @PostMapping("/add-user")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SUPER')")
    public String addUser(@ModelAttribute("user") User user, Authentication authentication) {
        return userService.addUser(user, authentication);
    }


    @GetMapping("/dashboard")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SUPER')")
    public String getAdminDashboard(Model model, Authentication authentication) {

        model.addAttribute("loggedInUserRole", Objects.requireNonNull(authentication.getAuthorities().stream().findFirst().orElse(null)).getAuthority());
        model.addAttribute("items", itemService.getAllItems());
        model.addAttribute("item", new Item());
        model.addAttribute("user", new User());
        return "dashboard";
    }

    @GetMapping("/dashboard/purchased/{startDate}/{endDate}")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SUPER')")
    public String getFilteredItemsForMaintenanceDate(@PathVariable LocalDate startDate, @PathVariable LocalDate endDate, Model model, Authentication authentication) {
        model.addAttribute("loggedInUserRole", Objects.requireNonNull(authentication.getAuthorities().stream().findFirst().orElse(null)).getAuthority());
        model.addAttribute("items", itemService.getItemsByDatePurchased(startDate, endDate));
        model.addAttribute("item", new Item());
        model.addAttribute("user", new User());
        return "dashboard";
    }

    @GetMapping("/dashboard/maintenance-date/{startDate}/{endDate}")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SUPER')")
    public String getFilteredItemsForPurchasedDate(@PathVariable LocalDate startDate, @PathVariable LocalDate endDate, Model model, Authentication authentication) {
        model.addAttribute("loggedInUserRole", Objects.requireNonNull(authentication.getAuthorities().stream().findFirst().orElse(null)).getAuthority());
        model.addAttribute("items", itemService.getItemsByMaintenanceDate(startDate, endDate));
        model.addAttribute("item", new Item());
        model.addAttribute("user", new User());
        return "dashboard";
    }

    @GetMapping("/dashboard/status/assigned")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SUPER')")
    public String getFilteredItemsByAssigned(Model model, Authentication authentication) {
        model.addAttribute("loggedInUserRole", Objects.requireNonNull(authentication.getAuthorities().stream().findFirst().orElse(null)).getAuthority());
        model.addAttribute("items", itemService.getItemsByStatus(AssetStatus.Assigned));
        model.addAttribute("item", new Item());
        model.addAttribute("user", new User());
        return "dashboard";
    }

    @GetMapping("/dashboard/status/unassigned")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SUPER')")
    public String getFilteredItemsByUnassigned(Model model, Authentication authentication) {
        model.addAttribute("loggedInUserRole", Objects.requireNonNull(authentication.getAuthorities().stream().findFirst().orElse(null)).getAuthority());
        model.addAttribute("items", itemService.getItemsByStatus(AssetStatus.Unassigned));
        model.addAttribute("item", new Item());
        model.addAttribute("user", new User());
        return "dashboard";
    }

    @GetMapping("/dashboard/status/maintenance")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SUPER')")
    public String getFilteredItemsByMaintenace(Model model, Authentication authentication) {
        model.addAttribute("loggedInUserRole", Objects.requireNonNull(authentication.getAuthorities().stream().findFirst().orElse(null)).getAuthority());
        model.addAttribute("items", itemService.getItemsByStatus(AssetStatus.InMaintenance));
        model.addAttribute("item", new Item());
        model.addAttribute("user", new User());
        return "dashboard";
    }

    @GetMapping("/dashboard/status/end-of-life")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SUPER')")
    public String getFilteredItemsByEndOfLife(Model model, Authentication authentication) {
        model.addAttribute("loggedInUserRole", Objects.requireNonNull(authentication.getAuthorities().stream().findFirst().orElse(null)).getAuthority());
        model.addAttribute("items", itemService.getItemsByStatus(AssetStatus.EndOfLife));
        model.addAttribute("item", new Item());
        model.addAttribute("user", new User());
        return "dashboard";
    }


    @GetMapping("/items/add")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SUPER')")
    public String getAddItem(Model model, Item item) {
        model.addAttribute("item", item);
        return "dashboard";
    }


    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SUPER')")
    @PostMapping("/items/add")
    public String addItem(@ModelAttribute("item") Item item, Authentication authentication) {
        itemService.saveItem(item, authentication);
        return "redirect:/procureapp/dashboard";
    }

    @PostMapping("/items/update")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SUPER')")
    public String updateItem(@RequestParam("id") Long id, @ModelAttribute("item") Item item, BindingResult result, Authentication authentication) {
        if (result.hasErrors()) {
            return "dashboard";
        }
        Item existingItem = itemService.getItemById(id);
        if (existingItem == null) {
            return "redirect:/procureapp/items";
        }
        itemService.updateItem(existingItem, item, authentication);

        return "redirect:/procureapp/dashboard";
    }

    @PostMapping("/items/delete")
    @PreAuthorize("hasAuthority('SUPER')")
    public String deleteItem(@RequestParam("id") Long id, Authentication authentication) {
        itemService.deleteItem(id, authentication);
        return "redirect:/procureapp/dashboard";
    }
}
