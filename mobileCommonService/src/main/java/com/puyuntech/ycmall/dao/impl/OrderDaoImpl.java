package com.puyuntech.ycmall.dao.impl;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.puyuntech.ycmall.dao.OrderDao;
import com.puyuntech.ycmall.entity.Member;
import com.puyuntech.ycmall.entity.Order;

/**
 * 
 * 订单Dao　. 
 * Created on 2015-9-11 下午3:33:57 
 * @author 施长成
 */
@Repository("orderDaoImpl")
public class OrderDaoImpl extends BaseDaoImpl<Order, Long> implements OrderDao {
	public List<Order> findList(Order.Type type, Order.Status status, Member member,Boolean isDelete) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Order> criteriaQuery = criteriaBuilder.createQuery(Order.class);
		Root<Order> root = criteriaQuery.from(Order.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		if (type != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("type"), type));
		}
		if (status != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("status"), status));
		}
		if (member != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("member"), member));
		}
		if (isDelete != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("isDelete"), isDelete));
		}
		criteriaQuery.where(restrictions);
		return super.findList(criteriaQuery, null, null, null, null);
	}
	
	public Order findBySn(String sn) {
		if (StringUtils.isEmpty(sn)) {
			return null;
		}
		String jpql = "select orders from Order orders where lower(orders.sn) = lower(:sn)";
		try {
			return entityManager.createQuery(jpql, Order.class).setParameter("sn", sn).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
}
