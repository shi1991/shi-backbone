package com.puyuntech.ycmall.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 
 * 配送验收 Entity . 
 * Created on 2015-8-28 下午3:01:10 
 * @author 施长成
 * 
 */
@Entity
@Table(name="t_stock_trans_diff")
public class StockTransDiff extends BaseEntity<Long> {
	
	public enum Status{
		//等待处理
		await,
		//补充入库
		check,
		//丢失
		loss
		
	}

	private StockTransCheck stockTransCheck;
	
	private StockLog stockLog;
	
	private Admin operator;
	
	private StockTransDiff.Status status;
	

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="f_operator")
	public Admin getOperator() {
		return operator;
	}

	public void setOperator(Admin operator) {
		this.operator = operator;
	}

	@ManyToOne
	@JoinColumn(name="f_stock_trans_check")
	public StockTransCheck getStockTransCheck() {
		return stockTransCheck;
	}

	public void setStockTransCheck(StockTransCheck stockTransCheck) {
		this.stockTransCheck = stockTransCheck;
	}

	@ManyToOne
	@JoinColumn(name="f_stock_log")
	public StockLog getStockLog() {
		return stockLog;
	}

	public void setStockLog(StockLog stockLog) {
		this.stockLog = stockLog;
	}

	@Column(name="f_status")
	public StockTransDiff.Status getStatus() {
		return status;
	}

	public void setStatus(StockTransDiff.Status status) {
		this.status = status;
	}

	
}
