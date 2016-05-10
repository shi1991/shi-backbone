package com.puyuntech.ycmall.dao;

import java.util.List;

import com.puyuntech.ycmall.entity.Order;
import com.puyuntech.ycmall.entity.Shipping;

/**
 * 
 * 发货单 . Created on 2015-9-13 下午5:02:08
 * 
 * @author 施长成
 */
public interface ShippingDao extends BaseDao<Shipping, Long> {
	/**
	 * 根据编号查找发货单
	 * 
	 * @param sn
	 *            编号(忽略大小写)
	 * @return 发货单，若不存在则返回null
	 */
	Shipping findBySn(String sn);

	List<Shipping> findByOrder(Order order);
}
