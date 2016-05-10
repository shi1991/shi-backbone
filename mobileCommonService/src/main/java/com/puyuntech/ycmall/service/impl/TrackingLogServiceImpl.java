package com.puyuntech.ycmall.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.puyuntech.ycmall.dao.TrackingLogDao;
import com.puyuntech.ycmall.entity.TrackingLog;
import com.puyuntech.ycmall.service.TrackingLogService;

/**
 * 
 * 物流信息. 
 * Created on 2015-12-22 下午6:14:19 
 * @author 严志森
 */
@Service("trackingLogServiceImpl")
public class TrackingLogServiceImpl  extends BaseServiceImpl<TrackingLog, Long> implements TrackingLogService {
	@Resource(name="trackingLogDaoImpl")
	private TrackingLogDao trackingLogDao;
	@Override
	public TrackingLog findByTrackingId(String nu) {
		return trackingLogDao.findByTrackingId(nu);
	}

}
