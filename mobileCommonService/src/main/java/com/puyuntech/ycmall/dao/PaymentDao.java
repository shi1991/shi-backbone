package com.puyuntech.ycmall.dao;

import com.puyuntech.ycmall.entity.Payment;

/**
 * 
 * Dao 收款单. 
 * Created on 2015-9-13 下午3:50:52 
 * @author 施长成
 */
public interface PaymentDao extends BaseDao<Payment, Long> {

	/**
	 * 根据编号查找收款单
	 * 
	 * @param sn
	 *            编号(忽略大小写)
	 * @return 收款单，若不存在则返回null
	 */
	Payment findBySn(String sn);
	
}
