package com.puyuntech.ycmall.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 
 * 采购申请项目. 
 * Created on 2015-8-28 上午10:01:38 
 * @author 施长成
 */
@Entity
@Table(name="t_stock_trans_req_item")
public class StockTransReqItem extends BaseEntity<Long> {

	/** 商品  **/
	private Product product;
	
	/** 采购数量 **/
	private Integer quantity;
	
	/** 关联申请 **/
	private StockTransReq stockTransReq;
	
	@ManyToOne
	@JoinColumn(name="f_product",nullable=false)
	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	@Column(name="f_quantity")
	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	@ManyToOne
	@JoinColumn(name="f_stock_trans_req")
	public StockTransReq getStockTransReq() {
		return stockTransReq;
	}

	public void setStockTransReq(StockTransReq stockTransReq) {
		this.stockTransReq = stockTransReq;
	}

}
