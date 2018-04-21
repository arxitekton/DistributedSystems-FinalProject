package com.ucu.edu.user.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.FilterDefs;
import org.hibernate.annotations.Filters;
import org.hibernate.annotations.ParamDef;
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.hateoas.Identifiable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ucu.edu.common.model.BaseEntity;
import com.ucu.edu.user.model.State;


@Entity
@Table(name = "user")
@FilterDefs( { @FilterDef( name = "authUserId", parameters = { @ParamDef( name = "authId", type = "long" ), @ParamDef( name = "hasRoleAdmin", type = "Boolean" ) } ) } )
@Filters( { @Filter(name="authUserId", condition = " NOT( NOT(`id` = :authId) AND NOT(:hasRoleAdmin)) ") } )
@JsonIgnoreProperties(ignoreUnknown = true)
public class User extends BaseEntity<Long> implements Serializable, Identifiable<Long> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;

	@NotEmpty
	@Email
	@Column(name = "email", length = 100, nullable = false, unique = true)
	@JsonIgnore
	private String email;

	@Column(name = "first_name", length = 100, nullable = false)
	private String firstname;

	@Column(name = "last_name", length = 100, nullable = false)
	private String lastname;

	@JsonIgnore
	@Column(name = "password", length = 255)
	private String password;

	@JsonIgnore
	@Enumerated(EnumType.STRING)
	@Column(name = "role", length = 20, nullable = false)
	private Role role;
	
	@JsonIgnore
	@Enumerated(EnumType.STRING)
	@Column(name = "sign_in_provider", length = 20)
	private SocialMediaService signInProvider;
	
	@JsonIgnore
	@Column(name = "link", length = 255)
	private String link;

	
	//@JsonIgnore
	//@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy = "user")
	//@OrderBy("creationTime DESC")
    //private Set<Photo> photos = new HashSet<Photo>();
	

	@JsonIgnore
	@Column(name = "mobile_number", length = 10/* , nullable=false */)
	private String mobile_number;

	@Column(name = "phone", length = 12)
	private String phone;

	@JsonIgnore
	@Column(name = "state", nullable = false)
	private String state = State.ACTIVE.getState();

	@Column(nullable = false)
	@Type(type = "org.hibernate.type.NumericBooleanType")
	private Boolean dealer = false;

	@JsonIgnore
	@Column(name = "post_index")
	private Integer post_index;

	@JsonIgnore
	@Column(name = "country")
	private String country;

	@JsonIgnore
	@Column(name = "city")
	private String city;

	@JsonIgnore
	@Column(name = "street")
	private String street;


	@Column(nullable = false)
	@Type(type = "org.hibernate.type.NumericBooleanType")
	private Boolean usePhoneCall = true;

	@Column(nullable = false)
	@Type(type = "org.hibernate.type.NumericBooleanType")
	private Boolean useSMS = true;

	@Column(nullable = false)
	@Type(type = "org.hibernate.type.NumericBooleanType")
	private Boolean contactOnSaturday = true;
	
	@JsonIgnore
	@OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	private PIN pin;
	
	public Long getId() {
		return id;
	}

	public Long getUserId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String first_name) {
		this.firstname = first_name;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String last_name) {
		this.lastname = last_name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public SocialMediaService getSignInProvider() {
		return signInProvider;
	}

	public void setSignInProvider(SocialMediaService signInProvider) {
		this.signInProvider = signInProvider;
	}

	public String getMobile_number() {
		return mobile_number;
	}

	public void setMobile_number(String mobile_number) {
		this.mobile_number = mobile_number;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Boolean isDealer() {
		return dealer;
	}

	public void setDealer(Boolean dealer) {
		this.dealer = dealer;
	}

	public Integer getPost_index() {
		return post_index;
	}

	public void setPost_index(Integer post_index) {
		this.post_index = post_index;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	
	public Boolean isUsePhoneCall() {
		return usePhoneCall;
	}

	public void setUsePhoneCall(Boolean usePhoneCall) {
		this.usePhoneCall = usePhoneCall;
	}
	
	public Boolean isUseSMS() {
		return useSMS;
	}

	public void setUseSMS(Boolean useSMS) {
		this.useSMS = useSMS;
	}	
	
	
	public PIN getPin() {
		return pin;
	}

	public void setPin(PIN pin) {
		this.pin = pin;
	}
	
	/*
	public Set<Photo> getPhotos() {
		return photos;
	}
	
	public void setPhotos(Set<Photo> photos) {
		this.photos = photos;
	}
	*/

	public User() {

	}

	public static Builder getBuilder() {
		return new Builder();
	}

	public static class Builder {

		private User user;

		public Builder() {
			user = new User();
			user.role = Role.ROLE_USER;
		}

		public Builder email(String email) {
			user.email = email;
			return this;
		}

		public Builder phone(String phone) {
			user.phone = phone;
			return this;
		}

		public Builder firstname(String first_name) {
			user.firstname = first_name;
			return this;
		}

		public Builder lastname(String last_name) {
			user.lastname = last_name;
			return this;
		}

		public Builder password(String password) {
			user.password = password;
			return this;
		}

		public Builder signInProvider(SocialMediaService signInProvider) {
			user.signInProvider = signInProvider;
			return this;
		}
		
		public Builder usePhoneCall(Boolean usePhoneCall) {
			user.usePhoneCall = usePhoneCall;
			return this;
		}

		public Builder useSMS(Boolean useSMS) {
			user.useSMS = useSMS;
			return this;
		}

		public User build() {
			return user;
		}
	}
	
/*
	@PreRemove
	public void preRemove() {	
		
		for (Iterator<Photo> i = photos.iterator(); i.hasNext();) {
			Photo element = i.next();
			element.setUser(null);
		}
		
	}
*/
}
