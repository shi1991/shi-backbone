package com.puyuntech.ycmall.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.PreRemove;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * 
 * 商品品牌 Entity . 
 * Created on 2015-8-27 上午11:31:27 
 * @author 施长成
 * 
 * 品牌 和 促销存在关联
 * 品牌 和 货品存在管理
 * 
 * 品牌和商品分类是一对多的关系
 */
@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler","fieldHandler"}) 
@Entity
@Table(name = "t_brand")
public class Brand extends OrderEntity<Long> {

	private static final long serialVersionUID = -3234169825479017519L;

	/** 路径前缀 */
	private static final String PATH_PREFIX = "/brand/content";

	/** 路径后缀 */
	private static final String PATH_SUFFIX = ".jhtml";

	/**
	 * 类型
	 */
	public enum Type {

		/** 文本 */
		text,

		/** 图片 */
		image
	}

	/** 名称 */
	private String name;

	/** 类型 */
	private Brand.Type type;

	/** logo */
	private String logo;

	/** 网址 */
	private String url;

	/** 介绍 */
	private String introduction;

	/** 商品 */
	private Set<Product> products = new HashSet<Product>();
	
	/** 型号 */
	private Set<ProductModel> model = new HashSet<ProductModel>();

	/** 商品分类 */
	private Set<ProductCategory> productCategories = new HashSet<ProductCategory>();

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
	 * 获取类型
	 * 
	 * @return 类型
	 */
	@NotNull
	@Column(name="f_type",nullable = false)
	public Brand.Type getType() {
		return type;
	}

	/**
	 * 设置类型
	 * 
	 * @param type
	 *            类型
	 */
	public void setType(Brand.Type type) {
		this.type = type;
	}

	/**
	 * 获取logo
	 * 
	 * @return logo
	 */
	@Length(max = 200)
	@Pattern(regexp = "^(?i)(http:\\/\\/|https:\\/\\/|\\/).*$")
	@Column(name="f_logo")
	public String getLogo() {
		return logo;
	}

	/**
	 * 设置logo
	 * 
	 * @param logo
	 *            logo
	 */
	public void setLogo(String logo) {
		this.logo = logo;
	}

	/**
	 * 获取网址
	 * TODO  网址可能 https 开头
	 * @return 网址
	 */
	@Length(max = 200)
	@Pattern(regexp = "^(?i)(http:\\/\\/|https:\\/\\/|ftp:\\/\\/|mailto:|\\/|#).*$")
	@Column(name="f_url")
	public String getUrl() {
		return url;
	}

	/**
	 * 设置网址
	 * 
	 * @param url
	 *            网址
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * 获取介绍
	 * 
	 * @return 介绍
	 */
	@Lob
	@Column(name="f_introduction")
	public String getIntroduction() {
		return introduction;
	}

	/**
	 * 设置介绍
	 * 
	 * @param introduction
	 *            介绍
	 */
	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	
	/**
	 * 获取商品
	 * 
	 * @return 商品
	 */
	 @OneToMany(mappedBy = "brand", fetch = FetchType.LAZY)
	public Set<Product> getProducts() {
		return products;
	}

	 /**
	  * 
	  * 设置货品.
	  * author: 施长成
	  *   date: 2015-9-28 下午2:16:08
	  * @param products
	  */
	public void setProducts(Set<Product> products) {
		this.products = products;
	}
	
	/**
	 * 获取型号
	 * 
	 * @return 型号
	 */
	 @OneToMany(mappedBy = "brand", fetch = FetchType.LAZY)
	public Set<ProductModel> getProductModel() {
		return model;
	}

	/**
	 * 设置型号
	 * 
	 * @param model
	 *            型号
	 */
	public void setProductModel(Set<ProductModel> model) {
		this.model = model;
	}

	/**
	 * 获取商品分类
	 * 
	 * @return 商品分类
	 */
	@ManyToMany(mappedBy = "brands", fetch = FetchType.LAZY )
	@OrderBy("order asc")
	public Set<ProductCategory> getProductCategories() {
		return productCategories;
	}

	/**
	 * 设置商品分类
	 * 
	 * @param productCategories
	 *            商品分类
	 */
	public void setProductCategories(Set<ProductCategory> productCategories) {
		this.productCategories = productCategories;
	}

	/**
	 * 获取路径
	 * 
	 * @return 路径
	 */
	@Transient
	public String getPath() {
		return getId() != null ? PATH_PREFIX + "/" + getId() + PATH_SUFFIX : null;
	}

	/**
	 * 删除前处理
	 */
	@PreRemove
	public void preRemove() {
		 Set<Product> goodsList = getProducts();
		if (goodsList != null) {
			for (Product product : goodsList) {
				product.setBrand(null);
			}
		}
		Set<ProductCategory> productCategories = getProductCategories();
		if (productCategories != null) {
			for (ProductCategory productCategory : productCategories) {
				productCategory.getBrands().remove(this);
			}
		}
	}

}
