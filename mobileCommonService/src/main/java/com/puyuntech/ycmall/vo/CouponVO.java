package com.puyuntech.ycmall.vo;

import java.math.BigDecimal;

import com.puyuntech.ycmall.entity.CouponCode;

/**
 * 
 *  获取有效优惠劵的临时 对象  . 
 * Created on 2015-10-30 下午1:53:27 
 * @author 施长成
 */
public class CouponVO {
	//优惠码
	private CouponCode couponCode;
	//商品ID
	private Long productId;
	//使用优惠劵最小ID
	private BigDecimal minPrice;

	public CouponCode getCouponCode() {
		return couponCode;
	}

	public void setCouponCode(CouponCode couponCode) {
		this.couponCode = couponCode;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public BigDecimal getMinPrice() {
		return minPrice;
	}

	public void setMinPrice(BigDecimal minPrice) {
		this.minPrice = minPrice;
	}
	
	

}
