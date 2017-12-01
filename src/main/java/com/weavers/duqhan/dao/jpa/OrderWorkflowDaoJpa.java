package com.weavers.duqhan.dao.jpa;

import java.util.List;

import javax.persistence.Query;

import com.weavers.duqhan.dao.OrderWorkflowDao;
import com.weavers.duqhan.domain.OrderWorkflow;

public class OrderWorkflowDaoJpa extends BaseDaoJpa<OrderWorkflow> implements OrderWorkflowDao {

	public OrderWorkflowDaoJpa() {
		super(OrderWorkflow.class, "OrderWorkflow");
	}

	@Override
	public List<OrderWorkflow> getAllOrderWorkflow() {
		
		Query query = getEntityManager().createQuery("SELECT o FROM OrderWorkflow o");
		
		return query.getResultList();
	}
}
