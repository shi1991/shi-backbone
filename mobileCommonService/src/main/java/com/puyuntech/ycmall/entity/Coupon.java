package com.puyuntech.ycmall.entity;

import groovy.lang.Binding;
import groovy.lang.GroovyShell;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.PreRemove;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * 
 * Entity - 优惠券 . Created on 2015-8-27 下午2:44:35
 * 
 * @author 施长成
 * 
 *         优惠劵和订单是多对多的关系 促销和优惠劵也是一对多的关系 优惠劵和订单关系是多对多
 * 
 */
@Entity
@Table(name = "t_coupon")
public class Coupon extends BaseEntity<Long> {

	private static final long serialVersionUID = -3892157531964708657L;

	/** 名称 */
	private String name;

	/** 前缀 */
	private String prefix;

	/** 使用起始日期 */
	private Date beginDate;

	/** 使用结束日期 */
	private Date endDate;

	/** 最小商品价格 */
	private BigDecimal minimumPrice;

	/** 价格运算表达式 */
	private String priceExpression;

	/** 是否启用 */
	private Boolean isEnabled;

	/** 优惠劵类型 1：本平台优惠券 2：第三方优惠券 **/
	private int couponType;

	/** 优惠劵图片 **/
	private String image;
	
	/** 折扣价格 **/
	private BigDecimal countPrice;
	
	/** 总数 */
	private Integer gross;
	
	/** 剩余 */
	private Integer residue;

	/** 优惠码 */
	private Set<CouponCode> couponCodes = new HashSet<CouponCode>();

	/** 订单 */
	private List<Order> orders = new ArrayList<Order>();

	/** 商品 */
	private Set<Product> products = new HashSet<Product>();


	/** 介绍 */
	private String introduction;

	/**
	 * 获取名称
	 * 
	 * @return 名称
	 */
	@NotEmpty
	@Length(max = 200)
	@Column(name = "f_name", nullable = false)
	public String getName() {
		return name;
	}

	/**
	 * 设置名称
	 * 
	 * @param name
	 *            名称
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 获取前缀
	 * 
	 * @return 前缀
	 */
	@NotEmpty
	@Length(max = 200)
	@Column(name = "f_prefix", nullable = false)
	public String getPrefix() {
		return prefix;
	}

	/**
	 * 设置前缀
	 * 
	 * @param prefix
	 *            前缀
	 */
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	/**
	 * 获取使用起始日期
	 * 
	 * @return 使用起始日期
	 */
	@Column(name = "f_begin_date")
	public Date getBeginDate() {
		return beginDate;
	}

	/**
	 * 设置使用起始日期
	 * 
	 * @param beginDate
	 *            使用起始日期
	 */
	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	/**
	 * 获取使用结束日期
	 * 
	 * @return 使用结束日期
	 */
	@Column(name = "f_end_date")
	public Date getEndDate() {
		return endDate;
	}

	/**
	 * 设置使用结束日期
	 * 
	 * @param endDate
	 *            使用结束日期
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	/**
	 * 获取最小商品价格
	 * 
	 * @return 最小商品价格
	 */
	@Min(0)
	@Digits(integer = 12, fraction = 3)
	@Column(name = "f_minimum_price", precision = 21, scale = 6)
	public BigDecimal getMinimumPrice() {
		return minimumPrice;
	}

	/**
	 * 设置最小商品价格
	 * 
	 * @param minimumPrice
	 *            最小商品价格
	 */
	public void setMinimumPrice(BigDecimal minimumPrice) {
		this.minimumPrice = minimumPrice;
	}

	/**
	 * 获取价格运算表达式
	 * 
	 * @return 价格运算表达式
	 */
	@Column(name = "f_price_expression")
	public String getPriceExpression() {
		return priceExpression;
	}

	/**
	 * 设置价格运算表达式
	 * 
	 * @param priceExpression
	 *            价格运算表达式
	 */
	public void setPriceExpression(String priceExpression) {
		this.priceExpression = priceExpression;
	}

	/**
	 * 获取是否启用
	 * 
	 * @return 是否启用
	 */
	@NotNull
	@Column(name = "f_is_enabled", nullable = false)
	public Boolean getIsEnabled() {
		return isEnabled;
	}

