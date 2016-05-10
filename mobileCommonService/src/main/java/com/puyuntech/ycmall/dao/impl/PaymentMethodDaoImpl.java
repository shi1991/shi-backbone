package com.puyuntech.ycmall.dao.impl;

import javax.persistence.NoResultException;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.puyuntech.ycmall.dao.PaymentMethodDao;
import com.puyuntech.ycmall.entity.PaymentMethod;

/**
 * 
 * Dao - 支付方式 . 
 * Created on 2015-9-13 下午5:19:35 
 * @author 施长成
 */
@Repository("paymentMethodDaoImpl")
public class PaymentMethodDaoImpl extends BaseDaoImpl<PaymentMethod, Long> implements PaymentMethodDao {

	@Override
	public PaymentMethod findByName(String name) {
		if (StringUtils.isEmpty(name)) {
			return null;
		}
		try {
			String jpql = "select paymentMethods from PaymentMethod paymentMethods where lower(paymentMethods.name) = lower(:name)";
			return entityManager.createQuery(jpql, PaymentMethod.class).setParameter("name", name).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

}
