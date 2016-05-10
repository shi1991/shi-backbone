package com.puyuntech.ycmall.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 
 * Entity - 门店 和 商品关联表 . 
 * Created on 2015-10-29 下午3:45:17 
 * @author 严志森
 * 	
 */
@Entity
@Table(name = "t_organization_product_service")
public class ShopProductsFw extends BaseEntity<Long> {

	private static final long serialVersionUID = 1881429780908215418L;

	/** 商品ID */
	private Product productId;
	
	/** 门店ID */
	private Organization organizationId;
	
	/** 服务ID */
	private ShopService shopServiceID;
	
	

	
	
	/**
	 * 服务
	 * 
	 * @return 
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "f_shopService_id")
	public ShopService getShopServiceID() {
		return shopServiceID;
	}

	public void setShopServiceID(ShopService shopServiceID) {
		this.shopServiceID = shopServiceID;
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
