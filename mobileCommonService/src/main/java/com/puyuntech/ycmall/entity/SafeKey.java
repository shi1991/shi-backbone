package com.puyuntech.ycmall.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Transient;

/**
 * Entity - 安全密钥. 
 * Created on 2015-8-26 上午11:06:10 
 * @author 施长成
 * 
 * @Embeddable 表示 此类可以被插入某个entity中
 */
@Embeddable
public class SafeKey implements Serializable {

	private static final long serialVersionUID = -4197734320791343341L;

	/** 密钥 */
	private String value;

	/** 过期时间 */
	private Date expire;

	/**
	 * 获取密钥
	 * 
	 * @return 密钥
	 */
	@Column(name = "f_safe_key_value")
	public String getValue() {
		return value;
	}

	/**
	 * 设置密钥
	 * 
	 * @param value
	 *            密钥
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * 获取过期时间
	 * 
	 * @return 过期时间
	 */
	@Column(name = "f_safe_key_expire")
	public Date getExpire() {
		return expire;
	}

	/**
	 * 设置过期时间
	 * 
	 * @param expire
	 *            过期时间
	 */
	public void setExpire(Date expire) {
		this.expire = expire;
	}

	/**
	 * 判断是否已过期
	 * 
	 * @return 是否已过期
	 */
	@Transient
	public boolean hasExpired() {
		return getExpire() != null && !getExpire().after(new Date());
	}

}
