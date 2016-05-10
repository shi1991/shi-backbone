package com.puyuntech.ycmall.dao;

import com.puyuntech.ycmall.entity.OrderLog;

/**
 * 
 * 订单记录 Dao . 
 * Created on 2015-9-13 下午3:21:08 
 * @author 施长成
 */
public interface OrderLogDao extends BaseDao<OrderLog , Long> {

	OrderLog findByStye(Long id);

}
