package com.puyuntech.ycmall.dao;

import com.puyuntech.ycmall.entity.TrackingLog;

/**
 * 
 * 物流信息. 
 * Created on 2015-12-22 下午6:15:13 
 * @author 严志森
 */
public interface TrackingLogDao extends BaseDao<TrackingLog, Long> {

	TrackingLog findByTrackingId(String nu);
}
