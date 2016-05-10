package com.puyuntech.ycmall.entity;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
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
@Table(name="t_stock_trans_check")
public class StockTransCheck extends BaseEntity<Long> {
	
	private String checkNo;
	
	private StockTransLog stockTransLog;
	
	private Admin operator;
	
	private Date date;
	
	private String memo;
	
	private Boolean isDifference;
	
	/** 库存变动项 **/
	private Set<StockTransCheckItem> stockTransCheckItems;
	
	@Column(name="f_check_no")
	public String getCheckNo() {
		return checkNo;
	}

	public void setCheckNo(String checkNo) {
		this.checkNo = checkNo;
	}

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


	@Column(name="f_memo")
	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	@Column(name="f_is_difference")
	public Boolean getIsDifference() {
		return isDifference;
	}

	public void setIsDifference(Boolean isDifference) {
		this.isDifference = isDifference;
	}

	@OneToOne
	@JoinColumn(name="f_stock_trans_log")
	public StockTransLog getStockTransLog() {
		return stockTransLog;
	}

	public void setStockTransLog(StockTransLog stockTransLog) {
		this.stockTransLog = stockTransLog;
	}

	@OneToMany(mappedBy = "stockTransCheck")
	public Set<StockTransCheckItem> getStockTransCheckItems() {
		return stockTransCheckItems;
	}

	public void setStockTransCheckItems(Set<StockTransCheckItem> stockTransCheckItems) {
		this.stockTransCheckItems = stockTransCheckItems;
	}

	
}
