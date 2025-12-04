package com.inventory.service;

import com.inventory.dtos.InventoryInputDTO;

public interface InventoryService {
    Integer getInventory(Long id);
    void changeInventory(InventoryInputDTO dto);
    void orderAffect(InventoryInputDTO dto);

}
