package ru.yandex.practicum.model;

import jakarta.persistence.*;
import lombok.*;
import ru.yandex.practicum.dto.enums.DeliveryState;

import java.util.UUID;

@Entity
@Table(name = "delivery")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Delivery {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "delivery_id")
    private UUID deliveryId;
    @Column(name = "order_id")
    private UUID orderId;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "from_address_id")
    private Address fromAddress;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "to_address_id")
    private Address toAddress;
    @Column(name = "state")
    private DeliveryState deliveryState;
    @Column(name = "delivery_weight")
    private Double deliveryWeight;
    @Column(name = "delivery_volume")
    private Double deliveryVolume;
    @Column(name = "fragile")
    private Boolean fragile;
}
