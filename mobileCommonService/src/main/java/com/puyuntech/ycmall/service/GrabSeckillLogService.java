package com.puyuntech.ycmall.service;

import java.util.List;

import com.puyuntech.ycmall.entity.GrabSeckillLog;
import com.puyuntech.ycmall.vo.ResultVO;

/**
 * 
 * 抢购记录 Service . 
 * Created on 2015-10-20 下午6:44:20 
 * @author 严志森
 */
public interface GrabSeckillLogService extends BaseService<GrabSeckillLog, Long> {
	
	
	List<ResultVO> find(int type,String id);

	List<ResultVO> findByBonusId(String bonusId);
}
