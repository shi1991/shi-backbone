package com.puyuntech.ycmall.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.puyuntech.ycmall.dao.GrabSeckillLogDao;
import com.puyuntech.ycmall.entity.GrabSeckillLog;
import com.puyuntech.ycmall.service.GrabSeckillLogService;
import com.puyuntech.ycmall.vo.ResultVO;

/**
 * 
 * Service   抢购记录 . Created on 2015-10-20 下午6:47:35
 * 
 * @author 严志森
 */
@Service("grabSeckillLogServiceImpl")
public class GrabSeckillLogServiceImpl extends BaseServiceImpl<GrabSeckillLog, Long> implements
GrabSeckillLogService {

	@Resource(name = "grabSeckillLogDaoImpl")
	private GrabSeckillLogDao grabSeckillLogDao;


	@Override
	public List<ResultVO> find(int type,String id) {
		return grabSeckillLogDao.find(type,id);
	}


	@Override
	public List<ResultVO> findByBonusId(String bonusId) {
		return grabSeckillLogDao.findByBonusId(bonusId);
	}

}
