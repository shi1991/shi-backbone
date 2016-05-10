package com.puyuntech.ycmall.dao;

import java.util.List;

import com.puyuntech.ycmall.entity.CouponCode;


/**
 * 
 * 优惠券 dao. 
 * Created on 2015-9-22 上午10:34:44 
 * @author 严志森
 */
public interface CouponCodeDao extends BaseDao<CouponCode, Long> {
	
	/**
	 * 设置优惠券使用状态
	 * 
	 * @param receiver
	 *            收货地址
	 */
	void setDefault(CouponCode couponCode);

	
	/**  
	 * 查找优惠码
	 * author: 南金豆
	 *   date: 2015-11-10 下午1:56:56
	 * @param id 优惠劵编号
	 * @return
	 */
	List<Object> find(String id);
}