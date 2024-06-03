package stephenaamuah.prmnt_application.service;


import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import stephenaamuah.prmnt_application.model.AssetType;
import stephenaamuah.prmnt_application.model.Item;
import stephenaamuah.prmnt_application.repository.ItemRepository;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import static stephenaamuah.prmnt_application.model.AssetType.*;

@Service
@Slf4j
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

    @Transactional
    public void saveItem(Item item) {
        String tag="";
        String num;
        switch (item.getTypeOfAsset()){
            case "CA" -> {
                num = String.valueOf((int) (Math.random() * (200 - 100)) + 100);
                tag = "PA/".concat(CA).concat("/").concat(num);
                break;
            }
            case "FF" -> {
                num = String.valueOf((int) (Math.random() * (300 - 200)) + 200);
                tag = "PA/".concat(FF).concat("/").concat(num);
                break;
            }
            case "TA" -> {
                num = String.valueOf((int) (Math.random() * (400 - 500)) + 400);
                tag = "PA/".concat(TA).concat("/").concat(num);
                break;
            }
            default -> tag = "";
        }
        item.setTag(tag);
        item.setMaintenanceDate(LocalDate.now().plusMonths(6));
        itemRepository.save(item);
    }


    public List<Item> getAllItems() {
        List<Item> items = itemRepository.findAll();
        items.forEach(x->{
            switch (x.getTypeOfAsset()){
                case "CA" -> {
                    x.setTypeOfAsset("Computer Asset");
                    break;
                }
                case "FF" -> {
                    x.setTypeOfAsset("Furniture and Fitting");
                    break;
                }
                case "TA" -> {
                    x.setTypeOfAsset("Transportation Asset");
                    break;
                }
                default -> {
                    break;
                }
            }
        });
        return items;
    }


    public Item getItemById(Long id) {
        return itemRepository.findById(id).orElse(null);
    }

    public void updateItem(Item item) {
        Optional<Item> optionalExistingItem = itemRepository.findById(item.getId());

        if (optionalExistingItem.isPresent()) {
            Item existingItem = optionalExistingItem.get();

            existingItem.setAsset(item.getAsset());
            existingItem.setBrand(item.getBrand());
            existingItem.setSerialNum(item.getSerialNum());
            existingItem.setMaintenanceDate(item.getMaintenanceDate());
            existingItem.setDescription(item.getDescription());
            itemRepository.save(existingItem);
        } else {
            log.info("Item with ID {} not found", item.getId());
        }
    }


    public void deleteItem(Long id) {
        itemRepository.deleteById(id);
    }


}
