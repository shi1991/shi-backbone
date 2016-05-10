package com.puyuntech.ycmall.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.AttributeConverter;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Converter;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.puyuntech.ycmall.BaseAttributeConverter;

/**
 * 
 * Entity - 订单项 . 
 * Created on 2015-8-27 下午4:09:49 
 * @author 施长成
 */
@Entity
@Table(name = "t_order_item")
public class OrderItem extends BaseEntity<Long> {

	private static final long serialVersionUID = -5622329816683797837L;

	/** 商品编号 */
	private String sn;

	/** 商品名称 */
	private String name;

	/** 商品类型 */
	private Product.Type type;

	/** 商品价格 */
	private BigDecimal price;

    /** 商品退货时价格 */
    private BigDecimal returnPrice;

	/** 商品重量 */
	private Integer weight;

	/** 是否需要物流 */
	private Boolean isDelivery;

	/** 商品缩略图 */
	private String thumbnail;

	/** 商品数量 */
	private Integer quantity;

	/** 已发货数量 */
	private Integer shippedQuantity;

	/** 已退货数量 */
	private Integer returnedQuantity;

	/** 商品 */
	private Product product;

	/** 订单 */
	private Order order;

	/** 规格 */
	private List<String> specifications = new ArrayList<String>();
	
	/** 是否已经评价 */
	private boolean isReview;

    /** 合约机 用户信息 -包含手机号 **/
    private ContractPhoneNumberUserInfo phoneNumInfo;

	/**
	 * 获取商品编号
	 * 
	 * @return 商品编号
	 */
	@Column(name="f_sn",nullable = false, updatable = false)
	public String getSn() {
		return sn;
	}

	/**
	 * 设置商品编号
	 * 
	 * @param sn
	 *            商品编号
	 */
	public void setSn(String sn) {
		this.sn = sn;
	}

	/**
	 * 获取商品名称
	 * 
	 * @return 商品名称
	 */
	@Column(name="f_name",nullable = false, updatable = false)
	public String getName() {
		return name;
	}

	/**
	 * 设置商品名称
	 * 
	 * @param name
	 *            商品名称
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 获取商品类型
	 * 
	 * @return 商品类型
	 */
	@Column(name="f_type",nullable = false, updatable = false)
	public Product.Type getType() {
		return type;
	}

	/**
	 * 设置商品类型
	 * 
	 * @param type
	 *            商品类型
	 */
	public void setType(Product.Type type) {
		this.type = type;
	}

	/**
	 * 获取商品价格
	 * 
	 * @return 商品价格
	 */
	@Column(name="f_price",nullable = false, updatable = false, precision = 21, scale = 6)
	public BigDecimal getPrice() {
		return price;
	}

	/**
	 * 设置商品价格
	 * 
	 * @param price
	 *            商品价格
	 */
	public void setPrice(BigDecimal price) {
		this.price = price;
	}

    /**
     * 获取商品退货时商品价格
     * @return
     */
    @Column(name="f_return_price",nullable = true, precision = 21, scale = 6)
    public BigDecimal getReturnPrice() {
        return returnPrice;
    }

    /**
     * 获取商品退货时商品价格
     * @param returnPrice
     */
    public void setReturnPrice(BigDecimal returnPrice) {
        this.returnPrice = returnPrice;
    }

	/**
	 * 获取商品重量
	 * 
	 * @return 商品重量
	 */
	@Column(name="f_weight",updatable = false)
	public Integer getWeight() {
		return weight;
	}

	/**
	 * 设置商品重量
	 * 
	 * @param weight
	 *            商品重量
	 */
	public void setWeight(Integer weight) {
		this.weight = weight;
	}

	/**
	 * 获取是否需要物流
	 * 
	 * @return 是否需要物流
	 */
	@Column(name="f_is_delivery",nullable = false, updatable = false)
	public Boolean getIsDelivery() {
		return isDelivery;
	}

	/**
	 * 设置是否需要物流
	 * 
	 * @param isDelivery
	 *            是否需要物流
	 */
	public void setIsDelivery(Boolean isDelivery) {
		this.isDelivery = isDelivery;
	}

	/**
	 * 获取商品缩略图
	 * 
	 * @return 商品缩略图
	 */
	@Column(name="f_thumbnail",updatable = false)
	public String getThumbnail() {
		return thumbnail;
	}

