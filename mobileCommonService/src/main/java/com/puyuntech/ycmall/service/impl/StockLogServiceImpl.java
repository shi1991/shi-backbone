package com.puyuntech.ycmall.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.puyuntech.ycmall.Page;
import com.puyuntech.ycmall.Pageable;
import com.puyuntech.ycmall.dao.StockLogDao;
import com.puyuntech.ycmall.entity.Organization;
import com.puyuntech.ycmall.entity.Product;
import com.puyuntech.ycmall.entity.StockLog;
import com.puyuntech.ycmall.service.StockLogService;
import com.puyuntech.ycmall.vo.ResultVO;

/**
 * 
 * ServiceImpl - 运营商. 
 * Created on 2015-10-14 下午2:14:04 
 * @author 王凯斌
 */
@Service("stockLogServiceImpl")
public class StockLogServiceImpl  extends BaseServiceImpl<StockLog, Long> implements StockLogService {
	
	@Resource(name="stockLogDaoImpl")
	private StockLogDao stockLogDao;
	
	public StockLog findBySn(String sn) {
		return stockLogDao.findBySn(sn);
	}

	@Override
	public List<ResultVO> findSnById(String ids) {
		return stockLogDao.findSnById(ids);
	}

	
	@Override
	public Page<StockLog> findPage(Pageable pageable) {
		return stockLogDao.findPage(pageable);
	}

	@Override
	public List<StockLog> findSnByShopId(String ids) {
		// TODO Auto-generated method stub
		return stockLogDao.findSnByShopId("");
	}

	@Override
	public StockLog findBySnAndProId(Product product, String productSn, Organization organization) {
		return stockLogDao.findBySnAndProId(product,productSn,organization);
	}
	
}
