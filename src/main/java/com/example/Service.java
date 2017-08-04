package com.example;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "SERVICES")
@Getter
@Setter
public class Service {
	@EmbeddedId
	private ServicesPK servicePK;

	@Column(name = "STATUS")
	private String status;

	@Column(name = "CREATED")
	@Temporal(TemporalType.DATE)
	private Date created;

	@ManyToOne(optional = false)
	@JoinColumn(name = "COID",referencedColumnName = "COID")
	private Customer customer;
}
