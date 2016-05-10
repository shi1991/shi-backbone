package com.puyuntech.ycmall.service;

import java.util.List;

import com.puyuntech.ycmall.entity.Contract;
import com.puyuntech.ycmall.entity.Operator;

/**
 * 
 * 合约套餐 . 
 * Created on 2015-10-18 上午11:35:30 
 * @author 施长成
 */
public interface ContractService extends BaseService<Contract , Long> {

	List<Contract> findByOperator(Operator operator);
	

}
