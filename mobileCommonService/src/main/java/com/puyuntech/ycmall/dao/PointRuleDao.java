package com.puyuntech.ycmall.dao;

import com.puyuntech.ycmall.entity.PointRule;

/**
 * 
 *Dao - 积分获得规则. 
 * Created on 2015-11-28 下午4:56:49 
 * @author 王凯斌
 */
public interface PointRuleDao extends BaseDao<PointRule, Long> {

	Long findByType(int type);

}