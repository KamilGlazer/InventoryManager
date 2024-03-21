package org.example.inventoryservice.rest;


import lombok.RequiredArgsConstructor;
import org.example.inventoryservice.service.InventoryService;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    @GetMapping("/inventory")
    public boolean checkItemQuantity(@RequestParam List<String> codes,
                                     @RequestParam List<String> quantities){
        return inventoryService.checkItemQuantity(createInventoryRequest(codes,quantities));
    }

    @PutMapping("/inventory")
    public void updateInventory(@RequestParam List<String> codes,
                                     @RequestParam List<String> quantities){
        inventoryService.updateInventory(createInventoryRequest(codes,quantities));
    }


    private Map<String,String> createInventoryRequest(List<String> codes,List<String> quantities){
        Map<String, String> map = new HashMap<>();
        for(int i = 0; i < codes.size(); i++) {
            map.put(codes.get(i), quantities.get(i));
        }
        return map;
    }
}
