package com.puyuntech.ycmall.service;

import java.util.List;

import com.puyuntech.ycmall.entity.GrabSeckill;
import com.puyuntech.ycmall.entity.GrabSeckill.GoodsTypeEnmu;
import com.puyuntech.ycmall.entity.GrabSeckill.GrabSecKillTypeEnmu;

/**
 * 
 * 抢购 Service . 
 * Created on 2015-11-10 下午1:47:58 
 * @author 南金豆
 */
public interface GrabSeckillService  extends BaseService<GrabSeckill, Long>{

	/**
	 * 
	 * 根据SN 数组 获取 秒杀表中存在的商品状态 .
	 * <p>方法详细说明,如果要换行请使用<br>标签</p>
	 * <br>
	 * author: 施长成
	 *   date: 2015-9-21 下午3:06:35
	 * @param products
	 * @param type  抢购类型  1.抢购 2.秒杀 
	 * @param goodsType 商品类型 1.商品 2.红包 3.优惠劵
	 * @return
	 */
	public List<GrabSeckill> listGrabSeckillBySns(String[] sns , GrabSeckill.GrabSecKillTypeEnmu type , GrabSeckill.GoodsTypeEnmu goodsType );

	/**
	 * 
	 * 根据单个 SN 获取 秒杀表中存在的商品状态 .
	 * <p>方法详细说明,如果要换行请使用<br>标签</p>
	 * <br>
	 * author: 施长成
	 *   date: 2015-9-21 下午3:06:35
	 * @param products
	 * @param type  抢购类型  1.抢购 2.秒杀 
	 * @param goodsType 商品类型 1.商品 2.红包 3.优惠劵
	 * @return
	 */
	public GrabSeckill listGrabSeckillBySn(String sn,
			GrabSecKillTypeEnmu seckill, GoodsTypeEnmu goods);
	
	/**
	 * 根据位置查询抢购秒杀.
	 * @author:严志森
	 * @date: 2016年4月6日 下午1:29:02 
	 * @param i 位置
	 * @return
	 */
	public GrabSeckill findByPosition(int i);

	

}
