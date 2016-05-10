package com.puyuntech.ycmall.service.impl;

import org.springframework.stereotype.Service;

import com.puyuntech.ycmall.entity.OrderItem;
import com.puyuntech.ycmall.service.OrderItemService;
/**
 * 
 * Service 订单 . Created on 2015-9-11 下午2:47:35
 * 
 * @author 施长成
 */
@Service("orderItemServiceImpl")
public class OrderItemServiceImpl extends BaseServiceImpl<OrderItem, Long> implements
		OrderItemService {

//	@Resource(name = "orderItemDaoImpl")
//	private OrderDao orderDao;
	
}
