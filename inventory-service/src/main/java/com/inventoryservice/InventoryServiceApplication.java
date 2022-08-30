package com.inventoryservice;

import com.inventoryservice.model.Inventory;
import com.inventoryservice.repository.InventoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
@Slf4j
@SpringBootApplication
public class InventoryServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(InventoryServiceApplication.class, args);
    }

    @Bean
    public CommandLineRunner loadData(InventoryRepository inventoryRepository) {
        return args -> {
            Inventory inventory = new Inventory();
            Inventory samsung_s20_ultra = Inventory.builder().skuCode("samsung_s20_ultra")
                    .quantity(10).build();
            Inventory samsung_s22_fe = Inventory.builder().skuCode("samsung_s22_FE")
                    .quantity(0).build();
            inventoryRepository.save(samsung_s20_ultra);
            inventoryRepository.save(samsung_s22_fe);
            log.info("DB records have been inserted. ");
        };
    }

}
