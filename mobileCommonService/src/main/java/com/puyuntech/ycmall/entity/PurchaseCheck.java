package com.puyuntech.ycmall.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * 
 * 采购验收记录  Entity . 
 * Created on 2015-8-28 下午3:55:36 
 * @author 施长成
 * 
 * TODO  退货单号 使用 外键【现在未做关联】
 */
@Entity
@Table(name="t_purchase_check")
public class PurchaseCheck extends BaseEntity<Long> {

	private static final long serialVersionUID = -143784509613498964L;
	
	/** 采购单编号 **/
	private Purchase purchase ;
	
	/** 仓库退换货单编号 **/
	private WareHouseChangeLog wareHouseChangeLog;
	
	/** 退货单号 **/
	private Integer shopChange;
	
	/** 商品  **/
	private Product product;
	
	/** 操作人 **/
	private Admin operator;
	
	private Date date;
	
	private int number;
	
	private String memo;
	
	private char billState;
	
	private char isDifference;
	
	//验收类型： 0：采购验收 1：退换货验收
	private Byte type;
	
	private Set<StockLog> stockLogs = new HashSet<StockLog>();

	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="f_purchase_fk")
	public Purchase getPurchase() {
		return purchase;
	}

	public void setPurchase(Purchase purchase) {
		this.purchase = purchase;
	}

	@ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="f_ware_house_change_log_fk")
	public WareHouseChangeLog getWareHouseChangeLog() {
		return wareHouseChangeLog;
	}

	public void setWareHouseChangeLog(WareHouseChangeLog wareHouseChangeLog) {
		this.wareHouseChangeLog = wareHouseChangeLog;
	}

	@NotNull
	@Column(name="f_type")
	public Byte getType() {
		return type;
	}

	public void setType(Byte type) {
		this.type = type;
	}

	@Column(name="f_shop_chang")
	public Integer getShopChange() {
		return shopChange;
	}

	public void setShopChange(Integer shopChange) {
		this.shopChange = shopChange;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="f_product")
	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="f_operator")
	public Admin getOperator() {
		return operator;
	}

	public void setOperator(Admin operator) {
		this.operator = operator;
	}

	@Column(name="f_date")
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@Column(name="f_number")
	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	@Column(name="f_memo")
	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	@Column(name="f_bill_state")
	public char getBillState() {
		return billState;
	}

	public void setBillState(char billState) {
		this.billState = billState;
	}

	@Column(name="f_is_difference")
	public char getIsDifference() {
		return isDifference;
	}

	public void setIsDifference(char isDifference) {
		this.isDifference = isDifference;
	}

	@ManyToMany
	@JoinTable(name="t_purchase_check_productsn",joinColumns = {@JoinColumn(name = "f_purchase_check", referencedColumnName = "f_id") }, inverseJoinColumns = { @JoinColumn(name = "f_product_sn", referencedColumnName = "f_id") } )
	public Set<StockLog> getStockLogs() {
		return stockLogs;
	}

	public void setStockLogs(Set<StockLog> stockLogs) {
		this.stockLogs = stockLogs;
	}
	
}
