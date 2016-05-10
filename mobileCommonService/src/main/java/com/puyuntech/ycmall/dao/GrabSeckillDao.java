package com.puyuntech.ycmall.dao;

import java.util.List;

import com.puyuntech.ycmall.entity.GrabSeckill;
import com.puyuntech.ycmall.entity.GrabSeckill.GoodsTypeEnmu;
import com.puyuntech.ycmall.entity.GrabSeckill.GrabSecKillTypeEnmu;

public interface GrabSeckillDao extends BaseDao<GrabSeckill, Long>{
	
	/**
	 * 
	 * 根据SN数组 获取 秒杀表中存在的商品状态 .
	 * <p>方法详细说明,如果要换行请使用<br>标签</p>
	 * <br>
	 * author: 施长成
	 *   date: 2015-9-21 下午3:06:35
	 * @param sns
	 * @param type  抢购类型  1.抢购 2.秒杀 
	 * @param goodsType 商品类型 1.商品 2.红包 3.优惠劵
	 * @return
	 */
	List<GrabSeckill> listGrabSeckillBySns(String[] sns,
			GrabSecKillTypeEnmu type, GoodsTypeEnmu goodsType);

	/**
	 * 
	 * 根据SN 获取 秒杀表中存在的商品状态 .
	 * <p>方法详细说明,如果要换行请使用<br>标签</p>
	 * <br>
	 * author: 施长成
	 *   date: 2015-9-21 下午3:06:35
	 * @param sns
	 * @param type  抢购类型  1.抢购 2.秒杀 
	 * @param goodsType 商品类型 1.商品 2.红包 3.优惠劵
	 * @return
	 */
	GrabSeckill listGrabSeckillBySn(String sn, GrabSecKillTypeEnmu type,
			GoodsTypeEnmu goodsType);

	GrabSeckill findByPosition(int i);

}
