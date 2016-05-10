package com.puyuntech.ycmall.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.puyuntech.ycmall.dao.StockTransReqDao;
import com.puyuntech.ycmall.dao.StockTransReqItemDao;
import com.puyuntech.ycmall.entity.PurchaseRequisition;
import com.puyuntech.ycmall.entity.StockTransReq;
import com.puyuntech.ycmall.entity.StockTransReqItem;
import com.puyuntech.ycmall.service.StockTransReqService;

/**
 * 
 * ServiceImpl - 库存变动. 
 * Created on 2015-10-14 下午2:14:04 
 * @author 王凯斌
 */
@Service("stockTransReqServiceImpl")
public class StockTransReqServiceImpl  extends BaseServiceImpl<StockTransReq, Long> implements StockTransReqService {
	
	@Resource(name = "stockTransReqDaoImpl")
	private StockTransReqDao stockTransReqDao;
	
	@Resource(name = "stockTransReqItemDaoImpl")
	private StockTransReqItemDao stockTransReqItemDao;
	
	
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public void inBill(Long[] ids){
		
		StockTransReq stockTransReq = null;
		for(Long id:ids){
			stockTransReq = find(id);
			stockTransReq.setStatus(StockTransReq.Status.inBill);
			update(stockTransReq);
		}
	}
	
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public void save(StockTransReq stockTransReq,List<StockTransReqItem> stockTransReqItems) {
		
		//创建申请单表头
		stockTransReqDao.persist(stockTransReq);
		//创建申请项
		for(StockTransReqItem item:stockTransReqItems){
			item.setStockTransReq(stockTransReq);
			stockTransReqItemDao.persist(item);
		}
	}
}
