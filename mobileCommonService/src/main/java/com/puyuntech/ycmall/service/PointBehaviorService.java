package com.puyuntech.ycmall.service;

import com.puyuntech.ycmall.entity.Member;
import com.puyuntech.ycmall.entity.PointBehavior;
import com.puyuntech.ycmall.entity.PointLog;

/**
 * 
 *  Service - 积分获得行为. 
 * Created on 2015-11-28 下午4:54:53 
 * @author 王凯斌
 */
public interface PointBehaviorService extends BaseService<PointBehavior, Long> {

	/**
	 * 根据名称查找积分获得行为
	 * 
	 * @param name
	 *            名称(忽略大小写)
	 * @return 积分获得行为，若不存在则返回null
	 */
	PointBehavior findByName(String name);
	
	/**
	 * 添加积分 
	 * @param PointBehavior
	 * @param Member
	 * @param Admin   
	 *  @return    Long 积分  
	 */
	public Long addPoint(Member member,PointBehavior pointBehavior,PointLog.Type type);
}