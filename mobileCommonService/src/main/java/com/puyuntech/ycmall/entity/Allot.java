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
 * 调拨单 . Created on 2015-8-28 下午3:49:26
 * 
 * @author 施长成
 */
@Entity
@Table(name = "t_allot")
public class Allot extends BaseEntity<Long> {

	private static final long serialVersionUID = 8944083164418181898L;

	// 出发地
	private Organization fromOrganization;

	// 目的地
	private Organization toOrganization;

	//商品
	private Product product;
	
	// 操作人
	private Admin operator;

	// 操作时间
	private Date date;

	// 单据状态 0：未入账 1已入账2已拒绝
	private char billState;
	
	// 是否进行处理 0:未处理 1已处理
	private Boolean isDispose;

	// 备注
	private String memo;
	
	//调拨申请id外键
	private AllotRequisition allotRequisitionId;
	
	// 商品库存记录
	private Set<StockLog> stockLogs = new HashSet<StockLog>();
	
	// 
	private AllotCheck allotCheck;
	
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="f_from_organization")
	public Organization getFromOrganization() {
		return fromOrganization;
	}

	public void setFromOrganization(Organization fromOrganization) {
		this.fromOrganization = fromOrganization;
	}

	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="f_to_organization")
	public Organization getToOrganization() {
		return toOrganization;
	}

	public void setToOrganization(Organization toOrganization) {
		this.toOrganization = toOrganization;
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
	@JoinColumn(name="f_operator")
	public Admin getOperator() {
		return operator;
	}

	public void setOperator(Admin operator) {
		this.operator = operator;
	}
	
	@Column(name="f_is_dispose")
	public Boolean getIsDispose() {
		return isDispose;
	}

	public void setIsDispose(Boolean isDispose) {
		this.isDispose = isDispose;
	}
	
	@Column(name="f_date")
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@Column(name="f_bill_state")
	public char getBillState() {
		return billState;
	}

	public void setBillState(char billState) {
		this.billState = billState;
	}

	@Column(name="f_memo")
	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "t_allot_productsn", joinColumns = { @JoinColumn(name = "f_allot", referencedColumnName = "f_id") }, inverseJoinColumns = { @JoinColumn(name = "f_product_sn", referencedColumnName = "f_id") })
	public Set<StockLog> getStockLogs() {
		return stockLogs;
	}

	public void setStockLogs(Set<StockLog> stockLogs) {
		this.stockLogs = stockLogs;
	}
	
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="t_allot_requisition_id")
	public AllotRequisition getAllotRequisitionId() {
		return allotRequisitionId;
	}

	public void setAllotRequisitionId(AllotRequisition allotRequisitionId) {
		this.allotRequisitionId = allotRequisitionId;
	}
	
	@OneToOne(mappedBy="allot",fetch=FetchType.LAZY)
	public AllotCheck getAllotCheck() {
		return allotCheck;
	}

	public void setAllotCheck(AllotCheck allotCheck) {
		this.allotCheck = allotCheck;
	}
	
	
	

}
