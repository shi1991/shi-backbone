package com.puyuntech.ycmall.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;
import javax.validation.Valid;

import com.puyuntech.ycmall.BaseAttributeConverter;
import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Store;


@Entity
@Table(name = "t_return_order_item")
public class ReturnOrderItem extends BaseEntity<Long> {
	
	private static final long serialVersionUID = 3202075728887957321L;
	
	/** 商品编号 */
	 private String sn;
	
	/** 商品名称 */
	private String name;
	
	/** 商品类型 */
	private Product.Type type;
	
	/** 退还价格 */
	private BigDecimal price;
	
	/** 退还积分 */
	private Long point;
	
	/** 商品重量 */
	private Integer weight;
	
	/** 缩略图 */
	private String thumbnail;
	
	/** 商品数量 */
	private Integer quantity;
	
	/** 商品 */
	private Product product;
	
	/** 退单 */
	private ReturnOrder returnOrder;
	
	/** 规格值 */
	private List<String> specificationValues = new ArrayList<String>();
	
	/** 备注 */
	private String memo;
	
	@Column(name="f_sn")
	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	@Column(name="f_name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 获取类型
	 * 
	 * @return 类型
	 */
	@Field(store = Store.YES, index = Index.YES, analyze = Analyze.NO)
	@Column(name="f_type",nullable = false)
	public Product.Type getType() {
		return type;
	}

	public void setType(Product.Type type) {
		this.type = type;
	}

	@Column(name="f_price", precision = 21, scale = 6)
	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	@Column(name="f_point")
	public Long getPoint() {
		return point;
	}

	public void setPoint(Long point) {
		this.point = point;
	}

	@Column(name="f_memo")
	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	/**
	 * 获取商品重量
	 * 
	 * @return 商品重量
	 */
	@Column(name="f_weight", updatable = false)
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
	 * 获取退货数量
	 * 
	 * @return 退货数量
	 */
	@Column(name="f_returned_quantity",nullable = false, updatable = false)
	public Integer getQuantity() {
		return quantity;
	}

	/**
	 * 设置退货数量
	 * 
	 * @param quantity
	 *            退货数量
	 */
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	
	/**
	 * 获取规格值
	 * 
	 * @return 规格值
	 */
    @Valid
    @Column(name = "f_specifications", length = 4000)
    @Convert(converter = SpecificationConverter.class)
    public List<String> getSpecificationValues() {
        return specificationValues;
    }

	/**
	 * 设置规格值
	 * 
	 * @param specificationValues
	 *            规格值
	 */
	public void setSpecificationValues(
			List<String> specificationValues) {
		this.specificationValues = specificationValues;
	}

	@Column(name="f_thumbnail")
	public String getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
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
	@JoinColumn(name="f_return_order")
	public ReturnOrder getReturnOrder() {
		return returnOrder;
	}

	public void setReturnOrder(ReturnOrder returnOrder) {
		this.returnOrder = returnOrder;
	}

    /**
     * 类型转换 - 规格
     *
     */
    @Converter
    public static class SpecificationConverter extends BaseAttributeConverter<List<String>> implements AttributeConverter<Object, String> {
    }
	
}
