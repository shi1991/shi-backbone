package com.puyuntech.ycmall.dao;

import java.util.List;

import com.puyuntech.ycmall.entity.DeliveryCorp;

/**
 * 
 * Dao - 物流公司. 
 * Created on 2015-11-30 上午10:23:34 
 * @author 王凯斌
 */
public interface DeliveryCorpDao extends BaseDao<DeliveryCorp, Long> {

	
	/**
	 * 根据名称查找物流公司
	 * 
	 * @param name
	 *            名称(忽略大小写)
	 * @return 物流公司，若不存在则返回null
	 */
	DeliveryCorp findByName(String name);

	List<DeliveryCorp> findByName();

}