package com.puyuntech.ycmall.dao.impl;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import com.puyuntech.ycmall.dao.FriendLinkDao;
import com.puyuntech.ycmall.entity.FriendLink;

/**
 * 
 * DaoImpl - 友情链接. 
 * Created on 2015-8-25 下午4:07:09 
 * @author Liaozhen
 */
@Repository("friendLinkDaoImpl")
public class FriendLinkDaoImpl extends BaseDaoImpl<FriendLink, Long> implements FriendLinkDao {

	public List<FriendLink> findList(FriendLink.Type type) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<FriendLink> criteriaQuery = criteriaBuilder.createQuery(FriendLink.class);
		Root<FriendLink> root = criteriaQuery.from(FriendLink.class);
		criteriaQuery.select(root);
		if (type != null) {
			criteriaQuery.where(criteriaBuilder.equal(root.get("type"), type));
		}
		criteriaQuery.orderBy(criteriaBuilder.asc(root.get("order")));
		return entityManager.createQuery(criteriaQuery).getResultList();
	}

}