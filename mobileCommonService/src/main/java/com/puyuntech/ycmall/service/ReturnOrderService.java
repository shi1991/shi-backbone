package com.puyuntech.ycmall.service;

import com.puyuntech.ycmall.Page;
import com.puyuntech.ycmall.Pageable;
import com.puyuntech.ycmall.entity.Organization;
import com.puyuntech.ycmall.entity.ReturnOrder;

/**
 * 
 * Service - 退单. 
 * Created on 2015-10-14 下午1:54:24 
 * @author 王凯斌
 */
public interface ReturnOrderService extends BaseService<ReturnOrder, Long>{
		
	Page<ReturnOrder> findByOrg(Organization organization,Pageable pageable);

}
