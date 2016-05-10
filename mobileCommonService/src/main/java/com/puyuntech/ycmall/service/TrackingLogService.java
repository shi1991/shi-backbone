package com.puyuntech.ycmall.service;

import com.puyuntech.ycmall.entity.TrackingLog;

/**
 * 
 * 物流信息. 
 * Created on 2015-12-22 下午6:13:10 
 * @author 严志森
 */
public interface TrackingLogService extends BaseService<TrackingLog, Long>{

	TrackingLog findByTrackingId(String nu);

}
