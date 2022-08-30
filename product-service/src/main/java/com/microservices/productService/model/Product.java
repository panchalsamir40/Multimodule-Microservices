package com.microservices.productService.model;

import lombok.*;
import javax.persistence.*;

import javax.persistence.*;
import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
 //,generator = "product_sequence"   @SequenceGenerator(name = "product_sequence", sequenceName = "product_sequence", schema = "microservices")
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
}
