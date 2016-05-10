package com.puyuntech.ycmall.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 
 *  红包记录 . 
 * Created on 2015-8-28 下午4:25:05 
 * @author 施长成
 */
@Entity
@Table(name="t_bonus_log")
public class BonusLog extends BaseEntity<Long> {

	private static final long serialVersionUID = 1914807244176326061L;
	
	private BonusEntity bonus;
	
	private String bonusSn;
	
	private Member member;
	
	/**
	 * 兑换状态
	 * 0 未兑换
	 * 1 兑换结束
	 */
	private char exchangeState;
	
	private Date acquireTime;
	
	/**兑换时间*/
	private Date exchangeTime;
	
	/**额度*/
	private BigDecimal credits;

	
	@ManyToOne(fetch = FetchType.LAZY) 
	@JoinColumn(name="f_bonus",referencedColumnName ="f_id")
	public BonusEntity getBonus() {
		return bonus;
	}

	public void setBonus(BonusEntity bonus) {
		this.bonus = bonus;
	}

	@Column(name="f_bonus_sn")
	public String getBonusSn() {
		return bonusSn;
	}

	public void setBonusSn(String bonusSn) {
		this.bonusSn = bonusSn;
	}

	@ManyToOne(fetch = FetchType.LAZY) 
	@JoinColumn(name="f_member")
	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	@Column(name="f_exchange_state")
	public char getExchangeState() {
		return exchangeState;
	}

	public void setExchangeState(char exchangeState) {
		this.exchangeState = exchangeState;
	}

	public Date getAcquireTime() {
		return acquireTime;
	}

	public void setAcquireTime(Date acquireTime) {
		this.acquireTime = acquireTime;
	}

	@Column(name="f_exchange_time")
	public Date getExchangeTime() {
		return exchangeTime;
	}

	public void setExchangeTime(Date exchangeTime) {
		this.exchangeTime = exchangeTime;
	}

	@Column(name="f_credits")
	public BigDecimal getCredits() {
		return credits;
	}

	public void setCredits(BigDecimal credits) {
		this.credits = credits;
	}
	
}
