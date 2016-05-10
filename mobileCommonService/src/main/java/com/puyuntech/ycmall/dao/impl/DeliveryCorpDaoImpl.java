package com.puyuntech.ycmall.dao.impl;

import java.util.List;

import javax.persistence.NoResultException;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.puyuntech.ycmall.dao.DeliveryCorpDao;
import com.puyuntech.ycmall.entity.DeliveryCorp;

/**
 * 
 * Dao - 物流公司. 
 * Created on 2015-11-30 上午10:24:08 
 * @author 王凯斌
 */
@Repository("deliveryCorpDaoImpl")
public class DeliveryCorpDaoImpl extends BaseDaoImpl<DeliveryCorp, Long> implements DeliveryCorpDao {

	@Override
	public DeliveryCorp findByName(String name) {
		if (StringUtils.isEmpty(name)) {
			return null;
		}
		try {
			String jpql = "select deliveryCorps from DeliveryCorp deliveryCorps where lower(deliveryCorps.name) = lower(:name)";
			return entityManager.createQuery(jpql, DeliveryCorp.class).setParameter("name", name).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public List<DeliveryCorp> findByName() {
		try {
			String jpql = "select deliveryCorps from DeliveryCorp deliveryCorps ";
			return entityManager.createQuery(jpql, DeliveryCorp.class).getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}

}