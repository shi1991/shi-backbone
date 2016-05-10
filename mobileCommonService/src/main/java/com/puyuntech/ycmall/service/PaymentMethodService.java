package com.puyuntech.ycmall.service;

import com.puyuntech.ycmall.entity.PaymentMethod;

/**
 * 
 * Service 支付方式  . 
 * Created on 2015-9-13 下午2:08:20 
 * @author 施长成
 */
public interface PaymentMethodService extends BaseService<PaymentMethod, Long> {

	/**
	 * 根据名称查找支付方式
	 * 
	 * @param name
	 *            名称(忽略大小写)
	 * @return 支付方式，若不存在则返回null
	 */
	PaymentMethod findByName(String name);
	
}
