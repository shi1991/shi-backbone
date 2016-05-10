package com.puyuntech.ycmall.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * 
 * 价格调整单. 
 * Created on 2015-11-20 下午4:16:05 
 * @author 王凯斌
 */
@Entity
@Table(name="t_cost_ajust")
public class CostAjust extends BaseEntity<Long> {
	
	private static final long serialVersionUID = -329847334887246608L;

	/** 商品  **/
	private Product product;
	
	/** 原价 */
	private BigDecimal organialCost;
	
	/** 申请价格 */
	private BigDecimal requestCost;
	
	/** 申请组织**/
	private Organization organization;
	
	/** 申请人 **/
	private Admin proposer;
	
	/** 申请人 **/
	private Admin operator;
	
	/** 申请时间 **/
	private Date applyDate;
	
	/** 变更时间 **/
	private Date ajustDate;
	
	/** 单据状态 **/
	private Boolean isInbill;
	
	/** 关联调价申请 **/
	private CostAjustRequisition costAjustRequisition;
	
	@ManyToOne
	@JoinColumn(name="f_product",nullable=false)
	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}
	
	@Column(name="f_organial_cost",nullable=false)
	public BigDecimal getOrganialCost() {
		return organialCost;
	}

	public void setOrganialCost(BigDecimal organialCost) {
		this.organialCost = organialCost;
	}

	@Column(name="f_request_cost",nullable=false)
	public BigDecimal getRequestCost() {
		return requestCost;
	}

	public void setRequestCost(BigDecimal requestCost) {
		this.requestCost = requestCost;
	}

	@ManyToOne
	@JoinColumn(name="f_organization",nullable=false)
	public Organization getOrganization() {
		return organization;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
	}

	@Column(name="f_apply_date")
	public Date getApplyDate() {
		return applyDate;
	}

	public void setApplyDate(Date applyDate) {
		this.applyDate = applyDate;
	}
	
	@Column(name="f_ajust_date",nullable=false)
	public Date getAjustDate() {
		return ajustDate;
	}

	public void setAjustDate(Date ajustDate) {
		this.ajustDate = ajustDate;
	}

	@ManyToOne
	@JoinColumn(name="f_proposer",nullable=false)
	public Admin getProposer() {
		return proposer;
	}

	public void setProposer(Admin proposer) {
		this.proposer = proposer;
	}

	@ManyToOne
	@JoinColumn(name="f_operator",nullable=false)
	public Admin getOperator() {
		return operator;
	}

	public void setOperator(Admin operator) {
		this.operator = operator;
	}

	@Column(name="f_is_inbill",nullable=false)
	public Boolean getIsInbill() {
		return isInbill;
	}

	public void setIsInbill(Boolean isInbill) {
		this.isInbill = isInbill;
	}

	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="f_cost_ajust_requisition_fk")
	public CostAjustRequisition getCostAjustRequisition() {
		return costAjustRequisition;
	}

	public void setCostAjustRequisition(CostAjustRequisition costAjustRequisition) {
		this.costAjustRequisition = costAjustRequisition;
	}

}
