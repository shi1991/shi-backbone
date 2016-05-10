package com.puyuntech.ycmall.dao.impl;

import javax.persistence.NoResultException;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.puyuntech.ycmall.dao.PointBehaviorDao;
import com.puyuntech.ycmall.entity.PointBehavior;

/**
 * 
 * Dao - 积分获得行为. 
 * Created on 2015-11-28 下午4:57:21 
 * @author 王凯斌
 */
@Repository("pointBehaviorDaoImpl")
public class PointBehaviorDaoImpl extends BaseDaoImpl<PointBehavior, Long> implements PointBehaviorDao {

	@Override
	public PointBehavior findByName(String name) {
		if (StringUtils.isEmpty(name)) {
			return null;
		}
		try {
			String jpql = "select pointBehaviors from PointBehavior pointBehaviors where lower(pointBehaviors.name) = lower(:name)";
			return entityManager.createQuery(jpql, PointBehavior.class).setParameter("name", name).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

}