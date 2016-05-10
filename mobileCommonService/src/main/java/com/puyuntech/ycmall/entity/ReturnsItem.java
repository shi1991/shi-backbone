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
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.puyuntech.ycmall.BaseAttributeConverter;

/**
 * 
 * Entity - 退货项 . 
 * Created on 2015-8-28 下午6:04:34 
 * @author 施长成
 */
@Entity
@Table(name = "t_returns_item")
public class ReturnsItem extends BaseEntity<Long> {

	private static final long serialVersionUID = -7706419985152283464L;

	/** 商品编号 */
	private String sn;

	/** 商品名称 */
	private String name;

	/** 数量 */
	private Integer quantity;

	/** 退货单 */
	private Returns returns;

	/** 规格 */
	private List<String> specifications = new ArrayList<String>();

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
	 * 获取数量
	 * 
	 * @return 数量
	 */
	@NotNull
	@Min(1)
	@Column(name="f_quantity",nullable = false, updatable = false)
	public Integer getQuantity() {
		return quantity;
	}

	/**
	 * 设置数量
	 * 
	 * @param quantity
	 *            数量
	 */
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	/**
	 * 获取退货单
	 * 
	 * @return 退货单
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="f_returns",nullable = false, updatable = false)
	public Returns getReturns() {
		return returns;
	}

	/**
	 * 设置退货单
	 * 
	 * @param returns
	 *            退货单
	 */
	public void setReturns(Returns returns) {
		this.returns = returns;
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
	 * 类型转换 - 规格
	 */
	@Converter
	public static class SpecificationConverter extends BaseAttributeConverter<List<String>> implements AttributeConverter<Object, String> {
	}

}
