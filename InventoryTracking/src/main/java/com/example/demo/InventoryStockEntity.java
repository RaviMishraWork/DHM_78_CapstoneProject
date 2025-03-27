package com.example.demo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "inventory")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@ToString
public class InventoryStockEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int id;
	@Column(nullable= false, unique = true)
	int sku;
	@Column(nullable=false)
	int quantity;

	public InventoryStockEntity(InventoryStockDao dao)
	{
		this.sku = dao.getSku();
		this.quantity = dao.getQuantity();
	}
	
}
