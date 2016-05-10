package com.puyuntech.ycmall.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.puyuntech.ycmall.dao.StockLogDao;
import com.puyuntech.ycmall.dao.StockTransDiffDao;
import com.puyuntech.ycmall.entity.Organization;
import com.puyuntech.ycmall.entity.StockLog;
import com.puyuntech.ycmall.entity.StockTransDiff;
import com.puyuntech.ycmall.service.StockTransDiffService;

/**
 * 
 * ServiceImpl - 库存变动. 
 * Created on 2015-10-14 下午2:14:04 
 * @author 王凯斌
 */
@Service("stockTransDiffServiceImpl")
public class StockTransDiffServiceImpl  extends BaseServiceImpl<StockTransDiff, Long> implements StockTransDiffService {
	
	@Resource(name = "stockLogDaoImpl")
	private StockLogDao stockLogDao;
	
	@Resource(name = "stockTransDiffDaoImpl")
	private StockTransDiffDao stockTransDiffDao;
	
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public void dispose(Long id,String type,Organization organization){
		StockTransDiff stockTransDiff = find(id);
		StockLog stockLog = stockTransDiff.getStockLog();
		
		if(type.equals("0")){
			stockLog.setState("8");
			stockTransDiff.setStatus(StockTransDiff.Status.loss);
			stockTransDiffDao.merge(stockTransDiff);
			stockLogDao.merge(stockLog);
		}else{
			stockLog.setState("1");
			stockLog.setOrganization(organization);
			stockLogDao.merge(stockLog);
			stockTransDiff.setStatus(StockTransDiff.Status.check);
			stockTransDiffDao.merge(stockTransDiff);
		}
	}
}
