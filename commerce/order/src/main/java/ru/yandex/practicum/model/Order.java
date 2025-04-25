package ru.yandex.practicum.model;

import jakarta.persistence.*;
import lombok.*;
import ru.yandex.practicum.dto.enums.OrderState;

import java.util.Map;
import java.util.UUID;

@Entity
@Table(name = "orders")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "order_id")
    private UUID orderId;
    @Column(name = "user_name")
    private String userName;
    @Column(name = "cart_id")
    private UUID cartId;
    @ElementCollection
    @CollectionTable(name = "order_products", joinColumns = @JoinColumn(name = "order_id"))
    @MapKeyColumn(name = "product_id")
    @Column(name = "quantity")
    private Map<UUID, Long> products;
    @Column(name = "payment_id")
    private UUID paymentId;
    @Column(name = "delivery_id")
    private UUID deliveryId;
    @Enumerated(EnumType.STRING)
    @Column(name = "state")
    private OrderState state;
    @Column(name = "delivery_weight")
    private Double deliveryWeight;
    @Column(name = "delivery_volume")
    private Double deliveryVolume;
    @Column(name = "fragile")
    private Boolean fragile;
    @Column(name = "total_price")
    private Double totalPrice;
    @Column(name = "delivery_price")
    private Double deliveryPrice;
    @Column(name = " product_price")
    private Double  productPrice;
}
