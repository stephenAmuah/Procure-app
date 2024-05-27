package stephenaamuah.prmnt_application.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import stephenaamuah.prmnt_application.model.Item;
import stephenaamuah.prmnt_application.service.ItemService;

@Controller
@RequestMapping("/procureapp")
public class AdminController {

    @Autowired
    private ItemService itemService;

    @GetMapping("/dashboard")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String getAdminPage(Model model) {
        model.addAttribute("items", itemService.getAllItems());
        model.addAttribute("item", new Item());

        return "dashboard";
    }


    @GetMapping("/items/add")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SUPER_ADMIN')")
    public String addItem(Model model, Item item) {
        model.addAttribute("item", item);
        return "dashboard";
    }

    @PostMapping("/items/add")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SUPER_ADMIN')")
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
        itemService.saveItem(existingItem);
        return "redirect:/procureapp/dashboard";
    }

    @PostMapping("/items/delete")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SUPER_ADMIN')")
    public String deleteItem(@RequestParam("id") Long id) {
        itemService.deleteItem(id);
        return "redirect:/procureapp/dashboard";
    }
}
