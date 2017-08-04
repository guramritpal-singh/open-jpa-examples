package com.example;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "CUSTOMERS")
@Setter
@Getter
public class Customer {
	@Id
	@Column(name = "ID")
	private Integer customerId;
	@Column(name = "NAME")
	private String name;
	@Column(name = "COID", insertable = false, updatable = false)
	private Integer coID;

	@OneToMany(mappedBy = "customer")
	private List<Service> services;
}
