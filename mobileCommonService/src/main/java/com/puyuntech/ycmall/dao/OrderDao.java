package com.puyuntech.ycmall.dao;

import java.util.List;

import com.puyuntech.ycmall.entity.Member;
import com.puyuntech.ycmall.entity.Order;

/**
 * 
 * 订单中心 . 
 * Created on 2015-9-11 下午3:31:44 
 * @author 施长成
 */
public interface OrderDao extends BaseDao<Order, Long> {
	/**
	 * 查找订单
	 * 
	 * @param type
	 *            类型
	 * @param status
	 *            状态
	 * @param member
	 *            会员
	 * @param isDelete
	 *            是否被删除
	 * @return 订单
	 */
	List<Order> findList(Order.Type type, Order.Status status, Member member, Boolean isDelete);

	/**
	 * 
	 * 根据编号查找订单.
	 * author: 严志森
	 *   date: 2015-9-25 下午3:10:23
	 * @param sn
	 * 				编号(忽略大小写)
	 * @return 		订单，若不存在则返回null
	 */
	Order findBySn(String sn);
}
