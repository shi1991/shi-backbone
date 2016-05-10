package com.puyuntech.ycmall.entity;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PreRemove;
import javax.persistence.Table;

/**
 * Entity - 优惠码 . 
 * Created on 2015-8-27 下午3:08:43 
 * @author 施长成
 */
@Entity
@Table(name = "t_coupon_code")
public class CouponCode extends BaseEntity<Long> {

	private static final long serialVersionUID = -8236930317127141217L;

	/** 号码 */
	private String code;

	/** 是否已使用 */
	private Boolean isUsed;

	/** 使用日期 */
	private Date usedDate;

	/** 优惠券 */
	private Coupon coupon;

	/** 会员 */
	private Member member;

	/** 订单 */
	private Order order;
	
	/**
	 * 获取号码
	 * 
	 * @return 号码
	 */
	@Column(name="f_code",nullable = false, updatable = false, unique = true)
	public String getCode() {
		return code;
	}
	

	/**
	 * 设置号码
	 * 
	 * @param code
	 *            号码
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * 获取是否已使用
	 * 
	 * @return 是否已使用
	 */
	@Column(name="f_is_used",nullable = false)
	public Boolean getIsUsed() {
		return isUsed;
	}

	/**
	 * 设置是否已使用
	 * 
	 * @param isUsed
	 *            是否已使用
	 */
	public void setIsUsed(Boolean isUsed) {
		this.isUsed = isUsed;
	}

	/**
	 * 获取使用日期
	 * 
	 * @return 使用日期
	 */
	@Column(name="f_used_used")
	public Date getUsedDate() {
		return usedDate;
	}

	/**
	 * 设置使用日期
	 * 
	 * @param usedDate
	 *            使用日期
	 */
	public void setUsedDate(Date usedDate) {
		this.usedDate = usedDate;
	}

	/**
	 * 获取优惠券
	 * 
	 * @return 优惠券
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="f_coupon",nullable = false, updatable = false)
	public Coupon getCoupon() {
		return coupon;
	}

	/**
	 * 设置优惠券
	 * 
	 * @param coupon
	 *            优惠券
	 */
	public void setCoupon(Coupon coupon) {
		this.coupon = coupon;
	}

	/**
	 * 获取会员
	 * 
	 * @return 会员
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="f_member")
	public Member getMember() {
		return member;
	}
	
	/**
	 * 设置会员
	 * 
	 * @param member
	 *            会员
	 */
	public void setMember(Member member) {
		this.member = member;
	}

	/**
	 * 获取订单
	 * 
	 * @return 订单
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "f_orders")
	public Order getOrder() {
		return order;
	}

	/**
	 * 设置订单
	 * 
	 * @param order
	 *            订单
	 */
	public void setOrder(Order order) {
		this.order = order;
	}

	/**
	 * 删除前处理
	 */
	@PreRemove
	public void preRemove() {
	/*	if (getOrder() != null) {
			getOrder().setCouponCode(null);
		}*/
	}

}
