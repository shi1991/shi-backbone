package com.puyuntech.ycmall.dao.impl;

import org.springframework.stereotype.Repository;

import com.puyuntech.ycmall.dao.OrderItemDao;
import com.puyuntech.ycmall.entity.OrderItem;

/**
 * 
 * 订单Dao　. 
 * Created on 2015-9-11 下午3:33:57 
 * @author 施长成
 */
@Repository("orderItemDaoImpl")
public class OrderItemDaoImpl extends BaseDaoImpl<OrderItem, Long> implements OrderItemDao {
	
}
