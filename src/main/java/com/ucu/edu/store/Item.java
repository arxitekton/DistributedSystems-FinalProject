package com.ucu.edu.store;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import org.springframework.data.annotation.PersistenceConstructor;

@Data
//@RequiredArgsConstructor(onConstructor_= {@PersistenceConstructor})
//@RequiredArgsConstructor(onConstructor_={@AnnotationsGohere})
public class Item {

	private final String caption;
	private final double price;

	int quantity = 1;

	public Item(String caption, double price) {
		this.caption = caption;
		this.price = price;
	}

	public Item(String caption, double price, int quantity) {

		this(caption, price);
		this.quantity = quantity;
	}
}
