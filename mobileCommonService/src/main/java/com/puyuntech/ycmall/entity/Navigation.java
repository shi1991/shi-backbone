package com.puyuntech.ycmall.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Entity - 导航
 * 
 * Created on 2015-8-26 下午17:50:30
 * @author 严志森
 */
@Entity
@Table(name = "t_navigation")
public class Navigation extends OrderEntity<Long> {

	private static final long serialVersionUID = -1697934895783334047L;

	/**
	 * 位置
	 */
	public enum Position {

		/** 顶部 */
		top,

		/** 中间 */
		middle,

		/** 底部 */
		bottom
	}

	/** 名称 */
	private String name;

	/** 位置 */
	private Navigation.Position position;

	/** 链接地址 */
	private String url;

	/** 是否新窗口打开 */
	private Boolean isBlankTarget;

	/**
	 * 获取名称
	 * 
	 * @return 名称
	 */
	@NotEmpty
	@Length(max = 255)
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
	 * 获取位置
	 * 
	 * @return 位置
	 */
	@NotNull
	@Column(name="f_position",nullable = false)
	public Navigation.Position getPosition() {
		return position;
	}

	/**
	 * 设置位置
	 * 
	 * @param position
	 *            位置
	 */
	public void setPosition(Navigation.Position position) {
		this.position = position;
	}

	/**
	 * 获取链接地址
	 * 
	 * @return 链接地址
	 */
	@NotEmpty
	@Length(max = 255)
	@Pattern(regexp = "^(?i)(http:\\/\\/|https:\\/\\/|ftp:\\/\\/|mailto:|\\/|#).*$")
	@Column(name="f_url",nullable = false)
	public String getUrl() {
		return url;
	}

	/**
	 * 设置链接地址
	 * 
	 * @param url
	 *            链接地址
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * 获取是否新窗口打开
	 * 
	 * @return 是否新窗口打开
	 */
	@NotNull
	@Column(name="f_is_blank_target",nullable = false)
	public Boolean getIsBlankTarget() {
		return isBlankTarget;
	}

	/**
	 * 设置是否新窗口打开
	 * 
	 * @param isBlankTarget
	 *            是否新窗口打开
	 */
	public void setIsBlankTarget(Boolean isBlankTarget) {
		this.isBlankTarget = isBlankTarget;
	}

}
