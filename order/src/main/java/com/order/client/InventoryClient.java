package com.order.client;

import com.order.dtos.InventoryInputDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "gateway",contextId = "inventory-client")
public interface InventoryClient {

    @PostMapping("/api/inventory/orderAffect")
    ResponseEntity<Void> orderAffect(@RequestBody InventoryInputDTO dto);


}
