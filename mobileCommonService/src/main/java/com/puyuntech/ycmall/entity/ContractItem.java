package com.puyuntech.ycmall.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;

import org.hibernate.validator.constraints.Length;
import org.springframework.core.annotation.Order;

/**
 * 
 * Entity - 套餐内容项 . 
 * Created on 2015-10-14 下午2:40:31 
 * @author 王凯斌
 *  update 施长成 rename
 */
@Entity
@Table(name = "t_contract_package")
public class ContractItem extends BaseEntity<Long> {

	private static final long serialVersionUID = 1176915090461012673L;

	/** 名称 */
//	private String name;
	
	/** 合约期 */
	private Integer lasttime;
	
	/** 每月花费 */
	private BigDecimal monthCost;
	
	/** 预存话费 */
	private BigDecimal telFare;
	
	/** 返还话费规则 */
	private String fareRules;
	
	/**套餐描述 */
	private String desc;
	
    /** 套餐是否有效 **/
    private Boolean state;
    
    /** 套餐名称 **/
    private Contract contract;
    
	/**
	 * 获取名称.
	 * 
	 * @return 名称
	 */
//	@Field(store = Store.YES, index = Index.YES, analyze = Analyze.YES)
//	@NotEmpty
//	@Length(max = 200)
//	@Column(name="f_name",nullable = false)
//	public String getName() {
//		return name;
//	}

	/**
	 * 设置名称.
	 * 
	 * @param name
	 *            名称
	 */
//	public void setName(String name) {
//		this.name = name;
//	}

	/**
	 * 
	 * 获取持续时间.
	 * 
	 * @return
	 */
	@Min(0)
	@Column(name="f_last_time",nullable = false)
	public Integer getLasttime() {
		return lasttime;
	}

	/**
	 * 设置持续时间.
	 * 
	 * @param lasttime
	 *            持续时间
	 */
	public void setLasttime(Integer lasttime) {
		this.lasttime = lasttime;
	}

	/**
	 * 获取每月花费.
	 * 
	 * @return 每月花费
	 */
	@Min(0)
	@Digits(integer = 12, fraction = 3)
	@Column(name="f_month_cost",precision = 21, scale = 6)
	@Order
	public BigDecimal getMonthCost() {
		return monthCost;
	}
	
	/**
	 * 设置每月花费.
	 * 
	 * @param monthCost
	 *            每月花费
	 */
	public void setMonthCost(BigDecimal monthCost) {
		this.monthCost = monthCost;
	}

	/**
	 * 获取预存话费.
	 * 
	 * @return 预存话费
	 */
	@Min(0)
	@Digits(integer = 12, fraction = 3)
	@Column(name="f_tel_fare",precision = 21, scale = 6)
	public BigDecimal getTelFare() {
		return telFare;
	}

	/**
	 * 设置预存话费.
	 * 
	 * @param telFare
	 *            预存话费
	 */
	public void setTelFare(BigDecimal telFare) {
		this.telFare = telFare;
	}

	/**
	 * 获取返还话费规则
	 * 
	 * @return 返还话费规则
	 */
	@Length(max = 200)
	@Column(name="f_fare_rules")
	public String getFareRules() {
		return fareRules;
	}

	/**
	 * 设置返还话费规则.
	 * 
	 * @param fareRules
	 *            返还规则
	 */
	public void setFareRules(String fareRules) {
		this.fareRules = fareRules;
	}

	/**
	 * 获取套餐描述
	 * 
	 * @return 套餐描述
	 */
	@Length(max = 200)
	@Column(name="f_desc")
	public String getDesc() {
		return desc;
	}

	/**
	 * 设置套餐描述.
	 * 
	 * @param desc
	 *            套餐描述
	 */
	public void setDesc(String desc) {
		this.desc = desc;
	}

	/**
	 * 获取套餐名称 .
	 * 
	 * @return 套餐名称
	 */
	@ManyToOne(fetch = FetchType.LAZY )
	@JoinColumn(name="f_contract" , nullable = false, updatable = true )
    public Contract getContract() {
		return contract;
	}

	/**
	 * 
	 * 设置套餐名称 .
	 * <br>
	 * author: 施长成
	 *   date: 2015-10-18 上午10:14:27
	 * @param contract
	 */
	public void setContract(Contract contract) {
		this.contract = contract;
	}

	/**
     * 获取套餐状态
     * @return
     */
    @Column(name="f_state" , nullable = false)
    public Boolean getState() {
        return state;
    }

    /**
     * 设置套餐状态
     * @param state
     */
    public void setState(Boolean state) {
        this.state = state;
    }
}
