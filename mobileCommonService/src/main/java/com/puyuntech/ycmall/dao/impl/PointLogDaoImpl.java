package com.puyuntech.ycmall.dao.impl;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import com.puyuntech.ycmall.Page;
import com.puyuntech.ycmall.Pageable;
import com.puyuntech.ycmall.dao.PointLogDao;
import com.puyuntech.ycmall.entity.Member;
import com.puyuntech.ycmall.entity.PointLog;

/**
 * 
 * 积分Dao . 
 * Created on 2015-9-13 下午4:06:16 
 * @author 施长成
 */
@Repository("pointLogDaoImpl")
public class PointLogDaoImpl extends BaseDaoImpl<PointLog, Long> implements PointLogDao {

	@Override
	public Page<PointLog> findPage(Member member, Pageable pageable) {
		if (member == null) {
			return Page.emptyPage(pageable);
		}
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<PointLog> criteriaQuery = criteriaBuilder.createQuery(PointLog.class);
		Root<PointLog> root = criteriaQuery.from(PointLog.class);
		criteriaQuery.select(root);
		criteriaQuery.where(criteriaBuilder.equal(root.get("member"), member));
		return super.findPage(criteriaQuery, pageable);
	}

	@Override
	public Long findByMemberNow(Member member) {
		String jpql = "select SUM(pointLog.credit) from PointLog pointLog where pointLog.member =:member AND date(pointLog.createDate)= curdate() AND pointLog.type = 9";
		Long count = entityManager.createQuery(jpql, Long.class).setParameter("member", member).getSingleResult();
		return count == null ? 0l : count;
	}
	
	@Override
	public Long findCountByMemberNow(Member member) {
		String jpql = "select count(*) from PointLog pointLog where pointLog.member =:member AND date(pointLog.createDate)= curdate() AND pointLog.type = 9";
		Long count = entityManager.createQuery(jpql, Long.class).setParameter("member", member).getSingleResult();
		return count;
	}

}
