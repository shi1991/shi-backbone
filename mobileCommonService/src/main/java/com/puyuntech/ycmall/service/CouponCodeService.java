package com.puyuntech.ycmall.service;

import java.util.List;

import com.puyuntech.ycmall.entity.CouponCode;


/**
 * 
 * 优惠券 service. 
 * Created on 2015-9-22 上午10:31:34 
 * @author 严志森
 */
public interface CouponCodeService extends BaseService<CouponCode, Long> {

	
	/**  
	 * 查找优惠码
	 * author: 南金豆
	 *   date: 2015-11-10 下午1:56:56
	 * @param id 优惠劵编号
	 * @return
	 */
	List<Object> find(String id);
}