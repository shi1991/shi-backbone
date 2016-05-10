package com.puyuntech.ycmall.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import com.puyuntech.ycmall.dao.GrabSeckillDao;
import com.puyuntech.ycmall.entity.GrabSeckill;
import com.puyuntech.ycmall.entity.GrabSeckill.GoodsTypeEnmu;
import com.puyuntech.ycmall.entity.GrabSeckill.GrabSecKillTypeEnmu;
import com.puyuntech.ycmall.service.GrabSeckillService;

@Service("grabSeckillServiceImpl")
public class GrabSeckillServiceImpl extends BaseServiceImpl<GrabSeckill, Long> implements GrabSeckillService{

	
	@Resource(name = "grabSeckillDaoImpl")
	private GrabSeckillDao grabSeckillDao;
	
	@Override
	public List<GrabSeckill> listGrabSeckillBySns(String[] sns,
			GrabSecKillTypeEnmu type, GoodsTypeEnmu goodsType) {
		return grabSeckillDao. listGrabSeckillBySns(sns, type, goodsType);
	}

	@Override
	public GrabSeckill listGrabSeckillBySn(String sn,
			GrabSecKillTypeEnmu type, GoodsTypeEnmu goodsType) {
		return grabSeckillDao. listGrabSeckillBySn(sn, type, goodsType);
	}
	
	
	@Override
	public GrabSeckill findByPosition(int i) {
		return grabSeckillDao.findByPosition(i);
	}
	

}
