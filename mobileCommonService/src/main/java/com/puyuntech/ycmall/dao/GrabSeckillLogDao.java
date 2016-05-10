package com.puyuntech.ycmall.dao;

import java.util.List;

import com.puyuntech.ycmall.entity.GrabSeckillLog;
import com.puyuntech.ycmall.vo.ResultVO;

/**
 * 
 * 抢购记录 . 
 * Created on 2015-10-20 下午6:31:44 
 * @author 严志森
 */
public interface GrabSeckillLogDao extends BaseDao<GrabSeckillLog, Long> {

	List<ResultVO> find(int type,String id);

	List<ResultVO> findByBonusId(String bonusId);
}
