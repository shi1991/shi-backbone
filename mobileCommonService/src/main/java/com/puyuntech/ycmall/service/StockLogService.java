package com.puyuntech.ycmall.service;

import java.util.List;

import com.puyuntech.ycmall.Page;
import com.puyuntech.ycmall.Pageable;
import com.puyuntech.ycmall.entity.Organization;
import com.puyuntech.ycmall.entity.Product;
import com.puyuntech.ycmall.entity.StockLog;
import com.puyuntech.ycmall.vo.ResultVO;

/**
 * 
 * Service - 库存记录. 
 * Created on 2015-10-14 下午1:54:24 
 * @author 王凯斌
 */
public interface StockLogService extends BaseService<StockLog, Long>{
	
	/**
	 * 
	 * 根据串号查找库存记录.
	 * author: 严志森
	 *   date: 2015-11-2 下午4:38:27
	 * @param sn
	 * @return
	 */
	StockLog findBySn(String sn);

	List<ResultVO> findSnById(String ids);

	
	
	/**
	 * 查找实体对象分页
	 * 
	 * @param pageable
	 *            分页信息
	 * @return 实体对象分页
	 */
	Page<StockLog> findPage(Pageable pageable);
	
	
	List<StockLog> findSnByShopId(String ids);

	StockLog findBySnAndProId(Product product, String productSn, Organization organization);
	
}
