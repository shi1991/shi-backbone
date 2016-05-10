package com.puyuntech.ycmall.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.puyuntech.ycmall.dao.ShippingMethodDao;
import com.puyuntech.ycmall.entity.ShippingMethod;
import com.puyuntech.ycmall.service.ShippingMethodService;

/**
 * 
 * 配送方式 . 
 * Created on 2015-9-13 下午2:19:49 
 * @author 施长成
 */
@Service("shippingMethodServiceImpl")
public class ShippingMethodServiceImpl extends BaseServiceImpl<ShippingMethod, Long> implements ShippingMethodService {

	@Resource(name="shippingMethodDaoImpl")
	private ShippingMethodDao shippingMethodDao;

	@Transactional(readOnly = true)
	public ShippingMethod findByName(String name) {
		return shippingMethodDao.findByName(name);
	}
	
}
