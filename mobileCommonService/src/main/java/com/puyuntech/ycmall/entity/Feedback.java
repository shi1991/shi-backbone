package com.puyuntech.ycmall.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 
 * Entity - 反馈 . 
 * Created on 2015-10-20 下午1:45:17 
 * @author 严志森
 * 	
 */
@Entity
@Table(name = "t_feedback")
public class Feedback extends BaseEntity<Long> {

	private static final long serialVersionUID = -7835338301374767341L;

	/** 时间点*/
	private Date feedbackTime;
	
	/** 反馈类型 1:购物问题，2:预订单相关 3:账号问题/投诉建议 4:其他**/
	private Integer feedbackType;
	
	/** 标题 */
	private String title;
	
	/** 处理状态 0:未处理 	1:已处理**/
	private enum handleType{
		
		/** 未处理 */
		untreated,
		
		/** 已处理 */
		treated
	}
	/** 处理状态 0:未处理 	1:已处理**/
	private Feedback.handleType handleType;
	
	/** 处理时间点*/
	private Date handleTime;
	
	/** 反馈内容*/
	private String content;
	
	/** 批注内容*/
	private String handleInfo;
	
	/** 反馈用户 */
	private Member member;
	
	/** 解决反馈管理 */
	private Admin admin;
	
	
	
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="f_member",nullable = false, updatable = false)
	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="f_admin",nullable = true, updatable = false)
	public Admin getAdmin() {
		return admin;
	}

	public void setAdmin(Admin admin) {
		this.admin = admin;
	}
	
	/**
	 * 获取时间点
	 * 
	 * @return 时间点
	 */
	@Column(name="f_feedback_Time",nullable = true)
	public Date getFeedbackTime() {
		return feedbackTime;
	}

	public void setFeedbackTime(Date feedbackTime) {
		this.feedbackTime = feedbackTime;
	}
	
	/**
	 * 获取 反馈类型
	 * 
	 * @return  1:购物问题，2:预订单相关 3:账号问题/投诉建议 4:其他
	 */
	@Column(name="f_feedback_Type",nullable = false)
	public Integer getFeedbackType() {
		return feedbackType;
	}

	public void setFeedbackType(Integer feedbackType) {
		this.feedbackType = feedbackType;
	}
	
	/**
	 * 获取 标题
	 * 
	 * @return  标题
	 */
	@Column(name="f_title",nullable = false)
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	/**
	 * 获取处理时间点
	 * 
	 * @return 处理时间点
	 */
	@Column(name="f_handle_Time",nullable = true)
	public Date getHandleTime() {
		return handleTime;
	}

	public void setHandleTime(Date handleTime) {
		this.handleTime = handleTime;
	}
	
	/**
	 * 获取内容
	 * 
	 * @return 内容
	 */
	@Column(name="f_content",nullable = false)
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	/**
	 * 获取批注内容
	 * 
	 * @return 批注内容
	 */
	@Column(name="f_handle_Info",nullable = true)
	public String getHandleInfo() {
		return handleInfo;
	}

	public void setHandleInfo(String handleInfo) {
		this.handleInfo = handleInfo;
	}
	
	/**
	 * 获取  处理状态 
	 * 
	 * @return 0:未处理 	1:已处理
	 */
	@Column(name="f_handle_Type",nullable = true)
	public Feedback.handleType getHandleType() {
		return handleType;
	}

	public void setHandleType(Feedback.handleType handleType) {
		this.handleType = handleType;
	}

	
	
}
