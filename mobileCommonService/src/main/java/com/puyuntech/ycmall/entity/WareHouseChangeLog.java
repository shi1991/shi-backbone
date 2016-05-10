package com.puyuntech.ycmall.entity;

import java.math.BigInteger;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * 
 * 仓库退换货记录 . 
 * Created on 2015-8-27 下午6:07:56 
 * @author 施长成
 */
@Entity
@Table(name="t_warehouse_chang" )
public class WareHouseChangeLog extends BaseEntity<Long> {

	private static final long serialVersionUID = 3198792275813355167L;
	
	/** 商品编号 **/	
	private Set<StockLog> stockLogs = new HashSet<StockLog>();
	
	/** 操作员 **/
	private String operator;
	
	/** 操作时间 **/
	private Date date;
	
	/** 备注 **/
	private String memo;
	
	/** 单据状态 **/
	private Boolean billState;
	
	/** 单据状态 **/
	private Boolean isCompleted;
	
	/** 供应商**/
	private Supplier supplier;
	
	/** 进价 **/
	private BigInteger unitPrice;
	
	/** 处理类型 0:退货 1：换货 **/
	private Byte type;
	
	/** 关联采购验收 **/
	private Set<PurchaseCheck> purchaseChecks = new HashSet<PurchaseCheck>();
	
	/** 是否完成验收 **/
	private Boolean isChecked;

	@ManyToMany
	@JoinTable(name = "t_warehouse_chang_productsn", joinColumns = { @JoinColumn(name = "f_warehouse_chang", referencedColumnName = "f_id") }, inverseJoinColumns = { @JoinColumn(name = "f_product_sn", referencedColumnName = "f_id") })
	public Set<StockLog> getStockLogs() {
		return stockLogs;
	}

	public void setStockLogs(Set<StockLog> stockLogs) {
		this.stockLogs = stockLogs;
	}

	@Column(name="f_operator")
	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	@Column(name="f_date")
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@Column(name="f_memo")
	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	@Column(name="f_bill_state")
	public Boolean getBillState() {
		return billState;
	}

	public void setBillState(Boolean billState) {
		this.billState = billState;
	}

	@Column(name="f_is_completed")
	public Boolean getIsCompleted() {
		return isCompleted;
	}

	public void setIsCompleted(Boolean isCompleted) {
		this.isCompleted = isCompleted;
	}

	@ManyToOne
	@JoinColumn(name="f_supplier")
	public Supplier getSupplier() {
		return supplier;
	}

	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}

	@Column(name="f_unitprice")
	public BigInteger getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(BigInteger unitPrice) {
		this.unitPrice = unitPrice;
	}

	@Column(name="f_type")
	public Byte getType() {
		return type;
	}

	public void setType(Byte type) {
		this.type = type;
	}
	
	/**
	 * 
	 * 获得采购验收.
	 * 
	 * @return
	 */
	@OneToMany(mappedBy = "wareHouseChangeLog")
	public Set<PurchaseCheck> getPurchaseChecks() {
		return purchaseChecks;
	}

	public void setPurchaseChecks(Set<PurchaseCheck> purchaseChecks) {
		this.purchaseChecks = purchaseChecks;
	}
	
	@Column(name = "f_is_checked")
	public Boolean getIsChecked() {
		return isChecked;
	}

	public void setIsChecked(Boolean isChecked) {
		this.isChecked = isChecked;
	}
	
	/**
	 * 设置操作员
	 * 
	 * @param operator
	 *            操作员
	 */
	@Transient
	public void setOperator(Admin operator) {
		setOperator(operator != null ? operator.getWebUsername() : null);
	}
}
