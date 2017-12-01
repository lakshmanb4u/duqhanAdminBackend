package com.weavers.duqhan.dao;

import java.util.List;

import com.weavers.duqhan.domain.OrderWorkflow;

public interface OrderWorkflowDao extends BaseDao<OrderWorkflow> {
	
	List<OrderWorkflow> getAllOrderWorkflow();

}
