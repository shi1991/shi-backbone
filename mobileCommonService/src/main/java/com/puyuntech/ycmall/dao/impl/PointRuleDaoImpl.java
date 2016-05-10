package com.puyuntech.ycmall.dao.impl;

import org.springframework.stereotype.Repository;

import com.puyuntech.ycmall.dao.PointRuleDao;
import com.puyuntech.ycmall.entity.PointRule;

/**
 * 
 * Dao - 积分获得规则. 
 * Created on 2015-11-28 下午4:57:21 
 * @author 王凯斌
 */
@Repository("pointRuleDaoImpl")
public class PointRuleDaoImpl extends BaseDaoImpl<PointRule, Long> implements PointRuleDao {

	@Override
	public Long findByType(int type) {
		String jpql = null ;
		switch (type) {
		//每日总额上限
		case 1:
			jpql = "select pointRule.value from PointRule pointRule where pointRule.type =:0 ";
			
			break;
		//每日次数上限
		case 2:
			jpql = "select pointRule.value from PointRule pointRule where pointRule.type =:1 ";
			break;
		//每次获得额度
		case 3:
			jpql = "select pointRule.value from PointRule pointRule where pointRule.type =:2 ";
			break;
		default:
			break;
		}
		return entityManager.createQuery(jpql, Long.class).getSingleResult().longValue();
	}

}