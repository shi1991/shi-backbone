package com.puyuntech.ycmall.entity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PreRemove;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.puyuntech.ycmall.entity.Product.SpecificationItemConverter;
import com.puyuntech.ycmall.entity.value.SpecificationItem;

/**
 * 
 * 商品型号 Entity . 
 * Created on 2015-9-24 下午13:51:27 
 * @author 王凯斌
 * 
 * 型号和商品存在关联
 * 型号与品牌存在关联
 * 
 */
@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler","fieldHandler"}) 
@Entity
@Table(name = "t_product_model")
public class ProductModel extends OrderEntity<Long> {

	private static final long serialVersionUID = 5437704140294633952L;

	/** 名称 */
	private String name;

	/** 品牌 */
	private Brand brand;
	
	/** 商品 */
	private Set<Product> product = new HashSet<Product>();
	
	/** 评论 */
	private Set<Review> reviews = new HashSet<Review>();
	
	/** 商品分类 */
	private ProductCategory productCategory;
	
	/** 规格项 通过【SpecificationItem】 转换成 key value的json数据保存到数据库  */
	private List<SpecificationItem> specificationItems = new ArrayList<SpecificationItem>();

	/**
	 * 获取名称
	 * 
	 * @return 名称
	 */
	@NotEmpty
	@Length(max = 200)
	@Column(name="f_name",nullable = false)
	public String getName() {
		return name;
	}

	/**
	 * 设置名称
	 * 
	 * @param name
	 *            名称
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 获取品牌
	 * 
	 * @return 品牌
	 */
	@JsonBackReference
	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="f_brand",nullable = false)
	public Brand getBrand() {
		return brand;
	}

	/**
	 * 设置品牌
	 * 
	 * @param brand
	 *            品牌
	 */
	@JsonBackReference
	public void setBrand(Brand brand) {
		this.brand = brand;
	}
	
	/**
	 * 获取商品
	 * 
	 * @return 商品
	 */
	@JsonBackReference
	@OneToMany(mappedBy = "productModel", fetch = FetchType.LAZY)
	public Set<Product> getProduct() {
		return product;
	}

	/**
	 * 设置商品
	 * 
	 * @param product
	 *            商品
	 */
	public void setProduct(Set<Product> product) {
		this.product = product;
	}
	
	/**
	 * 获取评论
	 * 
	 * @return 评论
	 */
	@JsonBackReference
	@OneToMany(mappedBy = "productModel", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	public Set<Review> getReviews() {
		return reviews;
	}

	/**
	 * 设置评论
	 * 
	 * @param reviews
	 *            评论
	 */
	public void setReviews(Set<Review> reviews) {
		this.reviews = reviews;
	}
	
	/**
	 * 获取商品分类
	 * 
	 * @return 商品分类
	 */
	@JsonBackReference
	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="f_product_category")
	public ProductCategory getProductCategory() {
		return productCategory;
	}

	/**
	 * 设置商品分类
	 * 
	 * @param productCategory
	 *            商品分类
	 */
	@JsonBackReference
	public void setProductCategory(ProductCategory productCategory) {
		this.productCategory = productCategory;
	}
	
	/**
	 * 获取规格项
	 * 
	 * @return 规格项
	 */
	@Valid
	@Column(name="f_specification_items",length = 4000)
	@Convert(converter = SpecificationItemConverter.class)
	public List<SpecificationItem> getSpecificationItems() {
		return specificationItems;
	}

	/**
	 * 设置规格项
	 * 
	 * @param specificationItems
	 *            规格项
	 */
	public void setSpecificationItems(List<SpecificationItem> specificationItems) {
		this.specificationItems = specificationItems;
	}
	
	/**TODO
	 * 删除前处理
	 */
	@PreRemove
	public void preRemove() {
		
	}
}
