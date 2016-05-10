package com.puyuntech.ycmall.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.search.annotations.Indexed;

/**
 * 
 * Entity - 积分记录. 
 * Created on 2015-8-26 下午5:24:02 
 * @author Liaozhen
 */
@Indexed
@Entity
@Table(name = "t_experience_log")
public class ExperienceLogEntity extends BaseEntity<Long> {

	private static final long serialVersionUID = 38194600348925811L;

	/** 获取经验值 */
	private Long credit;
	
	/** 扣除经验值 */
	private Long debit;
	
	/** 当前经验值 */
	private Long balance;
	
	/** 操作员 */
	private String operator;
	
	/** 备注 */
	private String memo;
	
	/** 会员 */
	private Member member;
	
	/** 类型 */
	private Integer type;
	
	public void setType(Integer type){
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
	
	@Column(name = "f_type", nullable = false)
	public Integer getType(){
		return type;
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
	
}
