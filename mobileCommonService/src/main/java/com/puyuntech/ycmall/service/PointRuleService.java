package com.puyuntech.ycmall.service;

import com.puyuntech.ycmall.entity.PointRule;

/**
 * 
 *  Service - 积分获得规则. 
 * Created on 2015-11-28 下午4:54:53 
 * @author 王凯斌
 */
public interface PointRuleService extends BaseService<PointRule, Long> {
	
	Long findByType(int type);
}