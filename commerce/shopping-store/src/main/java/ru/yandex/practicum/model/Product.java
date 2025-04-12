package ru.yandex.practicum.model;

import jakarta.persistence.*;
import lombok.*;
import ru.yandex.practicum.dto.enums.ProductCategory;
import ru.yandex.practicum.dto.enums.ProductState;
import ru.yandex.practicum.dto.enums.QuantityState;


import java.util.UUID;

@Entity
@Table(name = "products")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Product {
    @Id
    @Column(name = "product_id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID productId;
    @Column(name = "product_name")
    private String productName;
    @Column(name = "image_src")
    private String imageSrc;
    @Column(name = "quantity_state")
    @Enumerated(EnumType.STRING)
    private QuantityState quantityState;
    @Column(name = "product_state")
    @Enumerated(EnumType.STRING)
    private ProductState productState;
    @Column(name = "product_category")
    @Enumerated(EnumType.STRING)
    private ProductCategory productCategory;
    private Double price;
}
