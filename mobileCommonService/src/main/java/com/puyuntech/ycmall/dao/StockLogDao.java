package com.puyuntech.ycmall.dao;

import java.util.List;

import com.puyuntech.ycmall.Filter;
import com.puyuntech.ycmall.Page;
import com.puyuntech.ycmall.Pageable;
import com.puyuntech.ycmall.entity.Organization;
import com.puyuntech.ycmall.entity.Product;
import com.puyuntech.ycmall.entity.StockLog;
import com.puyuntech.ycmall.entity.StockTransLog;
import com.puyuntech.ycmall.vo.ResultVO;

/**
 * 
 * 库存记录-DAO. Created on 2015-10-12 下午6:05:29
 * 
 * @author 王凯斌
 */
public interface StockLogDao extends BaseDao<StockLog, Long> {
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
	
	List<StockLog> findSnByShopId(String shopid);
	
	/**
	 * 
	 * 根据店铺名称去除重复商品记录
	 * author: 吴玉章
	 *   date: 2015-11-2 下午4:38:27
	 * @param sn
	 * @return
	 */

	Page<StockLog> findPage(Pageable pageable);

	StockLog findBySnAndProId(Product product, String productSn, Organization organization);

}
