package com.puyuntech.ycmall.entity;

import java.math.BigInteger;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * 
 * 采购单. Created on 2015-8-28 上午10:12:15
 * 
 * @author 施长成
 * 
 *         各门店 或者 运维人员 提出预采购申请， 管理人员 根据预采购单 合并整理 出一份 正式采购单
 */
@Entity
@Table(name = "t_purchase")
public class Purchase extends BaseEntity<Long> {

	private static final long serialVersionUID = 6673124998495602807L;

	/** 商品编号 **/
	private Product product;

	/** 采购数量 **/
	private int number;

	/** 审批组织 **/
	private Organization organization;

	/** 审批人 **/
	private Admin admin;

	/** 采购人 **/
	private Admin purchaser;

	/** 供应商 **/
	private Supplier supplier;

	/** 进价 **/
	private BigInteger unitPrice;

	/** 总金额 **/
	private BigInteger allPrice;

	/** 处理日期 **/
	private Date treatmentDate;

	/** 备注 **/
	private String memo;

	/** 单据状态 0：未入账 1：入账**/
	private char billState;

	/** 生成批次 **/
	private String purchaseNo;

	/** 关联预采购单 **/
	private Set<PurchaseRequisition> purchaserequisitions = new HashSet<PurchaseRequisition>();

	/** 关联采购验收 **/
	private PurchaseCheck purchaseCheck;
	
	/** 是否完成验收 **/
	private Boolean isChecked;

	@ManyToOne
	@JoinColumn(name = "f_product")
	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	@Column(name = "f_number")
	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	@ManyToOne
	@JoinColumn(name = "f_organization", nullable = false)
	public Organization getOrganization() {
		return organization;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
	}

	@ManyToOne
	@JoinColumn(name = "f_admin")
	public Admin getAdmin() {
		return admin;
	}

	public void setAdmin(Admin admin) {
		this.admin = admin;
	}

	@ManyToOne
	@JoinColumn(name = "f_purchaser")
	public Admin getPurchaser() {
		return purchaser;
	}

	public void setPurchaser(Admin purchaser) {
		this.purchaser = purchaser;
	}

	@ManyToOne
	@JoinColumn(name = "f_supplier")
	public Supplier getSupplier() {
		return supplier;
	}

	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}

	@Column(name = "f_unitprice", precision = 12, scale = 6)
	public BigInteger getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(BigInteger unitPrice) {
		this.unitPrice = unitPrice;
	}

	@Column(name = "f_allprice", precision = 12, scale = 6)
	public BigInteger getAllPrice() {
		return allPrice;
	}

	public void setAllPrice(BigInteger allPrice) {
		this.allPrice = allPrice;
	}

	@Column(name = "f_treatmentdate")
	public Date getTreatmentDate() {
		return treatmentDate;
	}

	public void setTreatmentDate(Date treatmentDate) {
		this.treatmentDate = treatmentDate;
	}

	@Column(name = "f_memo")
	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	@Column(name = "f_bill_state")
	public char getBillState() {
		return billState;
	}

	public void setBillState(char billState) {
		this.billState = billState;
	}

	@Column(name = "f_purchase_no")
	public String getPurchaseNo() {
		return purchaseNo;
	}

	public void setPurchaseNo(String purchaseNo) {
		this.purchaseNo = purchaseNo;
	}

	/**
	 * 
	 * 获得预购单.
	 * 
	 * @return
	 */
	@OneToMany(mappedBy = "purchase", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	public Set<PurchaseRequisition> getPurchaserequisitions() {
		return purchaserequisitions;
	}

	/**
	 * 
	 * 设置预购单.
	 * 
	 * @param purchaserequisitions
	 *            预购单
	 */
	public void setPurchaserequisitions(
			Set<PurchaseRequisition> purchaserequisitions) {
		this.purchaserequisitions = purchaserequisitions;
	}

	/**
	 * 
	 * 获得采购验收.
	 * 
	 * @return
	 */
	@OneToOne(mappedBy = "purchase")
	public PurchaseCheck getPurchaseCheck() {
		return purchaseCheck;
	}

	public void setPurchaseCheck(PurchaseCheck purchaseCheck) {
		this.purchaseCheck = purchaseCheck;
	}

	@Column(name = "f_is_checked")
	public Boolean getIsChecked() {
		return isChecked;
	}

	public void setIsChecked(Boolean isChecked) {
		this.isChecked = isChecked;
	}

}