	/**
	 * 设置商品缩略图
	 * 
	 * @param thumbnail
	 *            商品缩略图
	 */
	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}

	/**
	 * 获取商品数量
	 * 
	 * @return 商品数量
	 */
	@Column(name="f_quantity",nullable = false, updatable = false)
	public Integer getQuantity() {
		return quantity;
	}

	/**
	 * 设置商品数量
	 * 
	 * @param quantity
	 *            商品数量
	 */
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	/**
	 * 获取已发货数量
	 * 
	 * @return 已发货数量
	 */
	@Column(name="f_shipped_quantity",nullable = false)
	public Integer getShippedQuantity() {
		return shippedQuantity;
	}

	/**
	 * 设置已发货数量
	 * 
	 * @param shippedQuantity
	 *            已发货数量
	 */
	public void setShippedQuantity(Integer shippedQuantity) {
		this.shippedQuantity = shippedQuantity;
	}

	/**
	 * 获取已退货数量
	 * 
	 * @return 已退货数量
	 */
	@Column(name="f_returned_quantity",nullable = false)
	public Integer getReturnedQuantity() {
		return returnedQuantity;
	}

	/**
	 * 设置已退货数量
	 * 
	 * @param returnedQuantity
	 *            已退货数量
	 */
	public void setReturnedQuantity(Integer returnedQuantity) {
		this.returnedQuantity = returnedQuantity;
	}

	/**
	 * 获取商品
	 * 
	 * @return 商品
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="f_product")
	public Product getProduct() {
		return product;
	}

	/**
	 * 设置商品
	 * 
	 * @param product
	 *            商品
	 */
	public void setProduct(Product product) {
		this.product = product;
	}

	/**
	 * 获取订单
	 * 
	 * @return 订单
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "f_orders", nullable = false, updatable = false)
	public Order getOrder() {
		return order;
	}
	
	/**
	 * 获取是否已经评价
	 * 
	 * @return 是否已经评价
	 */
	@Column(name="f_is_review",nullable = false)
	public boolean getIsReview() {
		return isReview;
	}

	public void setIsReview(boolean isReview) {
		this.isReview = isReview;
	}
	
	/**
	 * 设置订单
	 * 
	 * @param order
	 *            订单
	 */
	public void setOrder(Order order) {
		this.order = order;
	}

	/**
	 * 获取规格
	 * 
	 * @return 规格
	 */
	@Column(name="f_specifications",updatable = false, length = 4000)
	@Convert(converter = SpecificationConverter.class)
	public List<String> getSpecifications() {
		return specifications;
	}

	/**
	 * 设置规格
	 * 
	 * @param specifications
	 *            规格
	 */
	public void setSpecifications(List<String> specifications) {
		this.specifications = specifications;
	}

    /**
     * 获取合约套餐相关信息
     * @return
     */
    @OneToOne
    @JoinColumn(name="f_phonenum_info")
    public ContractPhoneNumberUserInfo getPhoneNumInfo() {
        return phoneNumInfo;
    }

    /**
     * 设置合约套餐相关信息
     * @param phoneNumInfo
     */
    public void setPhoneNumInfo(ContractPhoneNumberUserInfo phoneNumInfo) {
        this.phoneNumInfo = phoneNumInfo;
    }

    /**
	 * 获取商品总重量
	 * 
	 * @return 商品总重量
	 */
	@Transient
	public int getTotalWeight() {
		if (getWeight() != null && getQuantity() != null) {
			return getWeight() * getQuantity();
		} else {
			return 0;
		}
	}

	/**
	 * 获取小计
	 * 
	 * @return 小计
	 */
	@Transient
	public BigDecimal getSubtotal() {
		if (getPrice() != null && getQuantity() != null) {
			return getPrice().multiply(new BigDecimal(getQuantity()));
		} else {
			return BigDecimal.ZERO;
		}
	}

	/**
	 * 获取可发货数
	 * 
	 * @return 可发货数
	 */
	@Transient
	public int getShippableQuantity() {
		int shippableQuantity = getQuantity() - getShippedQuantity();
		return shippableQuantity >= 0 ? shippableQuantity : 0;
	}

	/**
	 * 获取可退货数
	 * 
	 * @return 可退货数
	 */
	@Transient
	public int getReturnableQuantity() {
		int returnableQuantity = getShippedQuantity() - getReturnedQuantity();
		return returnableQuantity >= 0 ? returnableQuantity : 0;
	}

	/**
	 * 类型转换 - 规格
	 * 
	 */
	@Converter
	public static class SpecificationConverter extends BaseAttributeConverter<List<String>> implements AttributeConverter<Object, String> {
	}

}
