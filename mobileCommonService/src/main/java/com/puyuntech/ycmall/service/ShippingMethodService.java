package com.puyuntech.ycmall.service;

import com.puyuntech.ycmall.entity.ShippingMethod;

/**
 * 
 * Service 配送方式 . 
 * Created on 2015-9-13 下午2:18:14 
 * @author 施长成
 */
public interface ShippingMethodService extends BaseService<ShippingMethod, Long> {

	
	/**
	 * 根据名称查找配送方式
	 * 
	 * @param name
	 *            名称(忽略大小写)
	 * @return 配送方式，若不存在则返回null
	 */
	ShippingMethod findByName(String name);
}
