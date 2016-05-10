package com.puyuntech.ycmall.dao.impl;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.puyuntech.ycmall.dao.ShippingDao;
import com.puyuntech.ycmall.entity.Order;
import com.puyuntech.ycmall.entity.Shipping;

/**
 * 
 * 发货单 Dao . 
 * Created on 2015-9-13 下午5:02:47 
 * @author 施长成
 */
@Repository("shippingDaoImpl")
public class ShippingDaoImpl extends BaseDaoImpl<Shipping, Long> implements ShippingDao {

	@Override
	public Shipping findBySn(String sn) {
		if (StringUtils.isEmpty(sn)) {
			return null;
		}
		String jpql = "select shipping from Shipping shipping where lower(shipping.sn) = lower(:sn)";
		try {
			return entityManager.createQuery(jpql, Shipping.class).setParameter("sn", sn).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public List<Shipping> findByOrder(Order order) {
		/**
		 * 定义jpql语句并且创建查询
		 */
		String jpql = "select shipping from Shipping shipping where shipping.order=:order order by shipping.createDate desc";
		
		TypedQuery<Shipping> query = entityManager.createQuery(jpql, Shipping.class).setParameter("order", order);
		return query.getResultList();
	}
	
}
