package com.puyuntech.ycmall.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.puyuntech.ycmall.dao.CouponCodeDao;
import com.puyuntech.ycmall.entity.CouponCode;
import com.puyuntech.ycmall.service.CouponCodeService;

/**
 * 
 * service 优惠券. 
 * Created on 2015-9-22 上午10:33:29 
 * @author 严志森
 */
@Service("couponCodeServiceImpl")
public class CouponCodeServiceImpl extends BaseServiceImpl<CouponCode, Long> implements CouponCodeService {

	
	@Resource(name = "couponCodeDaoImpl")
	private CouponCodeDao couponCodeDao;

	@Override
	public List<Object> find(String id) {
		return couponCodeDao.find(id);
	}



}