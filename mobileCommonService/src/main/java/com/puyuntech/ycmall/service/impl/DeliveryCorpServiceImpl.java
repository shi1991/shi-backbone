package com.puyuntech.ycmall.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.puyuntech.ycmall.dao.DeliveryCorpDao;
import com.puyuntech.ycmall.entity.DeliveryCorp;
import com.puyuntech.ycmall.service.DeliveryCorpService;

/**
 * 
 * Service - 物流公司. 
 * Created on 2015-11-30 上午10:25:27 
 * @author 王凯斌
 */
@Service("deliveryCorpServiceImpl")
public class DeliveryCorpServiceImpl extends BaseServiceImpl<DeliveryCorp, Long> implements DeliveryCorpService {

	@Resource(name="deliveryCorpDaoImpl")
	private DeliveryCorpDao deliveryCorpDao;
	
	
	@Override
	public DeliveryCorp findByName(String name) {
		return deliveryCorpDao.findByName(name);
	}


	@Override
	public List<DeliveryCorp> findByName() {
		return deliveryCorpDao.findByName();
	}

}