package com.puyuntech.ycmall.dao;

import com.puyuntech.ycmall.entity.Refunds;

/**
 * 
 *Dao - 退款单. 
 * Created on 2015-11-28 下午4:56:49 
 * @author 王凯斌
 */
public interface RefundsDao extends BaseDao<Refunds, Long> {

    /**
     * 根据pingxx 退款流水号查询退款单
     * @param pingxxSn
     * @return
     * @author 施长成
     */
    Refunds findByPingXXSn(String pingxxSn);
}