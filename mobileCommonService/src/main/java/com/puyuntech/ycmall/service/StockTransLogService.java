package com.puyuntech.ycmall.service;

import java.util.List;

import com.puyuntech.ycmall.entity.StockLog;
import com.puyuntech.ycmall.entity.StockTransLog;
import com.puyuntech.ycmall.entity.StockTransReq;

/**
 * 
 * Service - 库存变动单. 
 * Created on 2015-10-14 下午1:54:24 
 * @author 王凯斌
 */
public interface StockTransLogService extends BaseService<StockTransLog, Long>{
	//重载save
	void save(StockTransLog stockTransLog,List<StockLog> stockLogs,StockTransReq stockTransReq);
	
	void inBill(StockTransLog stockTransLog);

}
