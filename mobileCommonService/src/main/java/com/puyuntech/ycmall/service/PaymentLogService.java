package com.puyuntech.ycmall.service;

import com.puyuntech.ycmall.entity.PaymentLog;



/**
 *  Service - 支付记录
 * Created on 2015-10-23 下午1:35:14 
 * @author 南金豆
 */

public interface PaymentLogService extends BaseService<PaymentLog, Long> {

	/**
	 * 根据编号查找支付记录
	 * 
	 * @param sn
	 *            编号(忽略大小写)
	 * @return 支付记录，若不存在则返回null
	 */
	PaymentLog findBySn(String sn);
	
	/**
	 * 根据pingxxSN查找支付记录
	 * 
	 * @param sn
	 *            编号(忽略大小写)
	 * @return 支付记录，若不存在则返回null
	 */
	PaymentLog findByPingxxSN(String pingxxSN);

	/**
	 * 支付处理
	 *  @param paymentLogSn
     *            支付记录编号
     * @param status 订单记录状态
     * @param buyerAcount 订单支付帐号
     */
	void handle(String paymentLogSn, PaymentLog.Status status, String buyerAcount);

}