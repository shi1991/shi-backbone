package com.puyuntech.ycmall.entity.value;

import java.math.BigDecimal;

/**
 * 
 * 将商品和促销商品 同时加入到购物 . 
 * Created on 2015-10-22 下午4:32:39 
 * @author 施长成
 */
public class CartItemBindProductValue {
	
	private Long id;
	
	private BigDecimal price;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public CartItemBindProductValue(Long id, BigDecimal price) {
		super();
		this.id = id;
		this.price = price;
	}

	public CartItemBindProductValue() {
		super();
	}
}
