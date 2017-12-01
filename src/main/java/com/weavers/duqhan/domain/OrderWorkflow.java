/*
 * Order WorkFlow 
 * Created Date 11/27/2017
 */
package com.weavers.duqhan.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Entity
@Table(name="order_workflow")
public class OrderWorkflow extends BaseDomain {
	
	private static final long serialVersionUID = 1L;
	@Column(name = "module")
	private String module;
	
	@Column(name = "status")
	private String status;
	
	@Column(name = "sequence")
	private Double sequence;
	
	@Column(name = "description")
	@Size(min = 1, max = 255)
	private String description;
	
	@Column(name = "value")
	private String value;

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Double getSequence() {
		return sequence;
	}

	public void setSequence(Double sequence) {
		this.sequence = sequence;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	
	
	
}
