package com.puyuntech.ycmall.dao;

import com.puyuntech.ycmall.entity.PointReward;



public interface PointRewardDao extends BaseDao<PointReward , Long>{

	/**
	 * 判断invoiceSn是否存在
	 * 
	 * @param invoiceSn
	 *            invoiceSn(忽略大小写)
	 * @return invoiceSn是否存在
	 */
	
	boolean invoiceSnExists(String invoiceSn);

	

}
