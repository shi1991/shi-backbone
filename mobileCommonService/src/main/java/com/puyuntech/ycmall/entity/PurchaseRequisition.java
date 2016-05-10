package com.puyuntech.ycmall.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 
 * 采购申请单 . 
 * Created on 2015-8-28 上午10:01:38 
 * @author 施长成
 */
@Entity
@Table(name="t_purchase_requisition")
public class PurchaseRequisition extends BaseEntity<Long> {

	private static final long serialVersionUID = -2552583071740406351L;

	/** 商品编号  **/
	private Product product;
	
	/** 采购数量 **/
	private int number;
	
	/** 申请组织**/
	private Organization organization;
	
	/** 申请人 **/
	private Integer proposer;
	
	/** 申请时间 **/
	private Date applyDate;
	
	/** 备注 **/
	private String memo;
	
	/** 申请状态 **/
	private int isDisponse;
	
	/** 单据状态 **/
	private byte billState;
	
	/** 预采购编号 **/
	private String prePurchaseNo;

	/** 关联采购单编号 **/
	private Purchase purchase;
	
	@ManyToOne
	@JoinColumn(name="f_product",nullable=false)
	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	@Column(name="f_number")
	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	@ManyToOne
	@JoinColumn(name="f_organization",nullable=false)
	public Organization getOrganization() {
		return organization;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
	}

	/**
	 * 
	 * 获得采购单.
	 * 
	 * @return
	 */
	@ManyToOne
	@JoinColumn(name="f_purchase")
	public Purchase getPurchase() {
		return purchase;
	}

	/**
	 * 
	 * 设置采购单.
	 * 
	 * @param purchase 采购单
	 */
	public void setPurchase(Purchase purchase) {
		this.purchase = purchase;
	}

	@Column(name="f_proposer")
	public Integer getProposer() {
		return proposer;
	}

	public void setProposer(Integer proposer) {
		this.proposer = proposer;
	}

	@Column(name="f_apply_date")
	public Date getApplyDate() {
		return applyDate;
	}

	public void setApplyDate(Date applyDate) {
		this.applyDate = applyDate;
	}

	@Column(name="f_memo")
	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	@Column(name="f_is_dispose")
	public int getIsDisponse() {
		return isDisponse;
	}

	public void setIsDisponse(int isDisponse) {
		this.isDisponse = isDisponse;
	}

	@Column(name="f_bill_state")
	public byte getBillState() {
		return billState;
	}

	public void setBillState(byte billState) {
		this.billState = billState;
	}
	
	@Column(name="f_pre_purchase_no")
	public String getPrePurchaseNo() {
		return prePurchaseNo;
	}

	public void setPrePurchaseNo(String prePurchaseNo) {
		this.prePurchaseNo = prePurchaseNo;
	}

	private PurchaseRequisition() {
		super();
	}
	
	
}
