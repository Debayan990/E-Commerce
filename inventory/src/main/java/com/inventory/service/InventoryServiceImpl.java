package com.inventory.service;

import com.inventory.dtos.InventoryInputDTO;
import com.inventory.entities.Inventory;
import com.inventory.exception.InsuffientQuantityException;
import com.inventory.exception.UserIdNotFoundException;
import com.inventory.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {

    private final ModelMapper modelMapper;
    private final InventoryRepository inventoryRepository;

    @Override
    public Integer getInventory(Long id) {
       Inventory inventory= inventoryRepository.findById(id).orElseThrow(()->new UserIdNotFoundException("Id is not found"));
       return inventory.getQuantity();

    }

    @Override
    public void changeInventory(InventoryInputDTO dto) {
        Optional<Inventory> optionalInventory=inventoryRepository.findByProductid(dto.getProductid());
        if(optionalInventory.isPresent()){
            Inventory presentInventory=optionalInventory.get();
            presentInventory.setQuantity(presentInventory.getQuantity()+ dto.getQuantity());
            inventoryRepository.save(presentInventory);
        }
        else{
            Inventory presentInventory=modelMapper.map(dto,Inventory.class);
            inventoryRepository.save(presentInventory);
        }

    }

    @Override
    public void orderAffect(InventoryInputDTO dto) {
        Inventory inventory=inventoryRepository.findByProductid(dto.getProductid())
                .orElseThrow(()->new UserIdNotFoundException("User Id Not Found"));
        if(inventory.getQuantity()>=dto.getQuantity()){
            inventory.setQuantity(inventory.getQuantity()- dto.getQuantity());
        }
        else{
            throw new InsuffientQuantityException("Sorry Less Quantity");
        }
        inventoryRepository.save(inventory);
    }
}
