package com.puyuntech.ycmall.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.puyuntech.ycmall.dao.ShippingDao;
import com.puyuntech.ycmall.dao.SnDao;
import com.puyuntech.ycmall.entity.Order;
import com.puyuntech.ycmall.entity.Shipping;
import com.puyuntech.ycmall.entity.Sn;
import com.puyuntech.ycmall.service.ShippingService;

/**
 * 
 * Service - 发货单. 
 * Created on 2015-11-28 下午4:54:19 
 * @author 王凯斌
 */
@Service("shippingServiceImpl")
public class ShippingServiceImpl extends BaseServiceImpl<Shipping, Long> implements ShippingService {

	@Resource(name = "snDaoImpl")
	private SnDao snDao;
	
	@Resource(name = "shippingDaoImpl")
	private ShippingDao shippingDao;

	@Override
	@Transactional
	public Shipping save(Shipping shipping) {
		Assert.notNull(shipping);

		shipping.setSn(snDao.generate(Sn.Type.refunds));

		return super.save(shipping);
	}

	@Override
	public List<Shipping> findByOrder(Order order) {
		return shippingDao.findByOrder(order);
	}

}