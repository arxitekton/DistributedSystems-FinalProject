package com.ucu.edu.store;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;

@Data
@RequiredArgsConstructor(onConstructor = @__(@PersistenceConstructor))
public class Item {
	
	@Id private String id;
	
	private final String caption;
	private final double price;

	int quantity = 1;

	public Item(String caption, double price, int quantity) {

		this(caption, price);
		this.quantity = quantity;
	}
}
