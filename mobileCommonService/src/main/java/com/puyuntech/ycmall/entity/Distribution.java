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
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * 
 * 配送单 . Created on 2015-8-28 下午2:01:22
 * 
 * @author 施长成
 * 
 */
@Entity
@Table(name = "t_distribution")
public class Distribution extends BaseEntity<Long> {

	private static final long serialVersionUID = 2191472350931663690L;

	// 出发地
	private Organization fromAddress;

	// 目的地
	private Organization toAddress;

	//商品
	private Product product;
	
	// 商品库存记录
	private Set<StockLog> stockLogs = new HashSet<StockLog>();

	// 操作人
	private Admin operator;
	
	//数量
	private int number;

	// 操作时间
	private Date date;

	// 单据状态 0：未入账 1已入账
	private Boolean billState;

	// 备注
	private String memo;
	
	// 
	private DistributionCheck distributionCheck;

	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "f_from_address")
	public Organization getFromAddress() {
		return fromAddress;
	}

	public void setFromAddress(Organization fromAddress) {
		this.fromAddress = fromAddress;
	}

	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "f_to_address")
	public Organization getToAddress() {
		return toAddress;
	}

	public void setToAddress(Organization toAddress) {
		this.toAddress = toAddress;
	}

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "t_distribution_productsn", joinColumns = { @JoinColumn(name = "f_distribution", referencedColumnName = "f_id") }, inverseJoinColumns = { @JoinColumn(name = "f_product_sn", referencedColumnName = "f_id") })
	public Set<StockLog> getStockLogs() {
		return stockLogs;
	}

	public void setStockLogs(Set<StockLog> stockLogs) {
		this.stockLogs = stockLogs;
	}
	
	@Column(name="f_number")
	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}
	
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="f_product")
	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}
	
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "f_operator")
	public Admin getOperator() {
		return operator;
	}

	public void setOperator(Admin operator) {
		this.operator = operator;
	}

	@Column(name = "f_date")
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@Column(name = "f_bill_state")
	public Boolean getBillState() {
		return billState;
	}

	public void setBillState(Boolean billState) {
		this.billState = billState;
	}

	@Column(name = "f_memo")
	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}
	@OneToOne(mappedBy="distribution",fetch=FetchType.LAZY)
	public DistributionCheck getDistributionCheck() {
		return distributionCheck;
	}

	public void setDistributionCheck(DistributionCheck distributionCheck) {
		this.distributionCheck = distributionCheck;
	}
	
	

//	private Distribution() {
//		super();
//	}

}
