package com.puyuntech.ycmall.dao.impl;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.puyuntech.ycmall.dao.PointRewardDao;
import com.puyuntech.ycmall.entity.PointReward;

@Repository("pointRewardDaoImpl")
public class PointRewardDaoImpl  extends BaseDaoImpl<PointReward, Long> implements PointRewardDao{

	@Override
	public boolean invoiceSnExists(String invoiceSn) {
		if (StringUtils.isEmpty(invoiceSn)) {
			return false;
		}
		String jpql = "select count(*) from PointReward pointRewards where lower(pointRewards.invoiceSn) = lower(:invoiceSn)";
		Long count = entityManager.createQuery(jpql, Long.class).setParameter("invoiceSn", invoiceSn).getSingleResult();
		return count > 0;
	}

}
