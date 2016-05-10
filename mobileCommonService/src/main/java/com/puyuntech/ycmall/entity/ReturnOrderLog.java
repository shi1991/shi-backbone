package com.puyuntech.ycmall.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name = "t_return_order_log")
public class ReturnOrderLog extends BaseEntity<Long> {
	
	private static final long serialVersionUID = 3625604402946055518L;

	/**
	 * 类型
	 */
	public enum Type {

		/** 确认 0*/
		confirm,

		/** 处理 1*/
		deal,
		
		/** 完成 2**/
		complete,
		
	}
	
	private ReturnOrderLog.Type type;
	
	private Admin operator;
	
	private String content;
	
	private ReturnOrder returnOrder;
	
	@Column(name="f_type",nullable = false)
	public ReturnOrderLog.Type getType() {
		return type;
	}

	public void setType(ReturnOrderLog.Type type) {
		this.type = type;
	}

	@ManyToOne
	@JoinColumn(name = "f_operator")
	public Admin getOperator() {
		return operator;
	}

	public void setOperator(Admin operator) {
		this.operator = operator;
	}

	@Column(name="f_content")
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="f_return_order")
	public ReturnOrder getReturnOrder() {
		return returnOrder;
	}

	public void setReturnOrder(ReturnOrder returnOrder) {
		this.returnOrder = returnOrder;
	}
}
