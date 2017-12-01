package com.weavers.duqhan.dto;

import java.util.List;

import com.weavers.duqhan.domain.OrderWorkflow;

public class OrderWorkflowDto {

	//private List<OrderWorkflow> orderWorkflow;
	private Object orderWorkflowList;
	private String status;
    private String StatusCode;
    
	/*public List<OrderWorkflow> getOrderWorkflow() {
		return orderWorkflow;
	}
	public void setOrderWorkflow(List<OrderWorkflow> orderWorkflow) {
		this.orderWorkflow = orderWorkflow;
	}*/
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getStatusCode() {
		return StatusCode;
	}
	public void setStatusCode(String statusCode) {
		StatusCode = statusCode;
	}
	public Object getOrderWorkflowList() {
		return orderWorkflowList;
	}
	public void setOrderWorkflowList(Object orderWorkflowList) {
		this.orderWorkflowList = orderWorkflowList;
	}
	
    
    
}
