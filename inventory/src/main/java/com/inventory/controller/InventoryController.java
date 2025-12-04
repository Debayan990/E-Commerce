package com.inventory.controller;

import com.inventory.dtos.InventoryInputDTO;
import com.inventory.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/inventory")
public class InventoryController {

    private final InventoryService inventoryService;
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    ResponseEntity<Integer> getById(@PathVariable Long id){
        return ResponseEntity.status(200).body(inventoryService.getInventory(id));
    }
    @PreAuthorize("hasRole('SELLER')")
    @PostMapping("/change")
    ResponseEntity<Void> changeInventory(@RequestBody InventoryInputDTO dto){
        inventoryService.changeInventory(dto);
        return ResponseEntity.noContent().build();
    }
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/orderAffect")
    ResponseEntity<Void> orderAffect(@RequestBody InventoryInputDTO dto){
        inventoryService.orderAffect(dto);
        return ResponseEntity.noContent().build();
    }

}
