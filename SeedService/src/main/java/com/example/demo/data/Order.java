package com.example.demo.data;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "Orders")
public class Order {

    @Id
    @GeneratedValue(generator = "uuid2")
    @Column(name = "order_id")
    private UUID orderId;

    @Column(name = "supplier_id")
    private int supplierId;

    @OneToMany(mappedBy="order", cascade = CascadeType.ALL)
    private List<OrderProduct> products = new ArrayList<OrderProduct>();

    @Column(name = "total_price")
    private Double totalPrice;

    @Column(name= "order_created")
    private Date orderCreated;
    @Column(name= "order_updated")
    private Date orderUpdated;
    @Column(name= "order_completed")
    private Date orderCompleted;
    @Column(name= "order_status")
    private OrderStatus orderStatus;

    @PrePersist
    public void prePersist() {
        if (this.orderCreated == null) {
            this.orderCreated = new Date(System.currentTimeMillis());
        }
        if (this.orderUpdated == null) {
            this.orderUpdated = new Date(System.currentTimeMillis());
        }
        if (this.orderStatus == null) {
            this.orderStatus = OrderStatus.PENDING;
        }
    }
    @PreUpdate
    public void preUpdate() {
        this.orderUpdated = new Date(System.currentTimeMillis());
    }


    public void addProduct(OrderProduct product) {
        products.add(product);
        product.setOrder(this);
    }

}
