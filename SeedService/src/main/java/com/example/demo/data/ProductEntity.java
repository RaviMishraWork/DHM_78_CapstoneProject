package com.example.demo.data;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name="product")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(unique = true)
	private int sku;
	@Column(nullable = false)
	private String name;
	@Column(nullable = false)
	private String description;
	@Column(nullable = false)
	private int initial_stock;
	@Column(nullable = false)
	private double buyPrice = 0.0;
	@Column(nullable = false)
	private double sellPrice = 0.0;
	@Column
	private int supplierId;
	@Column
	private String imageUrl;
	@Column
	private String category;

}
