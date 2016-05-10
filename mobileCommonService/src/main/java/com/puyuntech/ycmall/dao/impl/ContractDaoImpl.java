package com.puyuntech.ycmall.dao.impl;

import java.util.List;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.puyuntech.ycmall.dao.ContractDao;
import com.puyuntech.ycmall.entity.Contract;
import com.puyuntech.ycmall.entity.Operator;

@Repository("contractDaoImpl")
public class ContractDaoImpl extends BaseDaoImpl<Contract, Long> implements ContractDao {

	@Override
	public List<Contract> findByOperator(Operator operator) {
		
		String jpql = "select contract from Contract contract where contract.operator=:operator";
		TypedQuery<Contract> query = entityManager.createQuery(jpql , Contract.class ).setParameter("operator", operator);
		List<Contract> list = query.getResultList();
		return list;
		
	}

	
	
	
}
