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
import com.puyuntech.ycmall.dao.StockTransCheckItemDao;
import com.puyuntech.ycmall.dao.StockTransDiffDao;
import com.puyuntech.ycmall.dao.StockTransLogDao;
import com.puyuntech.ycmall.entity.Product;
import com.puyuntech.ycmall.entity.StockLog;
import com.puyuntech.ycmall.entity.StockTransCheck;
import com.puyuntech.ycmall.entity.StockTransCheckItem;
import com.puyuntech.ycmall.entity.StockTransDiff;
import com.puyuntech.ycmall.entity.StockTransItem;
import com.puyuntech.ycmall.entity.StockTransLog;
import com.puyuntech.ycmall.service.StockTransCheckService;

/**
 * 
 * ServiceImpl - 库存变动. 
 * Created on 2015-10-14 下午2:14:04 
 * @author 王凯斌
 */
@Service("stockTransCheckServiceImpl")
public class StockTransCheckServiceImpl  extends BaseServiceImpl<StockTransCheck, Long> implements StockTransCheckService {

	@Resource(name = "stockTransLogDaoImpl")
	private StockTransLogDao stockTransLogDao;
	
	@Resource(name = "stockTransCheckItemDaoImpl")
	private StockTransCheckItemDao stockTransCheckItemDao;
	
	@Resource(name = "stockLogDaoImpl")
	private StockLogDao stockLogDao;
	
	@Resource(name = "stockTransDiffDaoImpl")
	private StockTransDiffDao stockTransDiffDao;
	
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public void check(StockTransLog stockTransLog, StockTransCheck stockTransCheck, List<StockLog> stockLogs) {
		
		stockTransLog.setStatus(StockTransLog.Status.checked);
		stockTransLogDao.merge(stockTransLog);
		
		
		List<StockLog> logs =new ArrayList<StockLog>();
		for(StockTransItem stockTransItem:stockTransLog.getStockTransItems()){
			logs.addAll(stockTransItem.getStockLogs());
		}
		
		logs.removeAll(stockLogs);
		if(logs.size()==0){
			stockTransCheck.setIsDifference(false);
		}else{
			stockTransCheck.setIsDifference(true);
		}
		stockTransCheck.setStockTransLog(stockTransLog);
		save(stockTransCheck);
		
		StockTransDiff stockTransDiff = null;
		for(StockLog stockLog:logs){
			stockTransDiff = new StockTransDiff();
			stockTransDiff.setOperator(stockTransCheck.getOperator());
			stockTransDiff.setStockLog(stockLog);
			stockTransDiff.setStatus(StockTransDiff.Status.await);
			stockTransDiff.setStockTransCheck(stockTransCheck);
			stockTransDiffDao.persist(stockTransDiff);
		}
		
		for(StockLog stockLog:stockLogs){
			stockLog.setState("1");
			stockLog.setOrganization(stockTransLog.getToOrganization());
			stockLogDao.merge(stockLog);
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
		StockTransCheckItem item = null;
		while (it.hasNext()) {
			item = new StockTransCheckItem();
			Map.Entry<Product,List<StockLog>> pair = (Map.Entry<Product,List<StockLog>>) it
					.next();
			item.setProduct(pair.getKey());
			item.setStockTransCheck(stockTransCheck);
			item.setStockLogs(new HashSet<StockLog>(pair.getValue()));
			stockTransCheckItemDao.persist(item);
			it.remove();
		}
	}

}
