package com.puyuntech.ycmall.entity;

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
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.puyuntech.ycmall.BaseAttributeConverter;

/**
 * 
 * 商品 属性 Entity . 
 * Created on 2015-8-27 上午11:21:45 
 * @author 施长成
 * 
 * 商品属性 和 商品分类之间是一个多对一的关系，【商品详情中属性 - 属性名称 是可以修改的】
 */
@Entity
@Table(name = "t_attribute")
public class Attribute extends OrderEntity<Long> {

	private static final long serialVersionUID = 385212519570791152L;

	/** 名称 */
	private String name;

	/** 属性序号 */
	private Integer propertyIndex;

	/** 绑定分类 */
	private ProductCategory productCategory;

	/** 可选项 */
	private List<String> options = new ArrayList<String>();

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
	 * 获取属性序号
	 * 
	 * @return 属性序号
	 */
	@Column(name="f_property_index",nullable = false, updatable = false)
	public Integer getPropertyIndex() {
		return propertyIndex;
	}

	/**
	 * 设置属性序号
	 * 
	 * @param propertyIndex
	 *            属性序号
	 */
	public void setPropertyIndex(Integer propertyIndex) {
		this.propertyIndex = propertyIndex;
	}

	/**
	 * 获取绑定分类
	 * 
	 * @return 绑定分类
	 */
	@NotNull(groups = Save.class)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="f_product_category",nullable = false, updatable = false)
	public ProductCategory getProductCategory() {
		return productCategory;
	}

	/**
	 * 设置绑定分类
	 * 
	 * @param productCategory
	 *            绑定分类
	 */
	public void setProductCategory(ProductCategory productCategory) {
		this.productCategory = productCategory;
	}

	/**
	 * 获取可选项
	 * 
	 * @return 可选项
	 */
	@NotEmpty
	@Column(name="f_options",nullable = false, length = 4000)
	@Convert(converter = OptionConverter.class)
	public List<String> getOptions() {
		return options;
	}

	/**
	 * 设置可选项
	 * 
	 * @param options
	 *            可选项
	 */
	public void setOptions(List<String> options) {
		this.options = options;
	}

	/**
	 * 类型转换 - 可选项
	 */
	@Converter
	public static class OptionConverter extends BaseAttributeConverter<List<String>> implements AttributeConverter<Object, String> {
	}

}
