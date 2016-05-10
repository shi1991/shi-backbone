package com.puyuntech.ycmall.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.puyuntech.ycmall.dao.PointRewardDao;
import com.puyuntech.ycmall.entity.PointReward;
import com.puyuntech.ycmall.service.PointRewardService;


@Service("pointRewardServiceImpl")
public class PointRewardServiceImpl extends BaseServiceImpl<PointReward, Long> implements PointRewardService{
	
	@Resource(name = "pointRewardDaoImpl")
	private PointRewardDao pointRewardDao;
	
	
	@Override
	public boolean invoiceSnExists(String invoiceSn) {
		return pointRewardDao.invoiceSnExists(invoiceSn);
	}

}
