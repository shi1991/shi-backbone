package com.puyuntech.ycmall.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.AttributeConverter;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Converter;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.puyuntech.ycmall.BaseAttributeConverter;

/**
 * 
 * Entity - 会员注册项 实体类. 
 * Created on 2015-8-26 上午11:34:37 
 * @author 施长成
 */
@Entity
@Table(name = "t_member_attribute")
public class MemberAttribute extends OrderEntity<Long> {

	private static final long serialVersionUID = 2790591852958652416L;

	/**
	 * 类型
	 */
	public enum Type {

		/** 姓名 */
		name,

		/** 性别 */
		gender,

		/** 出生日期 */
		birth,

		/** 地区 */
		area,

		/** 地址 */
		address,

		/** 邮编 */
		zipCode,

		/** 电话 */
		phone,

		/** 手机 */
		mobile,

		/** 文本 */
		text,

		/** 单选项 */
		select,

		/** 多选项 */
		checkbox
	}

	/** 名称 */
	private String name;

	/** 类型 */
	private MemberAttribute.Type type;

	/** 配比 */
	private String pattern;

	/** 是否启用 */
	private Boolean isEnabled;

	/** 是否必填 */
	private Boolean isRequired;

	/** 属性序号 */
	private Integer propertyIndex;

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
	 * 获取类型
	 * 
	 * @return 类型
	 */
	@NotNull(groups = Save.class)
	@Column(name="f_type",nullable = false, updatable = false)
	public MemberAttribute.Type getType() {
		return type;
	}

	/**
	 * 设置类型
	 * 
	 * @param type
	 *            类型
	 */
	public void setType(MemberAttribute.Type type) {
		this.type = type;
	}

	/**
	 * 获取配比
	 * 
	 * @return 配比
	 */
	@Length(max = 200)
	@Column(name="f_pattern")
	public String getPattern() {
		return pattern;
	}

	/**
	 * 设置配比
	 * 
	 * @param pattern
	 *            配比
	 */
	public void setPattern(String pattern) {
		this.pattern = pattern;
	}

	/**
	 * 获取是否启用
	 * 
	 * @return 是否启用
	 */
	@NotNull
	@Column(name="f_is_enabled",nullable = false)
	public Boolean getIsEnabled() {
		return isEnabled;
	}

	/**
	 * 设置是否启用
	 * 
	 * @param isEnabled
	 *            是否启用
	 */
	public void setIsEnabled(Boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

	/**
	 * 获取是否必填
	 * 
	 * @return 是否必填
	 */
	@NotNull
	@Column(name="f_is_required",nullable = false)
	public Boolean getIsRequired() {
		return isRequired;
	}

	/**
	 * 设置是否必填
	 * 
	 * @param isRequired
	 *            是否必填
	 */
	public void setIsRequired(Boolean isRequired) {
		this.isRequired = isRequired;
	}

	/**
	 * 获取属性序号
	 * 
	 * @return 属性序号
	 */
	@Column(name="f_property_index",updatable = false)
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
	 * 获取可选项
	 * 
	 * @return 可选项
	 */
	@Column(name="f_options",length = 4000)
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

	@Converter
	public static class OptionConverter extends BaseAttributeConverter<List<String>> implements AttributeConverter<Object, String> {
	}

}