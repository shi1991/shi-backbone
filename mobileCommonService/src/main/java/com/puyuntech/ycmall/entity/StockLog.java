package com.puyuntech.ycmall.entity;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

/**
 * 
 * Entity - 库存. 
 * Created on 2015-8-27 下午5:45:47 
 * @author 施长成
 */
@Entity
@Table(name = "t_stock_log")
public class StockLog extends BaseEntity<Long> {

	private static final long serialVersionUID = 2486757732237292195L;

	/**
	 * 类型
	 */
	public enum Type {

		/** 入库 */
		stockIn,

		/** 出库 */
		stockOut
	}

	/** 类型 */
	private StockLog.Type type;

	/** 商品序列号 **/
	private String productSn;
	
	/** 商品编号 应该外键到商品对象 **/
	private Product product; 
	
	/** 当前状态 1：在库 2：仓库退货中 3：配送中 4：调拨中 5：已发货 6：锁定 7：用户退货中 8：门店退货中 8:调拨中丢失 9:配送中丢失 10:已完成  其余待定 **/
	private String state;
	
	/** 所属组织机构 **/
	private Organization organization;
	
	/** 进价格 **/
	private BigDecimal unitPrince;
	
	/** 门店退货记录 **/
	private Set<ShopChange> shopChanges = new HashSet<ShopChange>();
	
	/** 仓库退货记录 **/
	private Set<WareHouseChangeLog> wareHouseChangeLogs = new HashSet<WareHouseChangeLog>();

	/** 配送 **/
	private Set<Distribution> distributions = new HashSet<Distribution>();
	
	/** 配送验收 **/
	private Set<DistributionCheck> distributionChecks = new HashSet<DistributionCheck>();
	
	/** 调拨单 **/
	private Set<Allot> allots = new HashSet<Allot>();

	/** 调拨单验收 **/
	private Set<AllotCheck> allotChecks = new HashSet<AllotCheck>();
	
	/** 采购验收 **/
	private Set<PurchaseCheck> purchaseChecks = new HashSet<PurchaseCheck>();
	
	/**
	 * 获取类型
	 * 
	 * @return 类型
	 */
	@Column(name="f_type",nullable = false, updatable = false)
	public StockLog.Type getType() {
		return type;
	}

	/**
	 * 设置类型
	 * 
	 * @param type
	 *            类型
	 */
	public void setType(StockLog.Type type) {
		this.type = type;
	}

	@Column(name="f_product_sn",unique=false)
	public String getProductSn() {
		return productSn;
	}

	public void setProductSn(String productSn) {
		this.productSn = productSn;
	}

	/**
	 * 获取商品
	 * 
	 * @return 商品
	 */
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="f_product",nullable = false, updatable = false)
	@JsonBackReference
	public Product getProduct() {
		return product;
	}

	/**
	 * 设置商品
	 * 
	 * @param product
	 *            商品
	 */
	@JsonBackReference
	public void setProduct(Product product) {
		this.product = product;
	}

	@Column(name="f_state")
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="f_organization",nullable = false, updatable = true)
	public Organization getOrganization() {
		return organization;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
	}

	@Column(name="f_unitprice",precision = 21, scale = 6)
	public BigDecimal getUnitPrince() {
		return unitPrince;
	}

	public void setUnitPrince(BigDecimal unitPrince) {
		this.unitPrince = unitPrince;
	}

	@ManyToMany(mappedBy="stockLogs",fetch=FetchType.LAZY)
	public Set<ShopChange> getShopChanges() {
		return shopChanges;
	}

	public void setShopChanges(Set<ShopChange> shopChanges) {
		this.shopChanges = shopChanges;
	}

	@ManyToMany(mappedBy = "stockLogs",fetch = FetchType.LAZY)
	public Set<WareHouseChangeLog> getWareHouseChangeLogs() {
		return wareHouseChangeLogs;
	}

	public void setWareHouseChangeLogs(Set<WareHouseChangeLog> wareHouseChangeLogs) {
		this.wareHouseChangeLogs = wareHouseChangeLogs;
	}

	@ManyToMany(mappedBy = "stockLogs",fetch = FetchType.LAZY)
	public Set<Distribution> getDistributions() {
		return distributions;
	}

	public void setDistributions(Set<Distribution> distributions) {
		this.distributions = distributions;
	}

	@ManyToMany(mappedBy="stockLogs",fetch = FetchType.LAZY)
	public Set<DistributionCheck> getDistributionChecks() {
		return distributionChecks;
	}

	public void setDistributionChecks(Set<DistributionCheck> distributionChecks) {
		this.distributionChecks = distributionChecks;
	}

	@ManyToMany(mappedBy="stockLogs",fetch = FetchType.LAZY)
	public Set<Allot> getAllots() {
		return allots;
	}

	public void setAllots(Set<Allot> allots) {
		this.allots = allots;
	}

	@ManyToMany(mappedBy="stockLogs",fetch = FetchType.LAZY)
	public Set<AllotCheck> getAllotChecks() {
		return allotChecks;
	}

	public void setAllotChecks(Set<AllotCheck> allotChecks) {
		this.allotChecks = allotChecks;
	}

	@ManyToMany(mappedBy="stockLogs",fetch = FetchType.LAZY)	
	public Set<PurchaseCheck> getPurchaseChecks() {
		return purchaseChecks;
	}

	public void setPurchaseChecks(Set<PurchaseCheck> purchaseChecks) {
		this.purchaseChecks = purchaseChecks;
	}
	
	
	
}
