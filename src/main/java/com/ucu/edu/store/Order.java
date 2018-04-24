package com.ucu.edu.store;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import com.ucu.edu.store.Item;

@Data
@RequiredArgsConstructor(onConstructor_ = {@PersistenceConstructor})
@Document
public class Order {
	
	private final String id;
	private final String customerId;
	private final Date orderDate;
	private final List<Item> items;

	/**
	 * Creates a new {@link Order} for the given customer id and order date.
	 *
	 * @param customerId
	 * @param orderDate
	 */
	public Order(String customerId, Date orderDate) {
		this(null, customerId, orderDate, new ArrayList<Item>());
	}



	/**
	 * Adds a {@link Item} to the {@link Order}.
	 *
	 * @param item
	 * @return
	 */
	public Order addItem(Item item) {

		this.items.add(item);
		return this;
	}
}
