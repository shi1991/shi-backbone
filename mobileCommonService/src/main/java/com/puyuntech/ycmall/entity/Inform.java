package com.puyuntech.ycmall.entity;

import javax.persistence.*;
import java.util.Set;

/**
 * 
 *  门店公告. 
 * Created on 2015-12-11 下午1:17:37 
 * @author 王凯斌
 */
@Entity
@Table(name="t_inform")
public class Inform extends BaseEntity<Long> {

	private static final long serialVersionUID = 5161087593501679378L;

	//公告标题
	private String title;
	
	//公告内容
	private String content;
	
	//是否发布
	private Boolean isDisplay;
	
	//创建人
	private Admin creater;
	
	//关联组织
	private Set<Organization> organizations;

	@Column(name="f_title",nullable=false)
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name="f_content",nullable=false)
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Column(name="f_is_display",nullable=false)
	public Boolean getIsDisplay() {
		return isDisplay;
	}

	public void setIsDisplay(Boolean isDisplay) {
		this.isDisplay = isDisplay;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="f_creater")
	public Admin getCreater() {
		return creater;
	}

	public void setCreater(Admin creater) {
		this.creater = creater;
	}

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "t_organization_inform", joinColumns = { @JoinColumn(name = "f_inform", referencedColumnName = "f_id") }, inverseJoinColumns = { @JoinColumn(name = "f_organization", referencedColumnName = "f_id") })
	@OrderBy("order asc")
	public Set<Organization> getOrganizations() {
		return organizations;
	}

	public void setOrganizations(Set<Organization> organizations) {
		this.organizations = organizations;
	}
	
	
}
