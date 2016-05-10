package com.puyuntech.ycmall.dao.impl;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import com.puyuntech.ycmall.Page;
import com.puyuntech.ycmall.Pageable;
import com.puyuntech.ycmall.dao.BonusDao;
import com.puyuntech.ycmall.entity.BonusEntity;
import com.puyuntech.ycmall.entity.Member;

@Repository("bonusDaoImpl")
public class BonusDaoImpl extends BaseDaoImpl<BonusEntity, Long> implements BonusDao{

	@Override
	public Page<BonusEntity> findPage(Member member,Pageable pageable) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<BonusEntity> criteriaQuery = criteriaBuilder.createQuery(BonusEntity.class);
		Root<BonusEntity> root = criteriaQuery.from(BonusEntity.class);
		criteriaQuery.select(root);
		if(null != member){
			criteriaQuery.where(criteriaBuilder.equal(root.get("member"), member));
		}
		return super.findPage(criteriaQuery, pageable);
	}
}
