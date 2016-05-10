package com.puyuntech.ycmall.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.puyuntech.ycmall.dao.ContractDao;
import com.puyuntech.ycmall.entity.Contract;
import com.puyuntech.ycmall.entity.Operator;
import com.puyuntech.ycmall.service.ContractService;

/**
 * 
 * 合约套餐. 
 * Created on 2015-10-18 上午11:42:07 
 * @author 施长成
 */
@Service("contractServiceImpl")
public class ContractServiceImpl extends BaseServiceImpl< Contract, Long> implements ContractService {

	@Resource(name = "contractDaoImpl")
	private ContractDao contractDao;

	public List<Contract> findByOperator(Operator operator){
		return contractDao.findByOperator(operator);
	}
	
	
	
	
}
