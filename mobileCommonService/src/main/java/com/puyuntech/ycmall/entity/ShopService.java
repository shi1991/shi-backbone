package com.puyuntech.ycmall.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="t_organization_service")
public class ShopService extends OrderEntity<Long>{

	private static final long serialVersionUID = 1L;

	/** 服务名称*/
	private String name;
	
	/** 服务描述 */
	private String description;
	
	/** 是否显示 */
	private Boolean type;

	@Column(name="f_name",nullable = false)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name="f_description",nullable = false)
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	

	@Column(name="f_type",nullable = false)
	public Boolean getType() {
		return type;
	}

	public void setType(Boolean type) {
		this.type = type;
	}

}
