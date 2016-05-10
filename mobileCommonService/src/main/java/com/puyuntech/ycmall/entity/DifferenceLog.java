package com.puyuntech.ycmall.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 
 *  差异商品记录  . 
 * Created on 2015-8-28 下午4:08:15 
 * @author 施长成
 * 
 */

@Entity
@Table(name="t_difference_log")
public class DifferenceLog extends BaseEntity<Long>{

	private static final long serialVersionUID = -681624786280015786L;

	private String productSn;
	
	private Integer distribution;
	
	/** 配送缺少/增多 0:缺少 1：增加  **/
	private char distributionMoreLess;
	
	private char dispositionState;
	
	private char allot;
	
	private char allotMoreLess;
	
	private char allotState;

	@Column(name="f_product_sn")
	public String getProductSn() {
		return productSn;
	}

	public void setProductSn(String productSn) {
		this.productSn = productSn;
	}

	@Column(name="f_distribution")
	public Integer getDistribution() {
		return distribution;
	}

	public void setDistribution(Integer distribution) {
		this.distribution = distribution;
	}

	@Column(name="f_distribution_more_less")
	public char getDistributionMoreLess() {
		return distributionMoreLess;
	}

	public void setDistributionMoreLess(char distributionMoreLess) {
		this.distributionMoreLess = distributionMoreLess;
	}

	@Column(name="f_disposition_state")
	public char getDispositionState() {
		return dispositionState;
	}

	public void setDispositionState(char dispositionState) {
		this.dispositionState = dispositionState;
	}

	@Column(name="'f_allot")
	public char getAllot() {
		return allot;
	}

	public void setAllot(char allot) {
		this.allot = allot;
	}

	@Column(name="f_allot_more_less")
	public char getAllotMoreLess() {
		return allotMoreLess;
	}

	public void setAllotMoreLess(char allotMoreLess) {
		this.allotMoreLess = allotMoreLess;
	}

	@Column(name="f_allot_state")
	public char getAllotState() {
		return allotState;
	}

	public void setAllotState(char allotState) {
		this.allotState = allotState;
	}

	private DifferenceLog() {
		super();
	}
	
	
}
