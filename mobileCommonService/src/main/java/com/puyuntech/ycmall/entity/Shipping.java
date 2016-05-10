package com.puyuntech.ycmall.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.groups.Default;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.lang.BooleanUtils;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * 
 * Entity - 发货单 . 
 * Created on 2015-8-27 下午5:15:57 
 * @author 施长成
 * 
 */
@Entity
@Table(name = "t_shipping")
public class Shipping extends BaseEntity<Long> {

	private static final long serialVersionUID = -4026572396565762359L;

	/**
	 * 配送验证组
	 */
	public interface Delivery extends Default {

	}
	
	public enum Status{
		/** 已发货 **/
		delivered,
		/** 已退货 **/
		returned
		
	}
	/** 编号 */
	private String sn;
	
	/** 状态 */
	private Shipping.Status status;
	
	/** 配送方式 */
	private String shippingMethod;

	/** 物流公司 */
	private String deliveryCorp;

	/** 物流公司网址 */
	private String deliveryCorpUrl;

	/** 物流公司代码 */
	private String deliveryCorpCode;

	/** 运单号 */
	private String trackingNo;

	/** 物流费用 */
	private BigDecimal freight;

	/** 收货人 */
	private String consignee;

	/** 地区 */
	private String area;

	/** 地址 */
	private String address;

	/** 邮编 */
	private String zipCode;

	/** 电话 */
	private String phone;

	/** 操作员 */
	private String operator;

	/** 备注 */
	private String memo;

	/** 订单 */
	private Order order;

	/** 发货项 */
	private List<ShippingItem> shippingItems = new ArrayList<ShippingItem>();
	
	/** 库存记录 */
	private Set<StockLog> stockLogs = new HashSet<StockLog>();

	/**
	 * 获取编号
	 * 
	 * @return 编号
	 */
	@Column(name="f_sn",nullable = false, updatable = false, unique = true)
	public String getSn() {
		return sn;
	}

	/**
	 * 设置编号
	 * 
	 * @param sn
	 *            编号
	 */
	public void setSn(String sn) {
		this.sn = sn;
	}
	
	/**
	 * 获取状态
	 * 
	 * @return 状态
	 */
	@Column(name="f_status",nullable = false)
	public Shipping.Status getStatus() {
		return status;
	}

	/**
	 * 设置状态
	 * 
	 * @param status
	 *            状态
	 */
	public void setStatus(Shipping.Status status) {
		this.status = status;
	}
	
	/**
	 * 获取配送方式
	 * 
	 * @return 配送方式
	 */
	@Column(name="f_shipping_method",updatable = false)
	public String getShippingMethod() {
		return shippingMethod;
	}

	/**
	 * 设置配送方式
	 * 
	 * @param shippingMethod
	 *            配送方式
	 */
	public void setShippingMethod(String shippingMethod) {
		this.shippingMethod = shippingMethod;
	}

	/**
	 * 获取物流公司
	 * 
	 * @return 物流公司
	 */
	@NotEmpty(groups = Delivery.class)
	@Column(name="f_delivery_corp",updatable = false)
	public String getDeliveryCorp() {
		return deliveryCorp;
	}

	/**
	 * 设置物流公司
	 * 
	 * @param deliveryCorp
	 *            物流公司
	 */
	public void setDeliveryCorp(String deliveryCorp) {
		this.deliveryCorp = deliveryCorp;
	}

	/**
	 * 获取物流公司网址
	 * 
	 * @return 物流公司网址
	 */
	@Column(name="f_delivery_corp_url",updatable = false)
	public String getDeliveryCorpUrl() {
		return deliveryCorpUrl;
	}

	/**
	 * 设置物流公司网址
	 * 
	 * @param deliveryCorpUrl
	 *            物流公司网址
	 */
	public void setDeliveryCorpUrl(String deliveryCorpUrl) {
		this.deliveryCorpUrl = deliveryCorpUrl;
	}

	/**
	 * 获取物流公司代码
	 * 
	 * @return 物流公司代码
	 */
	@Column(name="f_delivery_corp_code",updatable = false)
	public String getDeliveryCorpCode() {
		return deliveryCorpCode;
	}

	/**
	 * 设置物流公司代码
	 * 
	 * @param deliveryCorpCode
	 *            物流公司代码
	 */
	public void setDeliveryCorpCode(String deliveryCorpCode) {
		this.deliveryCorpCode = deliveryCorpCode;
	}

	/**
	 * 获取运单号
	 * 
	 * @return 运单号
	 */
	@Length(max = 200)
	@Column(name="f_tracking_no",updatable = false)
	public String getTrackingNo() {
		return trackingNo;
	}

	/**
	 * 设置运单号
	 * 
	 * @param trackingNo
	 *            运单号
	 */
	public void setTrackingNo(String trackingNo) {
		this.trackingNo = trackingNo;
	}

	/**
	 * 获取物流费用
	 * 
	 * @return 物流费用
	 */
	@Min(0)
	@Digits(integer = 12, fraction = 3)
	@Column(name="f_freight",updatable = false, precision = 21, scale = 6)
	public BigDecimal getFreight() {
		return freight;
	}

	/**
	 * 设置物流费用
	 * 
	 * @param freight
	 *            物流费用
	 */
	public void setFreight(BigDecimal freight) {
		this.freight = freight;
	}

	/**
	 * 获取收货人
	 * 
	 * @return 收货人
	 */
	@NotEmpty(groups = Delivery.class)
	@Length(max = 200)
	@Column(name="f_consignee",updatable = false)
	public String getConsignee() {
		return consignee;
	}

