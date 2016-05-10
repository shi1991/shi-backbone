package com.puyuntech.ycmall.service;

import java.util.List;

import com.puyuntech.ycmall.entity.Order;
import com.puyuntech.ycmall.entity.Shipping;

/**
 * 
 *  Service - 发货单. 
 * Created on 2015-11-28 下午4:54:53 
 * @author 王凯斌
 */
public interface ShippingService extends BaseService<Shipping, Long> {

	List<Shipping> findByOrder(Order order);

}