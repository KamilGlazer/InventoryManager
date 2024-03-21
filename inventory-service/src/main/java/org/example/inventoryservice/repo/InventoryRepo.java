package org.example.inventoryservice.repo;

import org.example.inventoryservice.model.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InventoryRepo extends JpaRepository<Inventory, Long> {

    Optional<Inventory> findInventoryByCode(String code);
}
