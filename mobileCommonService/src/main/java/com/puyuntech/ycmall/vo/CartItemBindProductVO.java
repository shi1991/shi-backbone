package com.puyuntech.ycmall.vo;

import java.math.BigDecimal;

import com.puyuntech.ycmall.entity.Product;

/**
 * 
 * 购物车商品中的 . Created on 2015-10-22 下午6:23:00
 * 
 * @author 施长成
 */
public class CartItemBindProductVO {

	/**
	 * 商品
	 */
	private Product product;

	/**
	 * 价格
	 */
	private BigDecimal price;

	/** 数量 */
	private Integer bindQuantity;

	/** 总金额 **/
	private BigDecimal bindTotalPrice;

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public Integer getBindQuantity() {
		return bindQuantity;
	}

	public void setBindQuantity(Integer bindQuantity) {
		this.bindQuantity = bindQuantity;
	}

	public BigDecimal getBindTotalPrice() {
		return bindTotalPrice;
	}

	public void setBindTotalPrice(BigDecimal bindTotalPrice) {
		this.bindTotalPrice = bindTotalPrice;
	}

}
