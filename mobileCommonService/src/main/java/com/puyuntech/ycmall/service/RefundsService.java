package com.puyuntech.ycmall.service;

import com.puyuntech.ycmall.entity.Order;
import com.puyuntech.ycmall.entity.Refunds;

/**
 * 
 *  Service - 退款单. 
 * Created on 2015-11-28 下午4:54:53 
 * @author 王凯斌
 */
public interface RefundsService extends BaseService<Refunds, Long> {

    /**
     * 保存 退款单
     * @param refunds
     * @param order
     * @return
     */
	public Refunds save(Refunds refunds,Order order);

    /**
     * 根据退款流水单号，获取退款单
     * @param pingxxSn
     * @return
     */
    public Refunds findByPingXXSn(String pingxxSn);
}