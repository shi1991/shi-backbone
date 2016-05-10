package com.puyuntech.ycmall.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 *  Entity - 日志 实体类. 
 * Created on 2015-8-26 下午17:45:30
 * @author 严志森
 */
@Entity
@Table(name = "t_log")
public class Log extends BaseEntity<Long> {

	private static final long serialVersionUID = -7186140456087433196L;

	/** "日志内容"属性名称 */
	public static final String LOG_CONTENT_ATTRIBUTE_NAME = Log.class.getName() + ".CONTENT";

	/** 操作 */
	private String operation;

	/** 操作员 */
	private String operator;

	/** 内容 */
	private String content;

	/** 请求参数 */
	private String parameter;

	/** IP */
	private String ip;

	/**
	 * 获取操作
	 * 
	 * @return 操作
	 */
	@Column(name="f_operation",nullable = false, updatable = false)
	public String getOperation() {
		return operation;
	}

	/**
	 * 设置操作
	 * 
	 * @param operation
	 *            操作
	 */
	public void setOperation(String operation) {
		this.operation = operation;
	}

	/**
	 * 获取操作员
	 * 
	 * @return 操作员
	 */
	@Column(name="f_operator",updatable = false)
	public String getOperator() {
		return operator;
	}

	/**
	 * 设置操作员
	 * 
	 * @param operator
	 *            操作员
	 */
	public void setOperator(String operator) {
		this.operator = operator;
	}

	/**
	 * 获取内容
	 * 
	 * @return 内容
	 */
	@Column(name="f_content",updatable = false, length = 4000)
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
	 * 获取请求参数
	 * 
	 * @return 请求参数
	 */
	@Lob
	@Column(name="f_parameter",updatable = false)
	public String getParameter() {
		return parameter;
	}

	/**
	 * 设置请求参数
	 * 
	 * @param parameter
	 *            请求参数
	 */
	public void setParameter(String parameter) {
		this.parameter = parameter;
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
	 * 设置操作员
	 * 
	 * @param operator
	 *            操作员
	 */
	@Transient
	public void setOperator(Admin operator) {
		setOperator(operator != null ? operator.getWebUsername() : null);
	}

}
