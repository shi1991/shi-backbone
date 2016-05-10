package com.puyuntech.ycmall.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 
 * 抢购 秒杀记录. 
 * Created on 2015-10-20 下午5:26:59 
 * @author 严志森
 */
@Entity
@Table(name="t_grab_seckill_log" )
public class GrabSeckillLog extends BaseEntity<Long>{
	
	private static final long serialVersionUID = 2841441447947508245L;

	/**
	 * 
	 * 抢购类型 . 
	 * Created on 2015-9-21 上午11:31:21 
	 * @author 施长成
	 */
	public enum GrabSecKillTypeEnmu {
		OTHER("0", "其他"), GRAB("1", "抢购"), SECKILL("2", "秒杀");
		private String id;
		private String name;

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		private GrabSecKillTypeEnmu() {
		}

		private GrabSecKillTypeEnmu(String id, String name) {
			this.id = id;
			this.name = name;
		}

	}

	/**
	 * 
	 * 商品类型 . 
	 * Created on 2015-9-21 上午11:31:42 
	 * @author 施长成
	 */
	public enum GoodsTypeEnmu {
		OTHER("0", "其他"), GOODS("1", "商品"), BONUS("2", "红包"), COUPONS("3",
				"优惠劵");
		private String id;
		private String name;

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		private GoodsTypeEnmu(String id, String name) {
			this.id = id;
			this.name = name;
		}
	}
	
	/** 类型 */
	private GrabSecKillTypeEnmu  type;
	
	
	/**  商品类型 */
	private GoodsTypeEnmu goodsTypes;
	
	/**  价格 */
	private BigDecimal price;
	
	/** 时间点 */
	private Date datetime;
	
	/** 会员 */
	private Member member;
	
	/** 订单 */
	private Order order;
	
	/** 物品编号 */
	private String goods;
	
	/** 抢购 */
	private GrabSeckill grabSeckills;
	

	@Enumerated(EnumType.ORDINAL)
	@Column(name="f_type",nullable = false)
	public GrabSecKillTypeEnmu getType() {
		return type;
	}

	public void setType(GrabSecKillTypeEnmu type) {
		this.type = type;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="t_order",nullable = true, updatable = false)
	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}
	
	@Column(name="f_goods",nullable = true)
	public String getGoods() {
		return goods;
	}

	public void setGoods(String goods) {
		this.goods = goods;
	}


	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="f_member",nullable = false, updatable = false)
	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	@Column(name="f_goods_type",nullable = false)
	public GoodsTypeEnmu getGoodsTypes() {
		return goodsTypes;
	}

	public void setGoodsTypes(GoodsTypeEnmu goodsTypes) {
		this.goodsTypes = goodsTypes;
	}
	
	@Column(name="f_price",nullable = false)
	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	
	/**
	 * 获取时间点
	 * 
	 * @return 时间点
	 */
	@Column(name="f_datetime",nullable = false)
	public Date getDatetime() {
		return datetime;
	}

	public void setDatetime(Date datetime) {
		this.datetime = datetime;
	}
	
	/**
	 * 获取抢购id
	 * 
	 * @return 抢购
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="f_grab_Seckills",nullable = false)
	public GrabSeckill getGrabSeckills() {
		return grabSeckills;
	}

	public void setGrabSeckills(GrabSeckill grabSeckills) {
		this.grabSeckills = grabSeckills;
	}
	
	
	

	


	
	
	

	
	
	
}
