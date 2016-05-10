package com.puyuntech.ycmall.dao.impl;

import javax.persistence.NoResultException;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.puyuntech.ycmall.dao.PaymentDao;
import com.puyuntech.ycmall.entity.Payment;

/**
 * 收款单 . 
 * Created on 2015-9-13 下午3:52:04 
 * @author 施长成
 */
@Repository("paymentDaoImpl")
public class PaymentDaoImpl extends BaseDaoImpl<Payment, Long> implements PaymentDao {

	@Override
	public Payment findBySn(String sn) {
		if(StringUtils.isEmpty(sn)){
			return null;
		}
		String jpql = "select payment from Payment where lower(payment.sn) = lower(:sn)";
		try{
			return entityManager.createQuery(jpql,Payment.class).setParameter("sn", sn).getSingleResult();
		}catch (NoResultException e) {
			logger.error("paymentDaoImpl findBysn param sb ="+sn +"result is null");
			return null;
		}
	}

}
