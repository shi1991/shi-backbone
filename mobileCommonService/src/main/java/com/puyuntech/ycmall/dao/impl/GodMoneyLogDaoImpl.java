package com.puyuntech.ycmall.dao.impl;


import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import com.puyuntech.ycmall.Page;
import com.puyuntech.ycmall.Pageable;
import com.puyuntech.ycmall.dao.GodMoneyLogDao;
import com.puyuntech.ycmall.entity.GodMoneyLog;
import com.puyuntech.ycmall.entity.Member;


@Repository("godMoneyLogDaoImpl")
public class GodMoneyLogDaoImpl extends BaseDaoImpl<GodMoneyLog, Long> implements GodMoneyLogDao{
	@Override
	public Page<GodMoneyLog> findPage(Member member,Pageable pageable) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<GodMoneyLog> criteriaQuery = criteriaBuilder.createQuery(GodMoneyLog.class);
		Root<GodMoneyLog> root = criteriaQuery.from(GodMoneyLog.class);
		criteriaQuery.select(root);
		if(null != member){
			criteriaQuery.where(criteriaBuilder.equal(root.get("member"), member));
		}
		return super.findPage(criteriaQuery, pageable);
	}
	
}
