package com.puyuntech.ycmall.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 
 * Entity - 返利 . 
 * Created on 2015-10-19 下午3:45:17 
 * @author 严志森
 * 	
 */
@Entity
@Table(name = "t_rebates")
public class Rebates extends BaseEntity<Long> {

	private static final long serialVersionUID = 4574620064434753973L;

	public enum Type{
		//其他
		other , 
		//神币
		god ,
		//积分
		point
	}
	
	/** 时间点*/
	private Date time;
	
	/** 获利用户 */
	private Member member;
	
	/** 获利种类 1：神币 	2：积分**/
	
	private Rebates.Type rebatesSpecies;
	
	/** 获利额度 */
	private Long rebatesCredits;
	
	/** 获利类型 1:注册，2:购买商品**/
	private char rebatesType;
	
	/**
	 * 获取时间点
	 * 
	 * @return 时间点
	 */
	@Column(name="f_time",nullable = false)
	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="f_member",nullable = false, updatable = false)
	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}
	
	/**
	 * 获取 获利种类 1：神币 2：积分
	 * 
	 * @return 获利种类 1：神币 2：积分
	 */
	@Column(name="f_rebates_species")
	public Rebates.Type getRebatesSpecies() {
		return rebatesSpecies;
	}
	
	
	public void setRebatesSpecies(Rebates.Type rebatesSpecies) {
		this.rebatesSpecies = rebatesSpecies;
	}
	
	/**
	 * 获取获利额度
	 * 
	 * @return 获利额度
	 */
	@Column(name="f_rebates_credits",nullable = false)
	public Long getRebatesCredits() {
		return rebatesCredits;
	}

	public void setRebatesCredits(Long rebatesCredits) {
		this.rebatesCredits = rebatesCredits;
	}
	
	/**
	 * 获取 获利类型 1:注册，2:购买商品
	 * 
	 * @return 获利类型 1:注册，2:购买商品
	 */
	@Column(name="f_rebates_type")
	public char getRebatesType() {
		return rebatesType;
	}

	public void setRebatesType(char rebatesType) {
		this.rebatesType = rebatesType;
	}
	
}
