package com.puyuntech.ycmall.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;

/**
 * 
 * Entity - 门店 和 商品关联表 . 
 * Created on 2015-10-29 下午3:45:17 
 * @author 严志森
 * 	
 */
@Entity
@Table(name = "t_organization_products")
public class OrganizationProducts extends BaseEntity<Long> {

	private static final long serialVersionUID = 1881429780908215418L;

	/** 门店库存数量 */
	private Integer stock;
	
	/** 已分配库存 */
	private Integer allocatedStock;
	
	/** 商品ID */
	private Product productId;
	
	/** 门店ID */
	private Organization organizationId;
	
	@Min(0)
	@Column(name = "f_stock",nullable =false)
	public Integer getStock() {
		return stock;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
	}
	
	@Min(0)
	@Column(name = "f_allocated_stock",nullable =false)
	public Integer getAllocatedStock() {
		return allocatedStock;
	}

	public void setAllocatedStock(Integer allocatedStock) {
		this.allocatedStock = allocatedStock;
	}
	
	/**
	 * 获取商品
	 * 
	 * @return 商品
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "f_product_id")
	public Product getProductId() {
		return productId;
	}

	public void setProductId(Product productId) {
		this.productId = productId;
	}
	
	/**
	 * 获取门店
	 * 
	 * @return 门店
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "f_organization_id")
	public Organization getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(Organization organizationId) {
		this.organizationId = organizationId;
	}
	
	
	
}
