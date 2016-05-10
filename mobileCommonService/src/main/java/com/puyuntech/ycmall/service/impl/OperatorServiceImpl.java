package com.puyuntech.ycmall.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.puyuntech.ycmall.dao.OperatorDao;
import com.puyuntech.ycmall.entity.Operator;
import com.puyuntech.ycmall.service.OperatorService;
import com.puyuntech.ycmall.vo.ResultVO;

/**
 * 
 * ServiceImpl - 运营商. 
 * Created on 2015-10-14 下午2:14:04 
 * @author 王凯斌
 */
@Service("operatorServiceImpl")
public class OperatorServiceImpl  extends BaseServiceImpl<Operator, Long> implements OperatorService {
	
	@Resource(name="operatorDaoImpl")
	private OperatorDao operatorDao;
	
	@Override
	public Operator findByName(String name) {
		return operatorDao.findByName(name);
	}

	@Override
	public List<ResultVO> findOther() {
		return operatorDao.findOther();
	}

	@Override
	public List<ResultVO> findBy() {
		return operatorDao.findBy();
	}

	@Override
	public List<Operator> findNoFei() {
		return operatorDao.findNoFei();
	}

}
