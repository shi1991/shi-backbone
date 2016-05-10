package com.puyuntech.ycmall.dao;

import com.puyuntech.ycmall.Page;
import com.puyuntech.ycmall.Pageable;
import com.puyuntech.ycmall.entity.Organization;
import com.puyuntech.ycmall.entity.ReturnOrder;

/**
 * 
 * 退单DAO. Created on 2015-10-12 下午6:05:29
 * 
 * @author 王凯斌
 */
public interface ReturnOrderDao extends BaseDao<ReturnOrder, Long> {

	Page<ReturnOrder> findByOrg(Organization organization, Pageable pageable);

}