	/**
	 * 设置收货人
	 * 
	 * @param consignee
	 *            收货人
	 */
	public void setConsignee(String consignee) {
		this.consignee = consignee;
	}

	/**
	 * 获取地区
	 * 
	 * @return 地区
	 */
	@NotEmpty(groups = Delivery.class)
	@Column(name="f_area",updatable = false)
	public String getArea() {
		return area;
	}

	/**
	 * 设置地区
	 * 
	 * @param area
	 *            地区
	 */
	public void setArea(String area) {
		this.area = area;
	}

	/**
	 * 获取地址
	 * 
	 * @return 地址
	 */
	@NotEmpty(groups = Delivery.class)
	@Length(max = 200)
	@Column(name="f_address",updatable = false)
	public String getAddress() {
		return address;
	}

	/**
	 * 设置地址
	 * 
	 * @param address
	 *            地址
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * 获取邮编
	 * 
	 * @return 邮编
	 */
	@NotEmpty(groups = Delivery.class)
	@Length(max = 200)
	@Pattern(regexp = "^\\d{6}$")
	@Column(name="f_zip_code",updatable = false)
	public String getZipCode() {
		return zipCode;
	}

	/**
	 * 设置邮编
	 * 
	 * @param zipCode
	 *            邮编
	 */
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	/**
	 * 获取电话
	 * 
	 * @return 电话
	 */
	@NotEmpty(groups = Delivery.class)
	@Length(max = 200)
	@Pattern(regexp = "^\\d{3,4}-?\\d{7,9}$")
	@Column(name="f_phone",updatable = false)
	public String getPhone() {
		return phone;
	}

	/**
	 * 设置电话
	 * 
	 * @param phone
	 *            电话
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * 获取操作员
	 * 
	 * @return 操作员
	 */
	@Column(name="f_operator",nullable = false, updatable = false)
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
	 * 获取备注
	 * 
	 * @return 备注
	 */
	@Length(max = 200)
	@Column(name="f_memo",updatable = false)
	public String getMemo() {
		return memo;
	}

	/**
	 * 设置备注
	 * 
	 * @param memo
	 *            备注
	 */
	public void setMemo(String memo) {
		this.memo = memo;
	}

	/**
	 * 获取订单
	 * 
	 * @return 订单
	 */
	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "f_orders", nullable = false, updatable = false)
	public Order getOrder() {
		return order;
	}

	/**
	 * 设置订单
	 * 
	 * @param order
	 *            订单
	 */
	public void setOrder(Order order) {
		this.order = order;
	}

	/**
	 * 获取发货项
	 * 
	 * @return 发货项
	 */
	@Valid
	@NotEmpty
	@OneToMany(mappedBy = "shipping", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	public List<ShippingItem> getShippingItems() {
		return shippingItems;
	}

	/**
	 * 设置发货项
	 * 
	 * @param shippingItems
	 *            发货项
	 */
	public void setShippingItems(List<ShippingItem> shippingItems) {
		this.shippingItems = shippingItems;
	}

	/**
	 * 获取数量
	 * 
	 * @return 数量
	 */
	@Transient
	public int getQuantity() {
		int quantity = 0;
		if (getShippingItems() != null) {
			for (ShippingItem shippingItem : getShippingItems()) {
				if (shippingItem != null && shippingItem.getQuantity() != null) {
					quantity += shippingItem.getQuantity();
				}
			}
		}
		return quantity;
	}

	/**
	 * 获取是否需要物流
	 * 
	 * @return 是否需要物流
	 */
	@Transient
	public boolean getIsDelivery() {
		return CollectionUtils.exists(getShippingItems(), new Predicate() {
			@Override
			public boolean evaluate(Object object) {
				ShippingItem shippingItem = (ShippingItem) object;
				return shippingItem != null && BooleanUtils.isTrue(shippingItem.getIsDelivery());
			}
		});
	}

	/**
	 * 设置配送方式
	 * 
	 * @param shippingMethod
	 *            配送方式
	 */
	@Transient
	public void setShippingMethod(ShippingMethod shippingMethod) {
		setShippingMethod(shippingMethod != null ? shippingMethod.getName() : null);
	}

	/**
	 * 设置物流公司
	 * 
	 * @param deliveryCorp
	 *            物流公司
	 */
	@Transient
	public void setDeliveryCorp(DeliveryCorp deliveryCorp) {
		setDeliveryCorp(deliveryCorp != null ? deliveryCorp.getName() : null);
		setDeliveryCorpUrl(deliveryCorp != null ? deliveryCorp.getUrl() : null);
		setDeliveryCorpCode(deliveryCorp != null ? deliveryCorp.getCode() : null);
	}

	/**
	 * 设置地区
	 * 
	 * @param area
	 *            地区
	 */
	@Transient
	public void setArea(Area area) {
		setArea(area != null ? area.getFullName() : null);
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
	
	@ManyToMany
	@JoinTable(name="t_shipping_stockLog",joinColumns = {@JoinColumn(name = "f_shipping", referencedColumnName = "f_id") }, inverseJoinColumns = { @JoinColumn(name = "f_stockLog", referencedColumnName = "f_id") } )
	public Set<StockLog> getStockLogs() {
		return stockLogs;
	}

	public void setStockLogs(Set<StockLog> stockLogs) {
		this.stockLogs = stockLogs;
	}
}
