package com.puyuntech.ycmall.dao.impl;

import javax.persistence.NoResultException;

import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.puyuntech.ycmall.dao.OrderLogDao;
import com.puyuntech.ycmall.entity.Order;
import com.puyuntech.ycmall.entity.OrderLog;
import com.puyuntech.ycmall.entity.Receiver;

/**
 * 
 * 订单记录 . 
 * Created on 2015-9-13 下午3:22:16 
 * @author 施长成
 */
@Repository("orderLogDaoImpl")
public class OrderLogDaoImpl extends BaseDaoImpl<OrderLog, Long> implements OrderLogDao {

	@Override
	public OrderLog findByStye(Long id) {
		if ( id == null ) {
			return null;
		}
		try {
			String jpql = "select orderLogs from OrderLog orderLogs where lower(orderLogs.order) = lower(:id) and orderLogs.type = 8 order by orderLogs.modifyDate desc";
			return entityManager.createQuery(jpql, OrderLog.class).setParameter("id", id).setMaxResults(1).getSingleResult();
		} catch (NoResultException e) {
		}
		return null;
	}

}
