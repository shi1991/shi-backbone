package com.puyuntech.ycmall.service;

import com.puyuntech.ycmall.entity.Organization;
import com.puyuntech.ycmall.entity.StockTransDiff;

/**
 * 
 * Service - 库存变动单. 
 * Created on 2015-10-14 下午1:54:24 
 * @author 王凯斌
 */
public interface StockTransDiffService extends BaseService<StockTransDiff, Long>{
	//处理差异
	void dispose(Long id,String type,Organization organization);
}