	/**
	 * 设置是否启用
	 * 
	 * @param isEnabled
	 *            是否启用
	 */
	public void setIsEnabled(Boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

	@Column(name = "f_coupon_type")
	public int getCouponType() {
		return couponType;
	}

	public void setCouponType(int couponType) {
		this.couponType = couponType;
	}

	@Column(name = "f_image")
	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	@Column(name = "f_count_price")
	public BigDecimal getCountPrice() {
		return countPrice;
	}

	public void setCountPrice(BigDecimal countPrice) {
		this.countPrice = countPrice;
	}

	/**
	 * 获取优惠码
	 * 
	 * @return 优惠码
	 */
	@OneToMany(mappedBy = "coupon", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	public Set<CouponCode> getCouponCodes() {
		return couponCodes;
	}

	/**
	 * 设置优惠码
	 * 
	 * @param couponCodes
	 *            优惠码
	 */
	public void setCouponCodes(Set<CouponCode> couponCodes) {
		this.couponCodes = couponCodes;
	}

	/**
	 * 获取订单
	 * 
	 * @return 订单
	 */
	@ManyToMany(mappedBy = "coupons", fetch = FetchType.LAZY)
	public List<Order> getOrders() {
		return orders;
	}

	/**
	 * 设置订单
	 * 
	 * @param orders
	 *            订单
	 */
	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}

	/**
	 * 获取介绍
	 * 
	 * @return 介绍
	 */
	@Lob
	@Column(name = "f_introduction")
	public String getIntroduction() {
		return introduction;
	}

	/**
	 * 设置介绍
	 * 
	 * @param introduction
	 *            介绍
	 */
	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	/**
	 * 
	 * 获取商品.
	 * 
	 * @return 商品
	 */
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "t_coupon_product", joinColumns = { @JoinColumn(name = "f_coupons", referencedColumnName = "f_id") }, inverseJoinColumns = { @JoinColumn(name = "f_products", referencedColumnName = "f_id") })
	public Set<Product> getProducts() {
		return products;
	}

	/**
	 * 
	 * 设置商品.
	 * 
	 * @param products
	 *            商品
	 */
	public void setProducts(Set<Product> products) {
		this.products = products;
	}

	/**
	 * 
	 * 获取总数
	 * 
	 * @return 总数
	 */
	@Column(name = "f_gross", nullable = false)
	public Integer getGross() {
		return gross;
	}

	public void setGross(Integer gross) {
		this.gross = gross;
	}
	
	/**
	 * 
	 * 获取剩余.
	 * 
	 * @return 剩余数量
	 */
	@Column(name = "f_residue", nullable = false)
	public Integer getResidue() {
		return residue;
	}

	public void setResidue(Integer residue) {
		this.residue = residue;
	}
	/**
	 * 判断是否已开始
	 * 
	 * @return 是否已开始
	 */
	@Transient
	public boolean hasBegun() {
		return getBeginDate() == null || !getBeginDate().after(new Date());
	}

	/**
	 * 判断是否已过期
	 * 
	 * @return 是否已过期
	 */
	@Transient
	public boolean hasExpired() {
		return getEndDate() != null && !getEndDate().after(new Date());
	}

	/**
	 * 计算优惠价格
	 * 
	 * @param price
	 *            商品价格
	 * @param quantity
	 *            商品数量
	 * @return 优惠价格
	 */
	@Transient
	public BigDecimal calculatePrice(BigDecimal price, Integer quantity) {
		if (price == null || quantity == null
				|| StringUtils.isEmpty(getPriceExpression())) {
			return price;
		}
		BigDecimal result = BigDecimal.ZERO;
		try {
			Binding binding = new Binding();
			binding.setVariable("quantity", quantity);
			binding.setVariable("price", price);
			GroovyShell groovyShell = new GroovyShell(binding);
			result = new BigDecimal(groovyShell.evaluate(getPriceExpression())
					.toString());
		} catch (Exception e) {
			System.out.println( e );
			return price;
		}
		if (result.compareTo(price) > 0) {
			return price;
		}
		return result.compareTo(BigDecimal.ZERO) > 0 ? result : BigDecimal.ZERO;
	}

	/**
	 * 删除前处理
	 */
	@PreRemove
	public void preRemove() {
		List<Order> orders = getOrders();
		if (orders != null) {
			for (Order order : orders) {
				order.getCoupons().remove(this);
			}
		}
	}

}
