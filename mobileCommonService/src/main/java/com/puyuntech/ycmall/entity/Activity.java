package com.puyuntech.ycmall.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.DateBridge;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Resolution;
import org.hibernate.search.annotations.Store;

@Entity
@Table(name="t_activity")
public class Activity extends OrderEntity<Long> {

	private static final long serialVersionUID = -1363758947829163207L;
	
	/** 活动标题 */
	private String title;
	
	/** 活动内容 */
	private String content;
	
	/** 活动logo */
	private String logo;
	
	/** 活动开始时间 */
	private Date beginDate;
	
	/** 活动结束时间 */
	private Date endDate;
	
	/** 活动门店  */
	private Organization organization;
	
	/** 活动创建人 **/
	private Admin admin;
	
	@Column(name="f_title",nullable = false)
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	@Column(name="f_content",nullable = false)
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	@Column(name="f_logo",nullable = false)
	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}
	
	@Field(store = Store.YES, index = Index.YES, analyze = Analyze.NO)
	@DateBridge(resolution = Resolution.SECOND)
	@Column(name="f_begin_date",nullable = false)
	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}
	
	@Field(store = Store.YES, index = Index.YES, analyze = Analyze.NO)
	@DateBridge(resolution = Resolution.SECOND)
	@Column(name="f_end_date",nullable = false)
	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	/**
	 * 活动门店
	 * @return
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "f_active_organization")
	public Organization getOrganization() {
		return organization;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
	}

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "f_admin")
	public Admin getAdmin() {
		return admin;
	}

	public void setAdmin(Admin admin) {
		this.admin = admin;
	}

}