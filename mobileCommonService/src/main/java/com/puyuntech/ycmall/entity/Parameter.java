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
 * 商品相关的参数  . 
 * Created on 2015-8-27 上午10:54:23 
 * @author 施长成
 */
@Entity
@Table(name = "t_parameter")
public class Parameter extends OrderEntity<Long> {

	private static final long serialVersionUID = -1968122348433976571L;

	/** 参数组【将几个参数归纳在同一个分组】  */
	private String group;

	/** 绑定分类 【参数和商品之间 是一个多对一的关系】  */
	private ProductCategory productCategory;

	/** 参数名称 */
	private List<String> names = new ArrayList<String>();

	/**
	 * 获取参数组
	 * 
	 * @return 参数组
	 */
	@NotEmpty
	@Length(max = 200)
	@Column(name = "f_parameter_group", nullable = false)
	public String getGroup() {
		return group;
	}

	/**
	 * 设置参数组
	 * 
	 * @param group
	 *            参数组
	 */
	public void setGroup(String group) {
		this.group = group;
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
	 * 获取参数名称
	 * 
	 * @return 参数名称
	 */
	@NotEmpty
	@Column(name="f_names",nullable = false, length = 4000)
	@Convert(converter = NameConverter.class)
	public List<String> getNames() {
		return names;
	}

	/**
	 * 设置参数名称
	 * 
	 * @param names
	 *            参数名称
	 */
	public void setNames(List<String> names) {
		this.names = names;
	}

	/**
	 * 类型转换 - 参数名称
	 */
	@Converter
	public static class NameConverter extends BaseAttributeConverter<List<String>> implements AttributeConverter<Object, String> {
	}

}
