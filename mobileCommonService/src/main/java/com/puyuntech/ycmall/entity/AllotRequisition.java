package com.puyuntech.ycmall.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/***
 * 
 * 调拨申请 —— 调货申请表 . 
 * Created on 2015-8-28 下午3:24:33 
 * @author 施长成
 */
@Entity
@Table(name="t_allot_requisition")
public class AllotRequisition extends BaseEntity<Long>{

	private static final long serialVersionUID = 6636347857132580677L;

	/** 与商品对象 一对一的关系 **/
	private Product product;
	
	private int number;
	
	private Organization fromOrganization;
	
	private Admin proposer;
	
	private Date applyDate;
	
	private String memo;
	
	private Organization toOrganization;
	
	private char isDispose;
	
	private Boolean billState;
	
	private String bathc;


	@OneToOne
	@JoinColumn(name="f_product")
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

	@OneToOne
	@JoinColumn(name="f_from_organization")
	public Organization getFromOrganization() {
		return fromOrganization;
	}

	public void setFromOrganization(Organization fromOrganization) {
		this.fromOrganization = fromOrganization;
	}

	@OneToOne
	@JoinColumn(name="f_proposer")
	public Admin getProposer() {
		return proposer;
	}

	public void setProposer(Admin proposer) {
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

	@OneToOne
	@JoinColumn(name="f_to_organization")
	public Organization getToOrganization() {
		return toOrganization;
	}

	public void setToOrganization(Organization toOrganization) {
		this.toOrganization = toOrganization;
	}

	@Column(name="f_is_dispose")
	public char getIsDispose() {
		return isDispose;
	}

	public void setIsDispose(char isDispose) {
		this.isDispose = isDispose;
	}

	@Column(name="f_bill_state")
	public Boolean getBillState() {
		return billState;
	}

	public void setBillState(Boolean billState) {
		this.billState = billState;
	}

	@Column(name="f_batch" )
	public String getBathc() {
		return bathc;
	}

	public void setBathc(String bathc) {
		this.bathc = bathc;
	}
	
}
