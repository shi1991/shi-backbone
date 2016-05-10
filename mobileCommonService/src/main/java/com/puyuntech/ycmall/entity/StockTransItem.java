package com.puyuntech.ycmall.entity;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 
 * 库存变动项. Created on 2015-8-28 上午10:12:15
 * 
 * @author 施长成
 * 
 */
@Entity
@Table(name = "t_stock_trans_item")
public class StockTransItem extends BaseEntity<Long> {

	/** 商品编号 **/
	private Product product;

	/** 关联库存变动表 **/
	private StockTransLog stockTransLog;
	
	/** 库存记录 **/
	private Set<StockLog> stockLogs = new HashSet<StockLog>();
	
	@ManyToOne
	@JoinColumn(name = "f_product")
	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	@ManyToOne
	@JoinColumn(name = "f_stock_trans_log")
	public StockTransLog getStockTransLog() {
		return stockTransLog;
	}

	public void setStockTransLog(StockTransLog stockTransLog) {
		this.stockTransLog = stockTransLog;
	}

	@ManyToMany
	@JoinTable(name="t_stock_trans_productsn",joinColumns = {@JoinColumn(name = "t_stock_trans_item", referencedColumnName = "f_id") }, inverseJoinColumns = { @JoinColumn(name = "f_product_sn", referencedColumnName = "f_id") } )
	public Set<StockLog> getStockLogs() {
		return stockLogs;
	}

	public void setStockLogs(Set<StockLog> stockLogs) {
		this.stockLogs = stockLogs;
	}

	

}
