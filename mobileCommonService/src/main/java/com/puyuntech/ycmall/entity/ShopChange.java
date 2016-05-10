package com.puyuntech.ycmall.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

/**
 * 
 * 仓库退换记录 . Created on 2015-8-28 上午10:57:16
 * 
 * @author 施长成
 * 
 *         记录和商品之间是 多对多关系
 */
@Entity
@Table(name = "t_shop_chang")
public class ShopChange extends BaseEntity<Long> {

	private static final long serialVersionUID = 5988561381370008751L;

	// 出发地
	private String fromAddress;

	// 目的地
	private String toAddress;

	// 商品编号
	private Set<StockLog> stockLogs = new HashSet<StockLog>();

	// 操作人
	private String operator;

	// 操作时间
	private Date date;

	// 单据状态 0：未入账 1已入账
	private char billState;

	// 备注
	private String memo;

	@Column(name = "f_from_address")
	public String getFromAddress() {
		return fromAddress;
	}

	public void setFromAddress(String fromAddress) {
		this.fromAddress = fromAddress;
	}

	@Column(name = "f_to_address")
	public String getToAddress() {
		return toAddress;
	}

	public void setToAddress(String toAddress) {
		this.toAddress = toAddress;
	}

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "t_shop_chang_productsn", joinColumns = { @JoinColumn(name = "f_shop_chang", referencedColumnName = "f_id") }, inverseJoinColumns = { @JoinColumn(name = "f_product_sn", referencedColumnName = "f_id") })
	public Set<StockLog> getStockLogs() {
		return stockLogs;
	}

	public void setStockLogs(Set<StockLog> stockLogs) {
		this.stockLogs = stockLogs;
	}

	@Column(name = "operator")
	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	@Column(name = "date")
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@Column(name = "billState")
	public char getBillState() {
		return billState;
	}

	public void setBillState(char billState) {
		this.billState = billState;
	}

	@Column(name = "memo")
	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

}
