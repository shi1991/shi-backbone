package com.puyuntech.ycmall.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "t_phone_number")
public class PhoneNumber extends BaseEntity<Long> {

	private static final long serialVersionUID = 8933533399915518490L;

	/**
     * 手机号码当前状态
     * 0 未售出
     * 1 已售出
     * 2 被锁定帐号
     */
    public enum  PHONESTATE{
        unsold,
        sold,
        locked
    }
	
	/** 超时时间 */
	public static final int TIMEOUT = 900;

	/** "密钥"Cookie名称 */
	public static final String KEY_COOKIE_NAME = "phoneKey";;

	/** 号码 */
	private String number;
	
	/** 销售价 */
	private BigDecimal price;
	
	/** 包含话费 */
	private BigDecimal telFare;
	
	/** 运营商 */
	private Operator operator;
	
	/** 当前号码状态 */
	private PHONESTATE isSold;

    /** 用户未登录时 使用唯一token **/
    private String phoneKey;

    /** token 的失效时间 **/
    private Date expire;
    
    /** 号码锁定的会员信息 **/
    private Member member;
    
	/**
	 * 
	 * 获取号码.
	 * 
	 * @return
	 */
	@Min(0)
	@Column(name="f_number",nullable = false)
	public String getNumber() {
		return number;
	}

	/**
	 * 设置号码.
	 * 
	 * @param number
	 *            号码
	 */
	public void setNumber(String number) {
		this.number = number;
	}

	/**
	 * 获取价格.
	 * 
	 * @return 价格
	 */
	@Min(0)
	@Digits(integer = 12, fraction = 3)
	@Column(name="f_price",precision = 21, scale = 6)
	public BigDecimal getPrice() {
		return price;
	}

	/**
	 * 设置价格.
	 * 
	 * @param price
	 *            价格
	 */
	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	/**
	 * 获取包含话费.
	 * 
	 * @return 话费
	 */
	@Min(0)
	@Digits(integer = 12, fraction = 3)
	@Column(name="f_tel_fare",precision = 21, scale = 6)
	public BigDecimal getTelFare() {
		return telFare;
	}

	/**
	 * 设置话费.
	 * 
	 * @param telFare
	 *            话费
	 */
	public void setTelFare(BigDecimal telFare) {
		this.telFare = telFare;
	}

	/**
	 * 获取运营商.
	 * 
	 * @return 运营商
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="f_operator")
	@JsonBackReference
	public Operator getOperator() {
		return operator;
	}

	/**
	 * 设置运营商.
	 * 
	 * @param operator
	 *            运营商
	 */
	public void setOperator(Operator operator) {
		this.operator = operator;
	}

    /**
     * 获取是否已售.
     *
     * @return 是否已售
     */
    @NotNull
    @Column(name="f_is_sold",nullable = false)
    public PHONESTATE getIsSold() {
        return isSold;
    }

    /**
     * 设置是否已售.
     *
     * @param isSold
     *            是否已售
     */
    public void setIsSold(PHONESTATE isSold) {
        this.isSold = isSold;
    }

 
    
    @Column(name = "f_phone_key")
	public String getPhoneKey() {
		return phoneKey;
	}

	public void setPhoneKey(String phoneKey) {
		this.phoneKey = phoneKey;
	}

	@Column(name="f_expire")
	public Date getExpire() {
		return expire;
	}

	public void setExpire(Date expire) {
		this.expire = expire;
	}

	@OneToOne
	@JoinColumn(name="f_member")
	@JsonIgnore
	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}
    
    
}
