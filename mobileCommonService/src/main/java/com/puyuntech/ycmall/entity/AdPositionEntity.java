package com.puyuntech.ycmall.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * 
 * Entity - 广告位. 
 * Created on 2015-8-24 下午5:19:36 
 * @author Liaozhen
 */
@Entity
@Table(name = "t_ad_position")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "seq_ad_position")
public class AdPositionEntity extends BaseEntity<Long> {

	private static final long serialVersionUID = -6120242745997111553L;
	
	/** 名称 */
	private String name;

	/** 宽度 */
	private Integer width;

	/** 高度 */
	private Integer height;

	/** 描述 */
	private String description;

	/** 模板 */
	private String template;

	/** 广告 */
	private Set<AdEntity> ads = new HashSet<AdEntity>();

	/** 是否在移动端显示 **/
	private String showMobile;
	
	/**
	 * 获取名称
	 * 
	 * @return 名称
	 */
	@NotEmpty
	@Length(max = 200)
	@Column(name = "f_name", nullable = false)
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
	 * 获取宽度
	 * 
	 * @return 宽度
	 */
	@Column(name = "f_width", nullable = false)
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
	@Column(name = "f_height", nullable = false)
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
	 * 获取描述
	 * 
	 * @return 描述
	 */
	@Length(max = 200)
	@Column(name = "f_description")
	public String getDescription() {
		return description;
	}

	/**
	 * 设置描述
	 * 
	 * @param description
	 *            描述
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * 获取模板
	 * 
	 * @return 模板
	 */
	@Column(name = "f_template", nullable = false)
	public String getTemplate() {
		return template;
	}

	/**
	 * 设置模板
	 * 
	 * @param template
	 *            模板
	 */
	public void setTemplate(String template) {
		this.template = template;
	}

	/**
	 * 获取是否移动端显示
	 * 
	 * @return PC端0；移动端1；POS端2
	 * 
	 * 注：不确定该字段是否有用
	 */
	@Length(max = 200)
	@Column(name = "f_position_type")
	public String getPositionType() {
		return showMobile;
	}

	/**
	 * 设置是否移动端显示
	 * 
	 * @param showMobile
	 *            是否移动端显示
	 *           
	 * 注：不确定该字段是否有用
	 */
	public void setPositionType(String showMobile) {
		this.showMobile = showMobile;
	}
	
	/**
	 * 获取广告
	 * 
	 * @return 广告
	 */
	@OneToMany(mappedBy = "adPosition", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
	@OrderBy("order asc")
	public Set<AdEntity> getAds() {
		return ads;
	}

	/**
	 * 设置广告
	 * 
	 * @param ads
	 *            广告
	 */
	public void setAds(Set<AdEntity> ads) {
		this.ads = ads;
	}

}
