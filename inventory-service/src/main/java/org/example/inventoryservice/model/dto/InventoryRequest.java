package org.example.inventoryservice.model.dto;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class InventoryRequest {

    private String code;
    private Integer quantity;
}
