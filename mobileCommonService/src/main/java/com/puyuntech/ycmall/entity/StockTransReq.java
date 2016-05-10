package com.puyuntech.ycmall.entity;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * 
 * 配送验收 Entity . 
 * Created on 2015-8-28 下午3:01:10 
 * @author 施长成
 * 
 */
@Entity
@Table(name="t_stock_trans_req")
public class StockTransReq extends BaseEntity<Long> {
	
	//状态
		public enum Status {
			
			/** 未入账 */
			await,

			/** 已入账 */
			inBill,

			/** 已同意 */
			agree,
			
			/** 已拒绝 */
			disagree,
		}
	
	private String reqNo;
	
	private StockTransReq.Status status;
	
	private Admin operator;
	
	// 出发地
	private Organization fromOrganization;

	// 目的地
	private Organization toOrganization;
	
	private Date date;
	
	private String memo;
	
	/** 关联采购申请项 **/
	private Set<StockTransReqItem> stockTransReqItems;

	@OneToOne(fetch=FetchType.LAZY)
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

	@Column(name="f_status")
	public StockTransReq.Status getStatus() {
		return status;
	}

	public void setStatus(StockTransReq.Status status) {
		this.status = status;
	}

	@Column(name="f_memo")
	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	@Column(name="f_req_no")
	public String getReqNo() {
		return reqNo;
	}

	public void setReqNo(String reqNo) {
		this.reqNo = reqNo;
	}

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

	@OneToMany(mappedBy = "stockTransReq")
	public Set<StockTransReqItem> getStockTransReqItems() {
		return stockTransReqItems;
	}

	public void setStockTransReqItems(Set<StockTransReqItem> stockTransReqItems) {
		this.stockTransReqItems = stockTransReqItems;
	}
	
}
