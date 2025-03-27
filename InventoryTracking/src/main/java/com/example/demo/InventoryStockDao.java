package com.example.demo;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@ToString
public class InventoryStockDao {
	int sku;
	int quantity;

	public InventoryStockDao(InventoryStockEntity entity)
	{
		this.sku = entity.getSku();
		this.quantity = entity.getQuantity();
	}

}
