package com.puyuntech.ycmall.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * 
 *  快递单模版 . 
 * Created on 2015-8-27 下午5:35:44 
 * @author 施长成
 */
@Entity
@Table(name = "t_delivery_template")
public class DeliveryTemplate extends BaseEntity<Long> {

	private static final long serialVersionUID = -4702934510768910316L;

	/** 名称 */
	private String name;

	/** 内容 */
	private String content;

	/** 宽度 */
	private Integer width;

	/** 高度 */
	private Integer height;

	/** 偏移量X */
	private Integer offsetX;

	/** 偏移量Y */
	private Integer offsetY;

	/** 背景图 */
	private String background;

	/** 是否默认 */
	private Boolean isDefault;

	/** 备注 */
	private String memo;

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
	 * 获取内容
	 * 
	 * @return 内容
	 */
	@NotEmpty
	@Lob
	@Column(name="f_content",nullable = false)
	public String getContent() {
		return content;
	}

	/**
	 * 设置内容
	 * 
	 * @param content
	 *            内容
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * 获取宽度
	 * 
	 * @return 宽度
	 */
	@NotNull
	@Min(1)
	@Column(name="f_width",nullable = false)
	public Integer getWidth() {
		return width;
	}

	/**
	 * 设置宽度
	 * 
	 * @param width
	 *            宽度
	 */
	public void setWidth(Integer width) {
		this.width = width;
	}

	/**
	 * 获取高度
	 * 
	 * @return 高度
	 */
	@NotNull
	@Min(1)
	@Column(name="f_height",nullable = false)
	public Integer getHeight() {
		return height;
	}

	/**
	 * 设置高度
	 * 
	 * @param height
	 *            高度
	 */
	public void setHeight(Integer height) {
		this.height = height;
	}

	/**
	 * 获取偏移量X
	 * 
	 * @return 偏移量X
	 */
	@NotNull
	@Column(name="f_offset_x",nullable = false)
	public Integer getOffsetX() {
		return offsetX;
	}

	/**
	 * 设置偏移量X
	 * 
	 * @param offsetX
	 *            偏移量X
	 */
	public void setOffsetX(Integer offsetX) {
		this.offsetX = offsetX;
	}

	/**
	 * 获取偏移量Y
	 * 
	 * @return 偏移量Y
	 */
	@NotNull
	@Column(name="f_offset_y",nullable = false)
	public Integer getOffsetY() {
		return offsetY;
	}

	/**
	 * 设置偏移量Y
	 * 
	 * @param offsetY
	 *            偏移量Y
	 */
	public void setOffsetY(Integer offsetY) {
		this.offsetY = offsetY;
	}

	/**
	 * 获取背景图
	 * 
	 * @return 背景图
	 */
	@Length(max = 200)
	@Pattern(regexp = "^(?i)(http:\\/\\/|https:\\/\\/|\\/).*$")
	@Column(name="f_background")
	public String getBackground() {
		return background;
	}

	/**
	 * 设置背景图
	 * 
	 * @param background
	 *            背景图
	 */
	public void setBackground(String background) {
		this.background = background;
	}

	/**
	 * 获取是否默认
	 * 
	 * @return 是否默认
	 */
	@NotNull
	@Column(name="f_is_default",nullable = false)
	public Boolean getIsDefault() {
		return isDefault;
	}

	/**
	 * 设置是否默认
	 * 
	 * @param isDefault
	 *            是否默认
	 */
	public void setIsDefault(Boolean isDefault) {
		this.isDefault = isDefault;
	}

	/**
	 * 获取备注
	 * 
	 * @return 备注
	 */
	@Length(max = 200)
	@Column(name="f_memo")
	public String getMemo() {
		return memo;
	}

	/**
	 * 设置备注
	 * 
	 * @param memo
	 *            备注
	 */
	public void setMemo(String memo) {
		this.memo = memo;
	}

}
