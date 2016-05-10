package com.puyuntech.ycmall.entity;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * 
 * 库存变动单 . Created on 2016-2-08 下午3:49:26
 * 
 * @author 王凯斌
 */
@Entity
@Table(name = "t_stock_trans_log")
public class StockTransLog extends BaseEntity<Long> {

	//类型
	public enum Type {

		/** 配送 */
		Delivery,

		/** 调拨 */
		Allocation,

		/** 门店退货 */
		Return
	}
	
	//状态
	public enum Status {
		
		/** 未入账 */
		await,

		/** 已入账 */
		inBill,

		/** 已验收 */
		checked
	}
	
	/** 单号 **/
	private String logNo;
	
	//类型
	private StockTransLog.Type type;
	
	//状态
	private StockTransLog.Status status;
	
	// 出发地
	private Organization fromOrganization;

	// 目的地
	private Organization toOrganization;
	
	// 操作人
	private Admin operator;

	// 入账时间
	private Date date;

	// 备注
	private String memo;
	
	/** 库存变动项 **/
	private Set<StockTransItem> stockTransItems;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="f_from_organization")
	public Organization getFromOrganization() {
		return fromOrganization;
	}

	public void setFromOrganization(Organization fromOrganization) {
		this.fromOrganization = fromOrganization;
	}

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="f_to_organization")
	public Organization getToOrganization() {
		return toOrganization;
	}

	public void setToOrganization(Organization toOrganization) {
		this.toOrganization = toOrganization;
	}

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="f_operator")
	public Admin getOperator() {
		return operator;
	}

	public void setOperator(Admin operator) {
		this.operator = operator;
	}
	
	@Column(name="f_date")
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}


	@Column(name="f_memo")
	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	@Column(name="f_log_no")
	public String getLogNo() {
		return logNo;
	}

	public void setLogNo(String logNo) {
		this.logNo = logNo;
	}

	@Column(name="f_type")
	public StockTransLog.Type getType() {
		return type;
	}

	public void setType(StockTransLog.Type type) {
		this.type = type;
	}

	@Column(name="f_status")
	public StockTransLog.Status getStatus() {
		return status;
	}

	public void setStatus(StockTransLog.Status status) {
		this.status = status;
	}

	@OneToMany(mappedBy = "stockTransLog")
	public Set<StockTransItem> getStockTransItems() {
		return stockTransItems;
	}

	public void setStockTransItems(Set<StockTransItem> stockTransItems) {
		this.stockTransItems = stockTransItems;
	}

	
}
