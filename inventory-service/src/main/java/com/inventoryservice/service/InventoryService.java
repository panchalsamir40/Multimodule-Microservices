package com.inventoryservice.service;

import com.inventoryservice.dto.InventoryResponse;
import com.inventoryservice.model.Inventory;
import com.inventoryservice.repository.InventoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class InventoryService {
    private final InventoryRepository inventoryRepository;

    @Autowired
    public InventoryService(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    @Transactional(readOnly = true)
    public List<InventoryResponse> isInStock(List<String> skuCode) throws InterruptedException {
        //Comment this when in production
        testTimeOut();

        List<InventoryResponse> inventoryResponses = inventoryRepository.findBySkuCodeIn(skuCode).stream().map(inventory ->
            InventoryResponse.builder().skuCode(inventory.getSkuCode()).isInStock(inventory.getQuantity() > 0).build()).toList();

        return inventoryResponses;
    }

    private static void testTimeOut() throws InterruptedException {
        log.info("Wait Started");
        Thread.sleep(10000);
        log.info("Wait Ended");
    }
}
