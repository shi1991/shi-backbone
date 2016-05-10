package com.puyuntech.ycmall.service.impl;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import org.apache.commons.lang.BooleanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.puyuntech.ycmall.Page;
import com.puyuntech.ycmall.Pageable;
import com.puyuntech.ycmall.dao.ReceiverDao;
import com.puyuntech.ycmall.entity.Member;
import com.puyuntech.ycmall.entity.Receiver;
import com.puyuntech.ycmall.service.ReceiverService;

/**
 * 
 * service 收货地址. 
 * Created on 2015-9-22 上午10:33:29 
 * @author 严志森
 */
@Service("receiverServiceImpl")
public class ReceiverServiceImpl extends BaseServiceImpl<Receiver, Long> implements ReceiverService {

	@Resource(name = "receiverDaoImpl")
	private ReceiverDao receiverDao;
	
	@PersistenceContext
	protected EntityManager entityManager;

	@Transactional
	public Receiver findDefault(Member member) {
		if (member == null) {
			return null;
		}
		try {
			String jpql = "select receiver from Receiver receiver where receiver.member = :member and receiver.isDefault = true";
			return entityManager.createQuery(jpql, Receiver.class).setParameter("member", member).getSingleResult();
		} catch (NoResultException e) {
			try {
				String jpql = "select receiver from Receiver receiver where receiver.member = :member order by receiver.modifyDate desc";
				Receiver receiver = entityManager.createQuery(jpql, Receiver.class).setParameter("member", member).setMaxResults(1).getSingleResult();
				if(receiver != null){
					receiver.setIsDefault(true);
					this.update(receiver);
				}
				return receiver;
			} catch (NoResultException e1) {
				return null;
			}
		}
	}

	@Transactional(readOnly = true)
	public Page<Receiver> findPage(Member member, Pageable pageable) {
		return receiverDao.findPage(member, pageable);
	}

	@Override
	@Transactional
	public Receiver save(Receiver receiver) {
		Assert.notNull(receiver);

		if (BooleanUtils.isTrue(receiver.getIsDefault())) {
			receiverDao.setDefault(receiver);
		}
		return super.save(receiver);
	}

	@Override
	@Transactional
	public Receiver update(Receiver receiver) {
		Assert.notNull(receiver);

		if (BooleanUtils.isTrue(receiver.getIsDefault())) {
			receiverDao.setDefault(receiver);
		}
		return super.update(receiver);
	}

}