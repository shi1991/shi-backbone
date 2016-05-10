package com.puyuntech.ycmall.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/***
 * 
 * 调拨验收记录  . 
 * Created on 2015-8-28 下午3:18:46 
 * @author 施长成
 */
@Entity
@Table(name="t_allot_diff")
public class AllotDiff extends BaseEntity<Long> {

	private static final long serialVersionUID = -1154088462837694337L;

	/** 配送编号 **/
	private Allot allot ;
	
	/** 商品序列号 **/
	private String productSn;
	
	private Admin operator;
	
	private Boolean distributionState;
	
	private String memo;

	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="f_allot")
	public Allot getAllot() {
		return allot;
	}

	public void setAllot(Allot allot) {
		this.allot = allot;
	}

	@Column(name="f_product_sn",unique=false)
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
	
}
