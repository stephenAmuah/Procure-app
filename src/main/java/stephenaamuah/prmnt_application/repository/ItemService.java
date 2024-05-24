package stephenaamuah.prmnt_application.repository;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import stephenaamuah.prmnt_application.model.Item;
import stephenaamuah.prmnt_application.service.ItemRepository;

import java.util.List;

@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

    public void saveItem(Item item) {
        itemRepository.save(item);
    }

    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }


    public Item getItemById(Long id) {
        return itemRepository.findById(Math.toIntExact(id)).orElse(null);
    }

    public void updateItem(Long id, Item item) {
        Item existingItem = itemRepository.findById(Math.toIntExact(id)).orElse(null);
        if (existingItem != null) {
            existingItem.setName(item.getName());
            existingItem.setDescription(item.getDescription());
            existingItem.setQuantity(item.getQuantity());
            itemRepository.save(existingItem);
        }
    }

    public void deleteItem(Long id) {
        itemRepository.deleteById(Math.toIntExact(id));
    }


}
