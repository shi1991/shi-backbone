package com.puyuntech.ycmall.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.puyuntech.ycmall.dao.StockLogDao;
import com.puyuntech.ycmall.dao.StockTransItemDao;
import com.puyuntech.ycmall.dao.StockTransLogDao;
import com.puyuntech.ycmall.dao.StockTransReqDao;
import com.puyuntech.ycmall.entity.Product;
import com.puyuntech.ycmall.entity.StockLog;
import com.puyuntech.ycmall.entity.StockTransItem;
import com.puyuntech.ycmall.entity.StockTransLog;
import com.puyuntech.ycmall.entity.StockTransReq;
import com.puyuntech.ycmall.service.StockTransLogService;

/**
 * 
 * ServiceImpl - 库存变动. 
 * Created on 2015-10-14 下午2:14:04 
 * @author 王凯斌
 */
@Service("stockTransLogServiceImpl")
public class StockTransLogServiceImpl  extends BaseServiceImpl<StockTransLog, Long> implements StockTransLogService {
	
	@Resource(name = "stockTransItemDaoImpl")
	private StockTransItemDao stockTransItemDao;
	
	@Resource(name = "stockTransLogDaoImpl")
	private StockTransLogDao stockTransLogDao;
	
	@Resource(name = "stockTransReqDaoImpl")
	private StockTransReqDao stockTransReqDao;
	
	@Resource(name = "stockLogDaoImpl")
	private StockLogDao stockLogDao;
	
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public void save(StockTransLog stockTransLog, List<StockLog> stockLogs,StockTransReq stockTransReq) {
		
		save(stockTransLog);
		if(stockTransReq!=null){
			stockTransReq.setStatus(StockTransReq.Status.agree);
			stockTransReqDao.merge(stockTransReq);
		}
		
		Map<Product,List<StockLog>> map =new HashMap<Product,List<StockLog>>();
		for(StockLog stockLog:stockLogs){
			if(!map.containsKey(stockLog.getProduct())){
				map.put(stockLog.getProduct(), new ArrayList<StockLog>());
			}
			map.get(stockLog.getProduct()).add(stockLog);
		}
		
		Iterator<Entry<Product,List<StockLog>>> it = map.entrySet()
				.iterator();
		StockTransItem item = null;
		while (it.hasNext()) {
			item = new StockTransItem();
			Map.Entry<Product,List<StockLog>> pair = (Map.Entry<Product,List<StockLog>>) it
					.next();
			item.setProduct(pair.getKey());
			item.setStockTransLog(stockTransLog);
			item.setStockLogs(new HashSet<StockLog>(pair.getValue()));
			stockTransItemDao.persist(item);
			it.remove();
		}
	}
	
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public void inBill(StockTransLog stockTransLog) {
		
		for(StockTransItem item:stockTransLog.getStockTransItems()){
			for(StockLog stockLog:item.getStockLogs()){
				if(stockTransLog.getType().equals(StockTransLog.Type.Allocation)){
					stockLog.setState("4");
				}else if(stockTransLog.getType().equals(StockTransLog.Type.Delivery)){
					stockLog.setState("3");
				}else{
					stockLog.setState("8");
				}
				
				stockLogDao.merge(stockLog);
			}
		}
		
		stockTransLog.setStatus(StockTransLog.Status.inBill);
		update(stockTransLog);
	}
	
	
}
