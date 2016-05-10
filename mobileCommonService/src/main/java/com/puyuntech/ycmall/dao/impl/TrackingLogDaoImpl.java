package com.puyuntech.ycmall.dao.impl;

import javax.persistence.NoResultException;

import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.puyuntech.ycmall.dao.TrackingLogDao;
import com.puyuntech.ycmall.entity.Order;
import com.puyuntech.ycmall.entity.TrackingLog;

/**
 * 
 * 物流信息. 
 * Created on 2015-12-22 下午6:16:01 
 * @author 严志森
 */
@Repository("trackingLogDaoImpl")
public class TrackingLogDaoImpl extends BaseDaoImpl<TrackingLog, Long> implements TrackingLogDao{

	@Override
	public TrackingLog findByTrackingId(String nu) {
		if (StringUtils.isEmpty(nu)) {
			return null;
		}
		String jpql = "select trackingLog from TrackingLog trackingLog where lower(trackingLog.trackingId) = lower(:nu)";
		try {
			return entityManager.createQuery(jpql, TrackingLog.class).setParameter("nu", nu).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

}
