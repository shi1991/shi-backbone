package com.puyuntech.ycmall.entity;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * 
 * 门店和店员评论  Entity. 
 * Created on 2015-12-25 上午10:35:22 
 * @author 严志森
 */
@Entity
@Table(name = "t_comment")
public class Comment extends BaseEntity<Long> {

	private static final long serialVersionUID = -7240719597207272403L;

	/**
	 * 类型
	 */
	public enum Type {

		/** 其他 */
		other,

		/** 门店 */
		organization,

		/** 店员 */
		admin
	}

	/** 评分 */
	private Integer score;

	/** 内容 */
	private String content;

	/** 是否显示 */
	private Boolean isShow;

	/** IP */
	private String ip;

	/** 会员 */
	private Member member;

	/** 门店 */
	private Organization organization;
	
	/** 店员 */
	private Admin admin;
	
	/** 类型 */
	private Comment.Type type;
	
	/** 评论时间 */
	private Date reviewDate;
	
	/**
	 * 获取评分
	 * 
	 * @return 评分
	 */
	@NotNull
	@Min(1)
	@Max(5)
	@Column(name="f_score",nullable = false, updatable = false)
	public Integer getScore() {
		return score;
	}

	/**
	 * 设置评分
	 * 
	 * @param score
	 *            评分
	 */
	public void setScore(Integer score) {
		this.score = score;
	}

	/**
	 * 获取内容
	 * 
	 * @return 内容
	 */
	@NotEmpty
	@Length(max = 200)
	@Column(name="f_content",nullable = false, updatable = false)
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
	 * 获取是否显示
	 * 
	 * @return 是否显示
	 */
	@Column(name="f_is_show",nullable = false)
	public Boolean getIsShow() {
		return isShow;
	}

	/**
	 * 设置是否显示
	 * 
	 * @param isShow
	 *            是否显示
	 */
	public void setIsShow(Boolean isShow) {
		this.isShow = isShow;
	}

	/**
	 * 获取IP
	 * 
	 * @return IP
	 */
	@Column(name="f_ip",nullable = false, updatable = false)
	public String getIp() {
		return ip;
	}

	/**
	 * 设置IP
	 * 
	 * @param ip
	 *            IP
	 */
	public void setIp(String ip) {
		this.ip = ip;
	}

	/**
	 * 获取会员
	 * 
	 * @return 会员
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="f_member",updatable = false)
	public Member getMember() {
		return member;
	}

	/**
	 * 设置会员
	 * 
	 * @param member
	 *            会员
	 */
	public void setMember(Member member) {
		this.member = member;
	}

	/**
	 * 获取评论时间
	 * 
	 * @return 评论时间
	 */
	@Column(name="f_create_date",insertable= false , updatable = false)
	public Date getReviewDate() {
		return reviewDate;
	}
	
	/**
	 * 
	 * 设置评论时间.
	 * 
	 * @param reviewDate 评论时间
	 */
	public void setReviewDate(Date reviewDate) {
		this.reviewDate = reviewDate;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="f_organization", updatable = false)
	public Organization getOrganization() {
		return organization;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="f_admin", updatable = false)
	public Admin getAdmin() {
		return admin;
	}

	public void setAdmin(Admin admin) {
		this.admin = admin;
	}
	
	@Column(name="f_type",nullable = false)
	public Comment.Type getType() {
		return type;
	}

	public void setType(Comment.Type type) {
		this.type = type;
	}
	
	
	
}
