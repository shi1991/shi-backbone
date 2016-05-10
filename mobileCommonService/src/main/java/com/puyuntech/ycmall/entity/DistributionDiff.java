package com.puyuntech.ycmall.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/***
 * 
 * 配送验收记录  . 
 * Created on 2015-8-28 下午3:18:46 
 * @author 施长成
 */
@Entity
@Table(name="t_distribution_diff")
public class DistributionDiff extends BaseEntity<Long> {
	
	private static final long serialVersionUID = 6500444243348180945L;

	/** 配送编号 **/
	private Distribution distribution ;
	
	/** 商品串号  **/
	private String productSn;
	
	private Admin operator;
	
	private Boolean distributionState;
	
	private String memo;

	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="f_distribution")
	public Distribution getDistribution() {
		return distribution;
	}

	public void setDistribution(Distribution distribution) {
		this.distribution = distribution;
	}

	@Column(name="f_product")
	public String getProductSn() {
		return productSn;
	}

	public void setProductSn(String productSn) {
		this.productSn = productSn;
	}

	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="f_operator")
	public Admin getOperator() {
		return operator;
	}

	public void setOperator(Admin operator) {
		this.operator = operator;
	}

	@Column(name="f_disposition_state")
	public Boolean getDistributionState() {
		return distributionState;
	}

	public void setDistributionState(Boolean distributionState) {
		this.distributionState = distributionState;
	}

	@Column(name="f_memo")
	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

//	private DistributionDiff() {
//		super();
//	}
	
}
