package org.example.inventoryservice.service;


import lombok.RequiredArgsConstructor;
import org.example.inventoryservice.model.entity.Inventory;
import org.example.inventoryservice.repo.InventoryRepo;
import org.springframework.stereotype.Service;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class InventoryService {

    private final InventoryRepo inventoryRepo;

    public boolean checkItemQuantity(Map<String, String> request){

        for(String code : request.keySet()) {
            Inventory inventory = inventoryRepo.findInventoryByCode(code).orElseThrow(RuntimeException::new);
            if(inventory.getQuantity() < Integer.parseInt(request.get(code))) {
                return false;
            }
        }
        return true;
    }

    public void updateInventory(Map<String, String> request) {
        for(String code : request.keySet()) {
            Inventory inventory = inventoryRepo.findInventoryByCode(code).orElseThrow(RuntimeException::new);
            inventory.setQuantity(inventory.getQuantity() - Integer.parseInt(request.get(code)));
            inventoryRepo.save(inventory);
        }
    }
}
