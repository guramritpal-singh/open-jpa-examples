package com.example;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;


@Embeddable
@Getter
@Setter
@EqualsAndHashCode
public class ServicesPK implements Serializable {
	@Column(name = "COID", nullable = false, insertable = false,updatable = false)
	private Integer coId;
	@Column(name = "VERSION", nullable = false, insertable = false,updatable = false)
	private Integer version;
}
