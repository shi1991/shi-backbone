package com.puyuntech.ycmall.dao;

import com.puyuntech.ycmall.entity.PointBehavior;

/**
 * 
 *Dao - 积分获得行为. 
 * Created on 2015-11-28 下午4:56:49 
 * @author 王凯斌
 */
public interface PointBehaviorDao extends BaseDao<PointBehavior, Long> {

	
	/**
	 * 根据名称查找积分获得行为
	 * 
	 * @param name
	 *            名称(忽略大小写)
	 * @return 积分获得行为，若不存在则返回null
	 */
	PointBehavior findByName(String name);

}