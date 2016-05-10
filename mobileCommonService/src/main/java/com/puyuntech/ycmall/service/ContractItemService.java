package com.puyuntech.ycmall.service;

import java.util.List;

import com.puyuntech.ycmall.entity.Contract;
import com.puyuntech.ycmall.entity.ContractItem;

/**
 * 
 * Service - 套餐内容项  . 
 * Created on 2015-10-14 下午1:54:24 
 * @author 王凯斌
 * 		update 施长成 rename
 */
public interface ContractItemService extends BaseService<ContractItem, Long>{

	List<ContractItem> findByContract(Contract contract);

}
