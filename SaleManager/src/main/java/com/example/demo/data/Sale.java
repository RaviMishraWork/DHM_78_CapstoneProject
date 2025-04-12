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

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "sales")
public class Sale {

    @Id
    @GeneratedValue(generator = "uuid2")
    @Column(name = "sale_id")
    private UUID saleId;

//    @Column(name = "supplier_id")
//    private int supplierId;
    @Column(name = "customer_username", nullable = false)
    private String customerUsername;

    @OneToMany(mappedBy="sale", cascade = CascadeType.ALL)
    private List<SaleProduct> products = new ArrayList<SaleProduct>();

    @Column(name = "total_price")
    private Double totalPrice;

    @Column(name= "sale_created")
    private Date saleCreated;
    @Column(name= "sale_updated")
    private Date saleUpdated;
    @Column(name= "sale_completed")
    private Date saleCompleted;
    @Column(name= "sale_status")
    private SaleStatus saleStatus;

    @PrePersist
    public void prePersist() {
        this.saleCreated = new Date(System.currentTimeMillis());
        this.saleUpdated = new Date(System.currentTimeMillis());
        this.saleStatus = SaleStatus.PENDING;
    }
    @PreUpdate
    public void preUpdate() {
        this.saleUpdated = new Date(System.currentTimeMillis());
    }


    public void addProduct(SaleProduct product) {
        products.add(product);
        product.setSale(this);
    }

}
