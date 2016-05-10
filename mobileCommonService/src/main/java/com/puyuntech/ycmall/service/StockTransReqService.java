package com.puyuntech.ycmall.service;

import java.util.List;

import com.puyuntech.ycmall.entity.StockTransReq;
import com.puyuntech.ycmall.entity.StockTransReqItem;

/**
 * 
 * Service - 库存变动单. 
 * Created on 2015-10-14 下午1:54:24 
 * @author 王凯斌
 */
public interface StockTransReqService extends BaseService<StockTransReq, Long>{
	void save(StockTransReq stockTransReq,List<StockTransReqItem> stockTransReqItems);
	
	void inBill(Long[] ids);
}
