package com.ucu.edu.common.model;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Version;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ucu.edu.common.converter.ZonedDateTimeAttributeConverter;

@MappedSuperclass
public abstract class BaseEntity<ID> {

	@Column(name = "creation_time", nullable = false)
	@Convert(converter = ZonedDateTimeAttributeConverter.class)
	private ZonedDateTime creationTime;
	
	@JsonIgnore
	@Column(name = "modification_time", nullable = false)
	@Convert(converter = ZonedDateTimeAttributeConverter.class)
	private ZonedDateTime modificationTime;

	@JsonIgnore
	@Version
	private long version;

	public abstract ID getId();

	public ZonedDateTime getCreationTime() {
		return creationTime;
	}

	public ZonedDateTime getModificationTime() {
		return modificationTime;
	}
	
	public long getVersion() {
		return version;
	}

	@PrePersist
	public void prePersist() {
		this.creationTime = ZonedDateTime.now(ZoneOffset.UTC);
		this.modificationTime = ZonedDateTime.now(ZoneOffset.UTC);
	}

	@PreUpdate
	public void preUpdate() {
		this.modificationTime = ZonedDateTime.now(ZoneOffset.UTC);
	}

	public static List<Long> extractIds(Collection<BaseEntity<Long>> entities) {
		return entities.stream().map(BaseEntity::getId).sorted().collect(Collectors.toList());
	}

}
