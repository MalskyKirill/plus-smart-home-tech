package ru.yandex.practicum.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "warehouse_product")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class WarehouseProduct {
    @Id
    @Column(name = "product_id")
    private UUID productId;
    private boolean fragile;
    private Double width;
    private Double height;
    private Double depth;
    private Double weight;
    private long quantity;
}
