package com.puyuntech.ycmall.service;

import java.util.List;

import com.puyuntech.ycmall.entity.Operator;
import com.puyuntech.ycmall.vo.ResultVO;

/**
 * 
 * Service - 运营商. 
 * Created on 2015-10-14 下午1:54:24 
 * @author 王凯斌
 */
public interface OperatorService extends BaseService<Operator, Long>{

	Operator findByName(String name);

	List<ResultVO> findOther();

	List<ResultVO> findBy();
	
	List<Operator> findNoFei();

}
