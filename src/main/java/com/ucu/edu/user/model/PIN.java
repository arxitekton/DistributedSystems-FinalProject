package com.ucu.edu.user.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ucu.edu.common.model.BaseEntity;

@Entity
@Table(name = "pin")
public class PIN extends BaseEntity<Long> {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@JsonIgnore
	private Long pinId;
	
	private String pin;
	
	@OneToOne
    @JoinColumn(name = "user_id")
	private User user;

	@Override
	public Long getId() {
		return pinId;
	}

	public Long getPinId() {
		return pinId;
	}

	public void setPinId(Long pinId) {
		this.pinId = pinId;
	}

	public String getPin() {
		return pin;
	}

	public void setPin(String pin) {
		this.pin = pin;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public PIN() {

	}

	public static Builder getBuilder() {
		return new Builder();
	}

	public static class Builder {

		private PIN pinWrapper;

		public Builder() {
			pinWrapper = new PIN();
		}

		public Builder pin(String pin) {
			pinWrapper.pin = pin;
			return this;
		}

		public Builder user(User user) {
			pinWrapper.user = user;
			return this;
		}

		public PIN build() {
			return pinWrapper;
		}
	}

}
