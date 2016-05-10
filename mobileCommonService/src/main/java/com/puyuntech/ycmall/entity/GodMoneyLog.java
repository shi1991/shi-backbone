package com.puyuntech.ycmall.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.search.annotations.Indexed;

/**
 * 
 * Entity - 神币记录. 
 * Created on 2015-8-26 下午5:01:52 
 * @author Liaozhen
 */
@Indexed
@Entity
@Table(name = "t_godMoney_log")
public class GodMoneyLog extends BaseEntity<Long> {

	private static final long serialVersionUID = 3292461598329423199L;

	/** 充值金额*/
	private BigDecimal credit;
	
	/** 消费神币 */
	private BigDecimal debit;
	
	/** 当前神币 */
	private BigDecimal balance;
	
	/** 操作员 */
	private String operator;
	
	/** 备注 */
	private String memo;
	
	/** 会员 */
	private Member member;
	
	/**
	 * 类型
	 */
	public enum Type {

		/** 神币充值 */
		recharge,

		/** 神币调整 */
		adjustment,

		/** 订单支付 */
		payment,

		/** 订单退款 */
		refunds,
		
		/** 积分支付 **/
		paymentPoint,
		
		/** 取消订单 **/
		cancelment,
		
		/**红包获取**/
		 packetAcquisition ,
		 
		 /** 商品推荐 **/
		 commodityRecommendation,
		 
		 /**红包发放**/
		 bounsApply
		
	}

	/** 类型 */
	private GodMoneyLog.Type type;
	
	@Column(name = "f_type", nullable = false)
	public GodMoneyLog.Type getType(){
		return type;
	}
	
	public void setType(GodMoneyLog.Type type){
		this.type = type;
	}

	@Column(name = "f_credit", nullable = false)
	public BigDecimal getCredit() {
		return credit;
	}

	public void setCredit(BigDecimal credit) {
		this.credit = credit;
	}

	@Column(name = "f_debit", nullable = false)
	public BigDecimal getDebit() {
		return debit;
	}

	public void setDebit(BigDecimal debit) {
		this.debit = debit;
	}

	@Column(name = "f_balance", nullable = false)
	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	@Column(name = "f_operator", nullable = true)
	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	@Column(name = "f_memo", nullable = true)
	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="f_member",nullable = false,updatable = false)
	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	/**
	 * 设置操作员
	 * 
	 * @param operator
	 *            操作员
	 */
	@Transient
	public void setOperator(Admin operator) {
		setOperator(operator != null ? operator.getWebUsername() : null);
	}
	
}
