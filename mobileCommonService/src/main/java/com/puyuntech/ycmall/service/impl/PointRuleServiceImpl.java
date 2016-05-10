package com.puyuntech.ycmall.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.puyuntech.ycmall.dao.PointRuleDao;
import com.puyuntech.ycmall.entity.PointRule;
import com.puyuntech.ycmall.service.PointRuleService;

/**
 * 
 * Service - 积分获得规则. 
 * Created on 2015-11-28 下午4:54:19 
 * @author 王凯斌
 */
@Service("pointRuleServiceImpl")
public class PointRuleServiceImpl extends BaseServiceImpl<PointRule, Long> implements PointRuleService {
	@Resource(name = "pointRuleDaoImpl")
	private PointRuleDao pointRuleDao;
	
	@Override
	public Long findByType(int type) {
		return pointRuleDao.findByType(type);
	}
	
}