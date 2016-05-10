package com.puyuntech.ycmall.entity;

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
 * Entity - 积分记录. 
 * Created on 2015-8-26 下午5:24:02 
 * @author Liaozhen
 */
@Indexed
@Entity
@Table(name = "t_point_log")
public class PointLog extends BaseEntity<Long> {

	private static final long serialVersionUID = 38194600348925811L;

	/**
	 * 类型
	 */
	public enum Type {

		/** 积分赠送 */
		reward,

		/** 积分兑换 */
		exchange,

		/** 积分兑换撤销 */
		undoExchange,

		/** 积分调整 */
		adjustment,
		
		/**红包获取 4*/
		 packetAcquisition,
		 
		 /**推荐购买商品5*/
		 commodityRecommendation,
		 
		 /**推荐注册6*/
		 registerRecommendation,
		 
		 /**注册7*/
		 register,
		 
		 /**登录8*/
		 login,
		 
		 /**订单支付9*/
		 payment,
		 
		 /**神币充值10*/
		 godMoneyRecharge,
		 
		 /**红包发放**/
		 bounsApply
		 
		 
		 
		 
	}
	
	/** 类型 */
	private PointLog.Type type;
	
	/** 获取积分 */
	private Long credit;
	
	/** 扣除积分 */
	private Long debit;
	
	/** 当前积分*/
	private Long balance;
	
	/** 操作员 */
	private String operator;
	
	/** 备注 */
	private String memo;

	/** 会员 */
	private Member member;
	
	@Column(name = "f_type", nullable = false)
	public PointLog.Type getType(){
		return type;
	}
	
	public void setType(PointLog.Type type){
		this.type = type;
	}
	
	@Column(name = "f_credit", nullable = false)
	public Long getCredit() {
		return credit;
	}

	public void setCredit(Long credit) {
		this.credit = credit;
	}

	@Column(name = "f_debit", nullable = false)
	public Long getDebit() {
		return debit;
	}

	public void setDebit(Long debit) {
		this.debit = debit;
	}

	@Column(name = "f_balance", nullable = false)
	public Long getBalance() {
		return balance;
	}

	public void setBalance(Long balance) {
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
