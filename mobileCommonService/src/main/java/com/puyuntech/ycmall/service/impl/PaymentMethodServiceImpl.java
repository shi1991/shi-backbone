package com.puyuntech.ycmall.service.impl;


import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.puyuntech.ycmall.dao.PaymentMethodDao;
import com.puyuntech.ycmall.entity.PaymentMethod;
import com.puyuntech.ycmall.service.PaymentMethodService;

/**
 * 
 * 支付方式 Service . 
 * Created on 2015-9-13 下午2:16:59 
 * @author 施长成
 */
@Service("paymentMethodServiceImpl")
public class PaymentMethodServiceImpl extends BaseServiceImpl<PaymentMethod, Long> implements PaymentMethodService{
	
	@Resource(name="paymentMethodDaoImpl")
	private PaymentMethodDao paymentMethodDao;
	
	@Transactional(readOnly = true)
	public PaymentMethod findByName(String name) {
		return paymentMethodDao.findByName(name);
	}
}
