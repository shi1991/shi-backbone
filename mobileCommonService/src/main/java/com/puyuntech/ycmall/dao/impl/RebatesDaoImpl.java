package com.puyuntech.ycmall.dao.impl;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;
import com.puyuntech.ycmall.dao.RebatesDao;
import com.puyuntech.ycmall.entity.Member;
import com.puyuntech.ycmall.entity.Rebates;

/**
 * 
 * 返利Dao　. 
 * Created on 2015-9-11 下午3:33:57 
 * @author 严志森
 */
@Repository("rebatesDaoImpl")
public class RebatesDaoImpl extends BaseDaoImpl<Rebates, Long> implements RebatesDao {
	public List<Rebates> findList(Member member) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Rebates> criteriaQuery = criteriaBuilder.createQuery(Rebates.class);
		Root<Rebates> root = criteriaQuery.from(Rebates.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		if (member != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("member"), member));
		}
		criteriaQuery.where(restrictions);
		return super.findList(criteriaQuery, null, null, null, null);
	}
}
