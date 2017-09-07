package com.xmen.mutants.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Table containing the requesters.
 *
 * @author Caetano Riverón
 */

@Entity
@Table(name = "requests")
public class Request implements java.io.Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "is_mutant")
	private Boolean isMutant;

	public Request() {
	}

	public Request(Boolean isMutant) {
		this.isMutant = isMutant;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Boolean getIsMutant() {
		return this.isMutant;
	}

	public void setIsMutant(Boolean isMutant) {
		this.isMutant = isMutant;
	}

}
