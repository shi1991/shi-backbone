package com.puyuntech.ycmall.dao.impl;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import com.puyuntech.ycmall.Page;
import com.puyuntech.ycmall.Pageable;
import com.puyuntech.ycmall.dao.ReturnOrderDao;
import com.puyuntech.ycmall.entity.Organization;
import com.puyuntech.ycmall.entity.ReturnOrder;

/**
 * 
 * DaoImpl - 退单. 
 * Created on 2015-10-14 下午1:06:38 
 * @author 王凯斌
 */
@Repository("returnOrderDaoImpl")
public class ReturnOrderDaoImpl extends BaseDaoImpl<ReturnOrder, Long> implements ReturnOrderDao{

	@Override
	public Page<ReturnOrder> findByOrg(Organization organization,Pageable pageable) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<ReturnOrder> criteriaQuery = criteriaBuilder.createQuery(ReturnOrder.class);
		Root<ReturnOrder> root = criteriaQuery.from(ReturnOrder.class);
		criteriaQuery.select(root);
		Predicate conditions = criteriaBuilder.conjunction();
		//加入member条件 
		if( null != organization ){
			conditions = criteriaBuilder.and(conditions , criteriaBuilder.equal(root.get("organization"), organization) );
		}
		criteriaQuery.where(conditions);
		
		return super.findPage(criteriaQuery, pageable);
	}
	
}
