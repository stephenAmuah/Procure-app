package stephenaamuah.prmnt_application.service;


import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import stephenaamuah.prmnt_application.model.Item;
import stephenaamuah.prmnt_application.repository.ItemRepository;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

    @Transactional
    public void saveItem(Item item) {
        itemRepository.save(item);
    }


    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }


    public Item getItemById(Long id) {
        return itemRepository.findById(id).orElse(null);
    }

    public void updateItem(Item item) {
        Optional<Item> optionalExistingItem = itemRepository.findById(item.getId());

        if (optionalExistingItem.isPresent()) {
            Item existingItem = optionalExistingItem.get();

            existingItem.setName(item.getName());
            existingItem.setDescription(item.getDescription());
            existingItem.setQuantity(item.getQuantity());
            itemRepository.save(existingItem);
        } else {
            log.info("Item with ID {} not found", item.getId());
        }
    }


    public void deleteItem(Long id) {
        itemRepository.deleteById(id);
    }


}
