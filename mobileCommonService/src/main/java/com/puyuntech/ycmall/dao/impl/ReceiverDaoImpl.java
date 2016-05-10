package com.puyuntech.ycmall.dao.impl;

import javax.annotation.Resource;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import com.puyuntech.ycmall.Page;
import com.puyuntech.ycmall.Pageable;
import com.puyuntech.ycmall.dao.ReceiverDao;
import com.puyuntech.ycmall.entity.Member;
import com.puyuntech.ycmall.entity.Receiver;

/**
 * 
 * 收货地址 dao. 
 * Created on 2015-9-22 上午10:35:32 
 * @author 严志森
 */
@Repository("receiverDaoImpl")
public class ReceiverDaoImpl extends BaseDaoImpl<Receiver, Long> implements ReceiverDao {
	

	public Page<Receiver> findPage(Member member, Pageable pageable) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Receiver> criteriaQuery = criteriaBuilder.createQuery(Receiver.class);
		Root<Receiver> root = criteriaQuery.from(Receiver.class);
		criteriaQuery.select(root);
		if (member != null) {
			criteriaQuery.where(criteriaBuilder.equal(root.get("member"), member));
		}
		return super.findPage(criteriaQuery, pageable);
	}

	public void setDefault(Receiver receiver) {
		receiver.setIsDefault(true);
		if (receiver.isNew()) {
			String jpql = "update Receiver receiver set receiver.isDefault = false where receiver.member = :member and receiver.isDefault = true";
			entityManager.createQuery(jpql).setParameter("member", receiver.getMember()).executeUpdate();
		} else {
			String jpql = "update Receiver receiver set receiver.isDefault = false where receiver.member = :member and receiver.isDefault = true and receiver != :receiver";
			entityManager.createQuery(jpql).setParameter("member", receiver.getMember()).setParameter("receiver", receiver).executeUpdate();
		}
	}

}