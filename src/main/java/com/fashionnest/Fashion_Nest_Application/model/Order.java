package com.fashionnest.Fashion_Nest_Application.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")  // Renamed from 'order' to 'orders'
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "order_id")
    private String orderId;

    @ManyToOne
    @JoinColumn(name = "user_id")  // Explicitly specify the foreign key column
    private User user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItems = new ArrayList<>();

    private LocalDateTime orderDate;
    private LocalDateTime deliveryDate;

    @OneToOne
    @JoinColumn(name = "shipping_address_id")  // Explicitly specify the foreign key column
    private Address shippingAddress;

    @Embedded
    private PaymentDetails paymentDetails = new PaymentDetails();

    @Column(name = "total_price")
    private double totalPrice;

    @Column(name = "total_discounted_price")
    private Integer totalDiscountedPrice;

    @Column(name = "discount")
    private Integer discount;

    @Column(name = "order_status")
    private String orderStatus;

    @Column(name = "total_item")
    private int totalItem;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
