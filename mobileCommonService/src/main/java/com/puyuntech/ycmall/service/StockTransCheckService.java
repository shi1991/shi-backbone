package com.puyuntech.ycmall.service;

import java.util.List;

import com.puyuntech.ycmall.entity.StockLog;
import com.puyuntech.ycmall.entity.StockTransCheck;
import com.puyuntech.ycmall.entity.StockTransLog;

/**
 * 
 * Service - 库存变动单. 
 * Created on 2015-10-14 下午1:54:24 
 * @author 王凯斌
 */
public interface StockTransCheckService extends BaseService<StockTransCheck, Long>{
	void check(StockTransLog stockTransLog,StockTransCheck stockTransCheck,List<StockLog> stockLogs);
}
