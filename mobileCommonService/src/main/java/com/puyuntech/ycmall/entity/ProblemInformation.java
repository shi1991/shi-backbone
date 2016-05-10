package com.puyuntech.ycmall.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * 
 * 问题内容表. 
 * Created on 2015-11-20 上午10:37:42 
 * @author 严志森
 */
@Entity
@Table(name = "t_problem_information")
public class ProblemInformation extends OrderEntity<Long> {

	private static final long serialVersionUID = -2350527995098278552L;

	private String title;
	
	private String desc;
	
	private ProblemClassification problemClassification;
	
	/**
	 * 获取标题
	 * 
	 * @return 标题
	 */
	@NotEmpty
	@Length(max = 200)
	@Column(name="f_title",nullable = false)
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	/**
	 * 获取内容
	 * 
	 * @return 内容
	 */
	@NotEmpty
	@Length(max = 200)
	@Column(name="f_content",nullable = false)
	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	/**
	 * 获取绑定分类
	 * 
	 * @return 绑定分类
	 */
	@NotNull(groups = Save.class)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="f_parent",nullable = false, updatable = false)
	public ProblemClassification getProblemClassification() {
		return problemClassification;
	}

	/**
	 * 设置绑定分类
	 * 
	 * @param problemClassification
	 *            绑定分类
	 */
	public void setProblemClassification(ProblemClassification problemClassification) {
		this.problemClassification = problemClassification;
	}

	

	
	
	
}
