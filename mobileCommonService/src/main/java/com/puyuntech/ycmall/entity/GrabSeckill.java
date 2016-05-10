package com.puyuntech.ycmall.entity;

import javax.persistence.*;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 
 * 抢购 秒杀. 
 * Created on 2015-8-28 下午5:26:59 
 * @author 施长成
 */
@Entity
@Table(name="t_grab_seckill" )
public class GrabSeckill extends BaseEntity<Long>{
	
	private static final long serialVersionUID = 2933869725469459370L;

	/**
	 * 
	 * 抢购类型 . 
	 * Created on 2015-9-21 上午11:31:21 
	 * @author 施长成
	 */
	public enum GrabSecKillTypeEnmu {
		
		OTHER, 
		
		//抢购
		GRAB, 
		
		//秒杀
		SECKILL,

	}

	/**
	 * 
	 * 商品类型 . 
	 * Created on 2015-9-21 上午11:31:42 
	 * @author 施长成
	 */
	public enum GoodsTypeEnmu {
		
		OTHER, 
		
		//商品
		GOODS,
		
		//红包
		BONUS,
		
		//优惠券
		COUPONS,
	}
	
	/**
	 * 
	 * 活动状态  . 
	 * Created on 2015-9-21 上午11:31:42 
	 * @author 施长成
	 */
	public enum StateEnmu {
		
		OTHER, 
		
		//进行中
		DOING, 
		
		//结束
		DONE,
		
		//未开始
		pretend,
	}

	private GrabSecKillTypeEnmu  type;
	
	private String goods;
	
	private GoodsTypeEnmu goodsTypes;
	
	private Integer goodsGross;
	
	private Integer goodsResidue;

    /* 开始时间 */
	private Date startTime;
	
	private Date endTime;
	
	private StateEnmu state;
	
	private BigDecimal price;
	
	private String operator;

	private String title;

    /* 秒杀展示图片 */
	private String image;

    /*内容*/
	private String content;

    /*位置*/
	private String position;
	
	private Date nowDate;
	
	private Boolean isRemove;

	//TODO 相关红包 ，未在红包处添加对应属性
	private BonusEntity bonus;
	
	//相关商品 ，未在商品处添加对应属性
	private Product product;
	
	//相关优惠券 ，未在优惠券处添加对应属性
	private Coupon coupon;

    /**
     * 2016年1月24日 12:27:01
     *  新增的 抢购的字段
     */
    /* APP抢购 左边图片 */
    private String leftImg;

    /* APP抢购 右边图片 */
    private String rightImg;
	
	@Enumerated(EnumType.ORDINAL)
	@Column(name="f_type")
	public GrabSecKillTypeEnmu getType() {
		return type;
	}

	public void setType(GrabSecKillTypeEnmu type) {
		this.type = type;
	}


	@Column(name="f_goods")
	public String getGoods() {
		return goods;
	}

	public void setGoods(String goods) {
		this.goods = goods;
	}

	@Column(name="f_goods_type")
	public GoodsTypeEnmu getGoodsTypes() {
		return goodsTypes;
	}

	public void setGoodsTypes(GoodsTypeEnmu goodsTypes) {
		this.goodsTypes = goodsTypes;
	}

	@Column(name="f_goods_gross",precision=11)
	public Integer getGoodsGross() {
		return goodsGross;
	}

	public void setGoodsGross(Integer goodsGross) {
		this.goodsGross = goodsGross;
	}

	@Column(name="f_goods_residue",precision=11 )
	public Integer getGoodsResidue() {
		return goodsResidue;
	}

	public void setGoodsResidue(Integer goodsResidue) {
		this.goodsResidue = goodsResidue;
	}

	@Column(name = "f_start_time" )
	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	@Column(name="f_end_time")
	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	@Column(name="f_state")
	public StateEnmu getState() {
		return state;
	}

	public void setState(StateEnmu state) {
		this.state = state;
	}

	@Column(name="f_price")
	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	@Column(name="f_operator")
	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	@Column(name="f_title" )
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name="f_image" )
	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	@Column(name="f_content")
	public String getContent() {
		return content;
	}


	public void setContent(String content) {
		this.content = content;
	}

	@Column(name="f_position")
	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}
	
	@Transient
	public Date getNowDate() {
		return new Date();
	}

	public void setNowDate(Date nowDate) {
		nowDate =  new Date();
		this.nowDate = nowDate;
	}

	public GrabSeckill() {
		super();
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="f_bonus")
	public BonusEntity getBonus() {
		return bonus;
	}

	public void setBonus(BonusEntity bonus) {
		this.bonus = bonus;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="f_product")
	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="f_coupon")
	public Coupon getCoupon() {
		return coupon;
	}

	public void setCoupon(Coupon coupon) {
		this.coupon = coupon;
	}

    @Column(name="f_left_img")
    public String getLeftImg() {
        return leftImg;
    }

    public void setLeftImg(String leftImg) {
        this.leftImg = leftImg;
    }

    @Column(name="f_right_img")
    public String getRightImg() {
        return rightImg;
    }

    public void setRightImg(String rightImg) {
        this.rightImg = rightImg;
    }

    @Column(name="f_is_remove")
    public Boolean getIsRemove() {
		return isRemove;
	}

	public void setIsRemove(Boolean isRemove) {
		this.isRemove = isRemove;
	}
    
    /**
	 * 
	 * 判断某一个商品当前的秒杀状态 .
	 * <p>方法详细说明,如果要换行请使用<br>标签</p>
	 * <br>
	 * author: 施长成
	 *   date: 2015-9-22 下午3:03:45
	 * @return
	 * 		false 表示 已经停止
	 * 		true 表示 还在进行中
	 */
	@Transient
	public boolean isSecKillStatus() {
		if(StateEnmu.DONE == getState() || getGoodsResidue() <1 || getNowDate().after(getEndTime()) || getStartTime().after(getNowDate()) ){
			return false;
		}else{
			return true;
		}
	}
	
	
}
