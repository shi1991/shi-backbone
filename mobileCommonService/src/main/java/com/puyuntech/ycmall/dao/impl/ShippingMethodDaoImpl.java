package com.puyuntech.ycmall.dao.impl;

import javax.persistence.NoResultException;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.puyuntech.ycmall.dao.ShippingMethodDao;
import com.puyuntech.ycmall.entity.ShippingMethod;

/**
 * 
 * Dao 配送方式 . 
 * Created on 2015-9-13 下午5:21:40 
 * @author 施长成
 */
@Repository("shippingMethodDaoImpl")
public class ShippingMethodDaoImpl extends BaseDaoImpl<ShippingMethod, Long> implements ShippingMethodDao {

	@Override
	public ShippingMethod findByName(String name) {
		if (StringUtils.isEmpty(name)) {
			return null;
		}
		try {
			String jpql = "select shippingMethods from ShippingMethod shippingMethods where lower(shippingMethods.name) = lower(:name)";
			return entityManager.createQuery(jpql, ShippingMethod.class).setParameter("name", name).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

}
