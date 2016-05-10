package com.puyuntech.ycmall.entity.value;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.hibernate.validator.constraints.Length;

/**
 * 
 * 发票中间Entity . 
 * Created on 2015-8-27 下午4:29:52 
 * @author 施长成
 */
@Embeddable
public class Invoice implements Serializable {

	private static final long serialVersionUID = 6414871177290711770L;

	/** 抬头 */
	private String title;

	/** 内容 */
	private String content;

	/**
	 * 构造方法
	 */
	public Invoice() {
	}

	/**
	 * 构造方法
	 * 
	 * @param title
	 *            抬头
	 * @param content
	 *            内容
	 */
	public Invoice(String title, String content) {
		this.title = title;
		this.content = content;
	}

	/**
	 * 获取抬头
	 * 
	 * @return 抬头
	 */
	@Length(max = 200)
	@Column(name = "f_invoice_title")
	public String getTitle() {
		return title;
	}

	/**
	 * 设置抬头
	 * 
	 * @param title
	 *            抬头
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * 获取内容
	 * 
	 * @return 内容
	 */
	@Length(max = 200)
	@Column(name = "f_invoice_content")
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

}
