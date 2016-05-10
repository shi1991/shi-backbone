package com.puyuntech.ycmall.dao;

import java.util.List;

import com.puyuntech.ycmall.entity.Operator;
import com.puyuntech.ycmall.vo.ResultVO;

/**
 * 
 * 运营商DAO. Created on 2015-10-12 下午6:05:29
 * 
 * @author 王凯斌
 */
public interface OperatorDao extends BaseDao<Operator, Long> {

	Operator findByName(String name);

	List<ResultVO> findOther();

	List<ResultVO> findBy();

	List<Operator> findNoFei();

}
