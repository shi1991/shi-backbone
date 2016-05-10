package com.puyuntech.ycmall.dao;

import java.util.List;

import com.puyuntech.ycmall.entity.Contract;
import com.puyuntech.ycmall.entity.Operator;

/**
 * 
 * 套餐内容 dao . 
 * Created on 2015-10-18 上午11:44:33 
 * @author 施长成
 * 
 */
public interface ContractDao extends BaseDao<Contract, Long> {

	/**
	 * 
	 * 根据运营商查询所有的套餐 .
	 * <br>
	 * author: 施长成
	 *   date: 2015-10-18 下午12:01:30
	 * @param operator
	 * @return
	 */
	List<Contract> findByOperator(Operator operator);


}
