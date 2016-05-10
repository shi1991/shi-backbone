package com.puyuntech.ycmall.dao;

import java.util.List;

import com.puyuntech.ycmall.entity.Contract;
import com.puyuntech.ycmall.entity.ContractItem;

/**
 * 
 * DAO - 套餐. Created on 2015-10-12 下午6:05:29
 * 
 * @author 王凯斌
 */
public interface ContractPackageDao extends BaseDao<ContractItem, Long> {

	List<ContractItem> findByContract(Contract contract);

}
