package com.product.client;

import com.product.dtos.InventoryInputDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "gateway")
public interface InventoryClient {

    @PostMapping("/api/inventory/change")
    ResponseEntity<Void> change(@RequestBody InventoryInputDTO dto);

}
