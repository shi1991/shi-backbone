package com.puyuntech.ycmall.entity;

import com.puyuntech.ycmall.BaseAttributeConverter;
import com.puyuntech.ycmall.entity.value.Invoice;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.groups.Default;
import java.math.BigDecimal;
import java.util.*;

/**
 * 
 * Entity - 订单 . 
 * Created on 2015-8-27 下午3:45:17 
 * @author 施长成
 * 和其他Entity存在的关系
 * 	
 */
@Entity
@Table(name = "t_order")
public class Order extends BaseEntity<Long> {

	private static final long serialVersionUID = 5227373608478684362L;

	/** 锁定过期时间 */
	public static final int LOCK_EXPIRE = 60;

	/**
	 * 配送验证组
	 */
	public interface Delivery extends Default {

	}

	/**
	 * 类型
	 */
	public enum Type {

		/** 普通订单 */
		general,

		/** 兑换订单 */
		exchange,
		
		/** 预定订单 **/
		reserve,
		
		/** 秒杀订单 **/
		seckill,
		
		/** 抢购订单 **/
		grab
	}

	/**
	 * 状态
	 */
	public enum Status {
		
		/** 等待付款 0*/
		pendingPayment,

		/** 等待收货 1*/
		pendingReceive,
		
		/** 等待发货 2*/
		pendingShipment,
		
		/** 待自提 3*/
		daiziti,
		
		/** 已取消 4*/
		canceled,
		
		/** 已完成 5*/
		completed,
		
		/** 已退货 6*/
		returned

	}
	
	public enum orderType{
		/** 其他 **/
		other,
		/** 普通订单 **/
		
	}

	/** 编号 */
	private String sn;

	/** 类型 */
	private Type type;

	/** 状态 */
	private Status status;

	/** 商品价格 */
	private BigDecimal price;

	/** 支付手续费 */
	private BigDecimal fee;

	/** 运费 */
	private BigDecimal freight;

	/** 税金 */
	private BigDecimal tax;

	/** 促销折扣 */
	private BigDecimal promotionDiscount;

	/** 优惠券折扣 */
	private BigDecimal couponDiscount;

	/** 调整金额 */
	private BigDecimal offsetAmount;

	/** 订单金额 */
	private BigDecimal amount;

	/** 已付金额 */
	private BigDecimal amountPaid;

	/** 使用神币数量 **/
	private BigDecimal godMoneyCount;

	/** 已付神币金额 **/
	private BigDecimal godMoneyPaid;

    /** 退款金额 只指现金 */
	private BigDecimal refundAmount;

	/** 赠送积分 */
	private Long rewardPoint;

	/** 兑换积分 */
	private Long exchangePoint;

	/** 商品重量 */
	private Integer weight;

	/** 商品数量 */
	private Integer quantity;

	/** 已发货数量 */
	private Integer shippedQuantity;

	/** 已退货数量 */
	private Integer returnedQuantity;

	/** 收货人 */
	private String consignee;

	/** 地区名称 */
	private String areaName;

	/** 地址 */
	private String address;

	/** 邮编 */
	private String zipCode;

	/** 电话 */
	private String phone;

	/** 附言 */
	private String memo;

	/** 过期时间 */
	private Date expire;

	/** 订购时间 */
	private Date preOrderDate;

	/** 是否已使用优惠码 */
	private Boolean isUseCouponCode;

	/** 是否已兑换积分 */
	private Boolean isExchangePoint;

	/** 是否已分配库存 */
	private Boolean isAllocatedStock;

	/** 是否线上订单 */
	private Boolean isOnline;

	/** 支付方式名称 */
	private String paymentMethodName;

	/** 支付方式类型 */
	private PaymentMethod.Type paymentMethodType;

	/** 配送方式名称 */
	private String shippingMethodName;

	/** 锁定KEY */
	private String lockKey;

	/** 锁定过期时间 */
	private Date lockExpire;

	/** 完成日期 */
	private Date completeDate;

	/** 发票 */
	private Invoice invoice;

	/** 地区 */
	private Area area;

	/** 支付方式 */
	private PaymentMethod paymentMethod;

	/** 配送方式 */
	private ShippingMethod shippingMethod;

	/** 会员 */
	private Member member;

	/** 优惠码 */
	private List<CouponCode> couponCode = new ArrayList<CouponCode>();

	/** 促销名称 */
	private List<String> promotionNames = new ArrayList<String>();

	/** 赠送优惠券 */
	private List<Coupon> coupons = new ArrayList<Coupon>();

	/** 订单项 */
	private List<OrderItem> orderItems = new ArrayList<OrderItem>();

	/** 支付记录 */
	private Set<PaymentLog> paymentLogs = new HashSet<PaymentLog>();

	/** 收款单 */
	private Set<Payment> payments = new HashSet<Payment>();

	/** 发货单 */
	private Set<Shipping> shippings = new HashSet<Shipping>();

	/** 退款单 */
	private Set<Refunds> refunds = new HashSet<Refunds>();

	/** 退货单 */
	private Set<Returns> returns = new HashSet<Returns>();

	/** 订单记录 */
	private Set<OrderLog> orderLogs = new HashSet<OrderLog>();

	/** 抢购记录 */
	private Set<GrabSeckillLog> grabSeckillLog;

	/** 组织机构 **/
	private Organization organization;

	/**  取货时间 **/
	private String collectTime;

	/** 定金 **/
	private BigDecimal deposit;

	/** 转换时间周期 **/
	private Integer changePeriod;

	/** 是否删除 */
	private Boolean isDelete;

	/** 发货前是否审核 */
	private Boolean isChecked;

	/** 售货员 **/
	private Admin admin;

	/** 退订单 **/
	private Set<ReturnOrder> returnOrders;

	/** 推荐人 **/
	private Member recommended;

    /** 已退神币金额 **/
    private BigDecimal returnGodMoenyAmount;

    /** 已退积分 **/
    private Long returnExchagePoint;

	@Column(name="f_collect_time")
	public String getCollectTime() {
		return collectTime;
	}

	public void setCollectTime(String collectTime) {
		this.collectTime = collectTime;
	}

	@ManyToOne
	@JoinColumn(name="f_organization")
	public Organization getOrganization() {
		return organization;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
	}

	@Column(name="f_deposit")
	public BigDecimal getDeposit() {
		return deposit;
	}

	public void setDeposit(BigDecimal deposit) {
		this.deposit = deposit;
	}

	@Column(name="f_change_period")
	public Integer getChangePeriod() {
		return changePeriod;
	}

	public void setChangePeriod(Integer changePeriod) {
		this.changePeriod = changePeriod;
	}

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
	 * 获取类型
	 *
	 * @return 类型
	 */
	@Column(name="f_type",nullable = false)
	public Type getType() {
		return type;
	}

	/**
	 * 设置类型
	 *
	 * @param type
	 *            类型
	 */
	public void setType(Type type) {
		this.type = type;
	}

	/**
	 * 获取状态
	 *
	 * @return 状态
	 */
	@Column(name="f_status",nullable = false)
	public Status getStatus() {
		return status;
	}

	/**
	 * 设置状态
	 *
	 * @param status
	 *            状态
	 */
	public void setStatus(Status status) {
		this.status = status;
	}

	/**
	 * 获取商品价格
	 *
	 * @return 商品价格
	 */
	@Column(name="f_price",nullable = false, precision = 21, scale = 6)
	public BigDecimal getPrice() {
		return price;
	}

	/**
	 * 设置商品价格
	 *
	 * @param price
	 *            商品价格
	 */
	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	/**
	 * 获取支付手续费
	 *
	 * @return 支付手续费
	 */
	@Column(name="f_fee",nullable = false, precision = 21, scale = 6)
	public BigDecimal getFee() {
		return fee;
	}

	/**
	 * 设置支付手续费
	 *
	 * @param fee
	 *            支付手续费
	 */
	public void setFee(BigDecimal fee) {
		this.fee = fee;
	}

	/**
	 * 获取运费
	 *
	 * @return 运费
	 */
	@NotNull(groups = Delivery.class)
	@Min(0)
	@Digits(integer = 12, fraction = 3)
	@Column(name="f_freight",nullable = false, precision = 21, scale = 6)
	public BigDecimal getFreight() {
		return freight;
	}

	/**
	 * 设置运费
	 *
	 * @param freight
	 *            运费
	 */
	public void setFreight(BigDecimal freight) {
		this.freight = freight;
	}

	/**
	 * 获取税金
	 *
	 * @return 税金
	 */
	@Min(0)
	@Digits(integer = 12, fraction = 3)
	@Column(name="f_tax",nullable = false, precision = 21, scale = 6)
	public BigDecimal getTax() {
		return tax;
	}

	/**
	 * 设置税金
	 *
	 * @param tax
	 *            税金
	 */
	public void setTax(BigDecimal tax) {
		this.tax = tax;
	}

	/**
	 * 获取促销折扣
	 *
	 * @return 促销折扣
	 */
	@Column(name="f_promotion_discount",nullable = false, updatable = false, precision = 21, scale = 6)
	public BigDecimal getPromotionDiscount() {
		return promotionDiscount;
	}

	/**
	 * 设置促销折扣
	 *
	 * @param promotionDiscount
	 *            促销折扣
	 */
	public void setPromotionDiscount(BigDecimal promotionDiscount) {
		this.promotionDiscount = promotionDiscount;
	}

	/**
	 * 获取优惠券折扣
	 *
	 * @return 优惠券折扣
	 */
	@Column(name="f_coupon_discount",nullable = false, updatable = false, precision = 21, scale = 6)
	public BigDecimal getCouponDiscount() {
		return couponDiscount;
	}

	/**
	 * 设置优惠券折扣
	 *
	 * @param couponDiscount
	 *            优惠券折扣
	 */
	public void setCouponDiscount(BigDecimal couponDiscount) {
		this.couponDiscount = couponDiscount;
	}

	/**
	 * 获取调整金额
	 *
	 * @return 调整金额
	 */
	@NotNull
	@Digits(integer = 12, fraction = 3)
	@Column(name="f_offset_amount",nullable = false, precision = 21, scale = 6)
	public BigDecimal getOffsetAmount() {
		return offsetAmount;
	}

	/**
	 * 设置调整金额
	 *
	 * @param offsetAmount
	 *            调整金额
	 */
	public void setOffsetAmount(BigDecimal offsetAmount) {
		this.offsetAmount = offsetAmount;
	}

	/**
	 * 获取订单金额
	 *
	 * @return 订单金额
	 */
	@Column(name="f_amount",nullable = false, precision = 21, scale = 6)
	public BigDecimal getAmount() {
		return amount;
	}

	/**
	 * 设置订单金额
	 *
	 * @param amount
	 *            订单金额
	 */
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	/**
	 * 获取已付金额
	 *
	 * @return 已付金额
	 */
	@Column(name="f_amount_paid",nullable = false, precision = 21, scale = 6)
	public BigDecimal getAmountPaid() {
		return amountPaid;
	}

	/**
	 * 设置已付金额
	 *
	 * @param amountPaid
	 *            已付金额
	 */
	public void setAmountPaid(BigDecimal amountPaid) {
		this.amountPaid = amountPaid;
	}

	/**
	 *
	 * 神币抵用金额  .
	 * <br>
	 * author: 施长成
	 *   date: 2015-11-10 下午5:23:51
	 * @return
	 */
	@Column(name="f_godmoney_paid", precision = 21, scale = 6)
	public BigDecimal getGodMoneyPaid() {
		return godMoneyPaid;
	}

	public void setGodMoneyPaid(BigDecimal godMoneyPaid) {
		this.godMoneyPaid = godMoneyPaid;
	}

	/**
	 *
	 * 神币使用数量 .
	 * <p>方法详细说明,如果要换行请使用<br>标签</p>
	 * <br>
	 * author: 施长成
	 *   date: 2015-11-10 下午5:45:03
	 * @return
	 */
	@Column(name="f_godmoney_count", precision = 21, scale = 6)
	public BigDecimal getGodMoneyCount() {
		return godMoneyCount;
	}

	public void setGodMoneyCount(BigDecimal godMoneyCount) {
		this.godMoneyCount = godMoneyCount;
	}

	/**
	 * 获取退款金额
	 *
	 * @return 退款金额
	 */
	@Column(name="f_refund_amount",nullable = false, precision = 21, scale = 6)
	public BigDecimal getRefundAmount() {
		return refundAmount;
	}

	/**
	 * 设置退款金额
	 *
	 * @param refundAmount
	 *            退款金额
	 */
	public void setRefundAmount(BigDecimal refundAmount) {
		this.refundAmount = refundAmount;
	}

	/**
	 * 获取赠送积分
	 *
	 * @return 赠送积分
	 */
	@Min(0)
	@Column(name="f_reward_point",nullable = false)
	public Long getRewardPoint() {
		return rewardPoint;
	}

	/**
	 * 设置赠送积分
	 *
	 * @param rewardPoint
	 *            赠送积分
	 */
	public void setRewardPoint(Long rewardPoint) {
		this.rewardPoint = rewardPoint;
	}

	/**
	 * 获取兑换积分
	 *
	 * @return 兑换积分
	 */
	@Column(name="f_exchange_point",nullable = false, updatable = false)
	public Long getExchangePoint() {
		return exchangePoint;
	}

	/**
	 * 设置兑换积分
	 *
	 * @param exchangePoint
	 *            兑换积分
	 */
	public void setExchangePoint(Long exchangePoint) {
		this.exchangePoint = exchangePoint;
	}

	/**
	 * 获取商品重量
	 *
	 * @return 商品重量
	 */
	@Column(name="f_weight", updatable = false)
	public Integer getWeight() {
		return weight;
	}

	/**
	 * 设置商品重量
	 *
	 * @param weight
	 *            商品重量
	 */
	public void setWeight(Integer weight) {
		this.weight = weight;
	}

	/**
	 * 获取商品数量
	 *
	 * @return 商品数量
	 */
	@Column(name="f_quantity",nullable = false, updatable = false)
	public Integer getQuantity() {
		return quantity;
	}

	/**
	 * 设置商品数量
	 *
	 * @param quantity
	 *            商品数量
	 */
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	/**
	 * 获取已发货数量
	 *
	 * @return 已发货数量
	 */
	@Column(name="f_shipped_quantity",nullable = false)
	public Integer getShippedQuantity() {
		return shippedQuantity;
	}

	/**
	 * 设置已发货数量
	 *
	 * @param shippedQuantity
	 *            已发货数量
	 */
	public void setShippedQuantity(Integer shippedQuantity) {
		this.shippedQuantity = shippedQuantity;
	}

	/**
	 * 获取已退货数量
	 *
	 * @return 已退货数量
	 */
	@Column(name="f_returned_quantity",nullable = false)
	public Integer getReturnedQuantity() {
		return returnedQuantity;
	}

	/**
	 * 设置已退货数量
	 *
	 * @param returnedQuantity
	 *            已退货数量
	 */
	public void setReturnedQuantity(Integer returnedQuantity) {
		this.returnedQuantity = returnedQuantity;
	}

	/**
	 * 获取收货人
	 *
	 * @return 收货人
	 */
	@NotEmpty(groups = Delivery.class)
	@Length(max = 200)
	@Column(name="f_consignee")
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
	 * 获取地区名称
	 *
	 * @return 地区名称
	 */
	@Column(name="f_area_name")
	public String getAreaName() {
		return areaName;
	}

	/**
	 * 设置地区名称
	 *
	 * @param areaName
	 *            地区名称
	 */
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	/**
	 * 获取地址
	 *
	 * @return 地址
	 */
	@NotEmpty(groups = Delivery.class)
	@Length(max = 200)
	@Column(name="f_address")
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
	@Column(name="f_zip_code")
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
	@Column(name="f_phone")
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
	 * 获取附言
	 *
	 * @return 附言
	 */
	@Length(max = 200)
	@Column(name="f_memo")
	public String getMemo() {
		return memo;
	}

	/**
	 * 设置附言
	 *
	 * @param memo
	 *            附言
	 */
	public void setMemo(String memo) {
		this.memo = memo;
	}

	/**
	 * 获取过期时间
	 *
	 * @return 过期时间
	 */
	@Column(name="f_expire")
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
	 * 获取预定时间
	 *
	 * @return 预定时间
	 */
	@Column(name="f_create_date",insertable= false , updatable = false)
	public Date getPreOrderDate() {
		return preOrderDate;
	}

	/**
	 *
	 * 设置预定时间.
	 *
	 * @param preOrderDate 预定时间
	 */
	public void setPreOrderDate(Date preOrderDate) {
		this.preOrderDate = preOrderDate;
	}

	/**
	 * 获取是否已使用优惠码
	 *
	 * @return 是否已使用优惠码
	 */
	@Column(name="f_is_use_coupon_code",nullable = false)
	public Boolean getIsUseCouponCode() {
		return isUseCouponCode;
	}

	/**
	 * 设置是否已使用优惠码
	 *
	 * @param isUseCouponCode
	 *            是否已使用优惠码
	 */
	public void setIsUseCouponCode(Boolean isUseCouponCode) {
		this.isUseCouponCode = isUseCouponCode;
	}

	@Column(name="f_is_online")
	public Boolean getIsOnline() {
		return isOnline;
	}

	public void setIsOnline(Boolean isOnline) {
		this.isOnline = isOnline;
	}

	/**
	 * 获取是否已兑换积分
	 *
	 * @return 是否已兑换积分
	 */
	@Column(name="f_is_exchange_point",nullable = false)
	public Boolean getIsExchangePoint() {
		return isExchangePoint;
	}

	/**
	 * 设置是否已兑换积分
	 *
	 * @param isExchangePoint
	 *            是否已兑换积分
	 */
	public void setIsExchangePoint(Boolean isExchangePoint) {
		this.isExchangePoint = isExchangePoint;
	}

	/**
	 * 获取是否已分配库存
	 *
	 * @return 是否已分配库存
	 */
	@Column(name="f_is_allocated_stock",nullable = false)
	public Boolean getIsAllocatedStock() {
		return isAllocatedStock;
	}

	/**
	 * 设置是否已分配库存
	 *
	 * @param isAllocatedStock
	 *            是否已分配库存
	 */
	public void setIsAllocatedStock(Boolean isAllocatedStock) {
		this.isAllocatedStock = isAllocatedStock;
	}

	/**
	 * 获取支付方式名称
	 *
	 * @return 支付方式名称
	 */
	@Column(name="f_payment_method_name")
	public String getPaymentMethodName() {
		return paymentMethodName;
	}

	/**
	 * 设置支付方式名称
	 *
	 * @param paymentMethodName
	 *            支付方式名称
	 */
	public void setPaymentMethodName(String paymentMethodName) {
		this.paymentMethodName = paymentMethodName;
	}

	/**
	 * 获取支付方式类型
	 *
	 * @return 支付方式类型
	 */
	@Column(name="f_payment_method_type")
	public PaymentMethod.Type getPaymentMethodType() {
		return paymentMethodType;
	}

	/**
	 * 设置支付方式类型
	 *
	 * @param paymentMethodType
	 *            支付方式类型
	 */
	public void setPaymentMethodType(PaymentMethod.Type paymentMethodType) {
		this.paymentMethodType = paymentMethodType;
	}

	/**
	 * 获取配送方式名称
	 *
	 * @return 配送方式名称
	 */
	@Column(name="f_shipping_method_name")
	public String getShippingMethodName() {
		return shippingMethodName;
	}

	/**
	 * 设置配送方式名称
	 *
	 * @param shippingMethodName
	 *            配送方式名称
	 */
	public void setShippingMethodName(String shippingMethodName) {
		this.shippingMethodName = shippingMethodName;
	}

	/**
	 * 获取锁定KEY
	 *
	 * @return 锁定KEY
	 */
	@Column(name="f_lock_key")
	public String getLockKey() {
		return lockKey;
	}

	/**
	 * 设置锁定KEY
	 *
	 * @param lockKey
	 *            锁定KEY
	 */
	public void setLockKey(String lockKey) {
		this.lockKey = lockKey;
	}

	/**
	 * 获取锁定过期时间
	 *
	 * @return 锁定过期时间
	 */
	@Column(name="f_lock_expire")
	public Date getLockExpire() {
		return lockExpire;
	}

	/**
	 * 设置锁定过期时间
	 *
	 * @param lockExpire
	 *            锁定过期时间
	 */
	public void setLockExpire(Date lockExpire) {
		this.lockExpire = lockExpire;
	}

	/**
	 * 获取完成日期
	 *
	 * @return 完成日期
	 */
	@Column(name="f_complete_date")
	public Date getCompleteDate() {
		return completeDate;
	}

	/**
	 * 设置完成日期
	 *
	 * @param completeDate
	 *            完成日期
	 */
	public void setCompleteDate(Date completeDate) {
		this.completeDate = completeDate;
	}

	/**
	 * 获取发票
	 *
	 * @return 发票
	 */
	@Valid
	@Embedded
	public Invoice getInvoice() {
		return invoice;
	}

	/**
	 * 设置发票
	 *
	 * @param invoice
	 *            发票
	 */
	public void setInvoice(Invoice invoice) {
		this.invoice = invoice;
	}

	/**
	 * 获取地区
	 *
	 * @return 地区
	 */
	@NotNull(groups = Delivery.class)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="f_area")
	public Area getArea() {
		return area;
	}

	/**
	 * 设置地区
	 *
	 * @param area
	 *            地区
	 */
	public void setArea(Area area) {
		this.area = area;
	}

	/**
	 * 获取支付方式
	 *
	 * @return 支付方式
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="f_payment_method")
	public PaymentMethod getPaymentMethod() {
		return paymentMethod;
	}

	/**
	 * 设置支付方式
	 *
	 * @param paymentMethod
	 *            支付方式
	 */
	public void setPaymentMethod(PaymentMethod paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	/**
	 * 获取配送方式
	 *
	 * @return 配送方式
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="f_shipping_method")
	public ShippingMethod getShippingMethod() {
		return shippingMethod;
	}

	/**
	 * 设置配送方式
	 *
	 * @param shippingMethod
	 *            配送方式
	 */
	public void setShippingMethod(ShippingMethod shippingMethod) {
		this.shippingMethod = shippingMethod;
	}

	/**
	 * 获取会员
	 *
	 * @return 会员
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="f_member",nullable = false, updatable = false)
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
	 * 获取优惠码
	 *
	 * @return 优惠码
	 */
	@OneToMany(mappedBy = "order", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	public List<CouponCode> getCouponCode() {
		return couponCode;
	}

	/**
	 * 设置优惠码
	 *
	 * @param couponCode
	 *            优惠码
	 */
	public void setCouponCode(List<CouponCode> couponCode) {
		this.couponCode = couponCode;
	}

	/**
	 * 获取促销名称
	 *
	 * @return 促销名称
	 */
	@Column(name="f_promotion_names",updatable = false, length = 4000)
	@Convert(converter = PromotionNameConverter.class)
	public List<String> getPromotionNames() {
		return promotionNames;
	}

	/**
	 * 设置促销名称
	 *
	 * @param promotionNames
	 *            促销名称
	 */
	public void setPromotionNames(List<String> promotionNames) {
		this.promotionNames = promotionNames;
	}

	/**
	 * 获取赠送优惠券
	 *
	 * @return 赠送优惠券
	 */
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "t_order_coupon", joinColumns = { @JoinColumn(name = "f_orders", referencedColumnName = "f_id") }, inverseJoinColumns = { @JoinColumn(name = "f_coupons", referencedColumnName = "f_id") })
	public List<Coupon> getCoupons() {
		return coupons;
	}

	/**
	 * 设置赠送优惠券
	 *
	 * @param coupons
	 *            赠送优惠券
	 */
	public void setCoupons(List<Coupon> coupons) {
		this.coupons = coupons;
	}

	/**
	 * 获取订单项
	 *
	 * @return 订单项
	 */
	@OneToMany(mappedBy = "order", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@OrderBy("type asc")
	public List<OrderItem> getOrderItems() {
		return orderItems;
	}

	/**
	 * 设置订单项
	 *
	 * @param orderItems
	 *            订单项
	 */
	public void setOrderItems(List<OrderItem> orderItems) {
		this.orderItems = orderItems;
	}

	/**
	 * 获取支付记录
	 *
	 * @return 支付记录
	 */
	@OneToMany(mappedBy = "order", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	@OrderBy("createDate asc")
	public Set<PaymentLog> getPaymentLogs() {
		return paymentLogs;
	}

	/**
	 * 设置支付记录
	 *
	 * @param paymentLogs
	 *            支付记录
	 */
	public void setPaymentLogs(Set<PaymentLog> paymentLogs) {
		this.paymentLogs = paymentLogs;
	}

	/**
	 * 获取收款单
	 *
	 * @return 收款单
	 */
	@OneToMany(mappedBy = "order", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	@OrderBy("createDate asc")
	public Set<Payment> getPayments() {
		return payments;
	}

	/**
	 * 设置收款单
	 *
	 * @param payments
	 *            收款单
	 */
	public void setPayments(Set<Payment> payments) {
		this.payments = payments;
	}

	/**
	 * 获取退款单
	 *
	 * @return 退款单
	 */
	@OneToMany(mappedBy = "order", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	@OrderBy("createDate asc")
	public Set<Refunds> getRefunds() {
		return refunds;
	}

	/**
	 * 设置退款单
	 *
	 * @param refunds
	 *            退款单
	 */
	public void setRefunds(Set<Refunds> refunds) {
		this.refunds = refunds;
	}

	/**
	 * 获取发货单
	 *
	 * @return 发货单
	 */
	@OneToMany(mappedBy = "order", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	@OrderBy("createDate asc")
	public Set<Shipping> getShippings() {
		return shippings;
	}

	/**
	 * 设置发货单
	 *
	 * @param shippings
	 *            发货单
	 */
	public void setShippings(Set<Shipping> shippings) {
		this.shippings = shippings;
	}

	/**
	 * 获取退货单
	 *
	 * @return 退货单
	 */
	@OneToMany(mappedBy = "order", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	@OrderBy("createDate asc")
	public Set<Returns> getReturns() {
		return returns;
	}

	/**
	 * 获取抢购记录
	 *
	 * @return 抢购记录
	 */
	@OneToMany(mappedBy = "order", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	public Set<GrabSeckillLog> getGrabSeckillLog() {
		return grabSeckillLog;
	}

	public void setGrabSeckillLog(Set<GrabSeckillLog> grabSeckillLog) {
		this.grabSeckillLog = grabSeckillLog;
	}

	/**
	 * 设置退货单
	 *
	 * @param returns
	 *            退货单
	 */
	public void setReturns(Set<Returns> returns) {
		this.returns = returns;
	}

	/**
	 * 获取订单记录
	 *
	 * @return 订单记录
	 */
	@OneToMany(mappedBy = "order", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	@OrderBy("createDate asc")
	public Set<OrderLog> getOrderLogs() {
		return orderLogs;
	}

	@OneToMany(mappedBy = "order", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	@OrderBy("createDate asc")
	public Set<ReturnOrder> getReturnOrders() {
		return returnOrders;
	}

	public void setReturnOrders(Set<ReturnOrder> returnOrders) {
		this.returnOrders = returnOrders;
	}

	/**
	 * 获取推荐人信息 .
	 * author: 施长成
	 *   date: 2015-12-1 下午5:05:52
	 * @return
	 */
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn( name="f_recommended" )
	public Member getRecommended() {
		return recommended;
	}

	/**
	 *
	 * 设置推荐人.
	 * author: 施长成
	 *   date: 2015-12-1 下午5:07:17
	 * @param recommended
	 */
	public void setRecommended(Member recommended) {
		this.recommended = recommended;
	}

	/**
	 * 设置订单记录
	 *
	 * @param orderLogs
	 *            订单记录
	 */
	public void setOrderLogs(Set<OrderLog> orderLogs) {
		this.orderLogs = orderLogs;
	}

	@ManyToOne(fetch=FetchType.LAZY , targetEntity=Admin.class)
	@JoinTable(name = "t_admin_order_log", joinColumns = { @JoinColumn(name = "f_order", referencedColumnName = "f_id") }, inverseJoinColumns = { @JoinColumn(name = "f_admin", referencedColumnName = "f_id") })
	public Admin getAdmin() {
		return admin;
	}

	public void setAdmin(Admin admin) {
		this.admin = admin;
	}

	@Column(name="f_is_checked")
	public Boolean getIsChecked() {
		return isChecked;
	}

	public void setIsChecked(Boolean isChecked) {
		this.isChecked = isChecked;
	}

    @Column(name="f_returned_godmonty_amount")
    public BigDecimal getReturnGodMoenyAmount() {
        return returnGodMoenyAmount;
    }

    public void setReturnGodMoenyAmount(BigDecimal returnGodMoenyAmount) {
        this.returnGodMoenyAmount = returnGodMoenyAmount;
    }

    @Column(name="f_returned_exchange_point")
    public Long getReturnExchagePoint() {
        return returnExchagePoint;
    }

    public void setReturnExchagePoint(Long returnExchagePoint) {
        this.returnExchagePoint = returnExchagePoint;
    }

	/**
	 * 获取是否需要物流
	 *
	 * @return 是否需要物流
	 */
	@Transient
	public boolean getIsDelivery() {
		return CollectionUtils.exists(getOrderItems(), new Predicate() {
			@Override
			public boolean evaluate(Object object) {
				OrderItem orderItem = (OrderItem) object;
				return orderItem != null && BooleanUtils.isTrue(orderItem.getIsDelivery());
			}
		});
	}

	/**
	 * 获取应付金额
	 *
	 * @return 应付金额
	 */
	@Transient
	public BigDecimal getAmountPayable() {
//		if (!hasExpired() && !Order.Status.completed.equals(getStatus()) && !Order.Status.failed.equals(getStatus()) && !Order.Status.canceled.equals(getStatus()) && !Order.Status.denied.equals(getStatus())) {
		if (!hasExpired() && !Status.completed.equals(getStatus()) && !Status.canceled.equals(getStatus())) {
			BigDecimal amountPayable = getAmount().subtract(getAmountPaid());
			return amountPayable.compareTo(BigDecimal.ZERO) >= 0 ? amountPayable : BigDecimal.ZERO;
		}
		return BigDecimal.ZERO;
	}

	/**
	 * 获取应收金额
	 *
	 * @return 应收金额
	 */
	@Transient
	public BigDecimal getAmountReceivable() {
//		if (!hasExpired() && PaymentMethod.Type.cashOnDelivery.equals(getPaymentMethodType()) && !Order.Status.completed.equals(getStatus()) && !Order.Status.failed.equals(getStatus()) && !Order.Status.canceled.equals(getStatus()) && !Order.Status.denied.equals(getStatus())) {
		if (!hasExpired() && !Status.completed.equals(getStatus()) && !Status.canceled.equals(getStatus())) {
			BigDecimal amountReceivable = getAmount().subtract(getAmountPaid());
			return amountReceivable.compareTo(BigDecimal.ZERO) >= 0 ? amountReceivable : BigDecimal.ZERO;
		}
		return BigDecimal.ZERO;
	}
	
	
	/**
	 * 获取现金应收金额
	 *
	 * @return 应收金额
	 */
	@Transient
	public BigDecimal getCashAmountReceivable() {
		BigDecimal amountReceivable = getAmount().subtract(getGodMoneyPaid());
		return amountReceivable.compareTo(BigDecimal.ZERO) >= 0 ? amountReceivable : BigDecimal.ZERO;
	}

	/**
	 * 获取应退金额
	 *
	 * @return 应退金额
	 */
	@Transient
	public BigDecimal getRefundableAmount() {
		if (hasExpired() || Status.canceled.equals(getStatus()) || Status.returned.equals(getStatus())) {
			BigDecimal refundableAmount = getAmountPaid();
			return refundableAmount.compareTo(BigDecimal.ZERO) >= 0 ? refundableAmount : BigDecimal.ZERO;
		}
		if (Status.completed.equals(getStatus())) {
			BigDecimal refundableAmount = getAmountPaid().subtract(getAmount());
			return refundableAmount.compareTo(BigDecimal.ZERO) >= 0 ? refundableAmount : BigDecimal.ZERO;
		}
		return BigDecimal.ZERO;
	}

	/**
	 * 获取可发货数
	 *
	 * @return 可发货数
	 */
	@Transient
	public int getShippableQuantity() {
		if (!hasExpired() && Status.pendingShipment.equals(getStatus())) {
			int shippableQuantity = getQuantity() - getShippedQuantity();
			return shippableQuantity >= 0 ? shippableQuantity : 0;
		}
		return 0;
	}

	/**
	 * 获取可退货数
	 *
	 * @return 可退货数
	 */
	@Transient
	public int getReturnableQuantity() {
//		if (!hasExpired() && Order.Status.failed.equals(getStatus())) {
		if (!hasExpired()) {
			int returnableQuantity = getShippedQuantity() - getReturnedQuantity();
			return returnableQuantity >= 0 ? returnableQuantity : 0;
		}
		return 0;
	}

	/**
	 * 获取是否锁定
	 *
	 * @return 是否锁定
	 */
	@Column(name="f_is_delete",nullable = false)
	public Boolean getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Boolean isDelete) {
		this.isDelete = isDelete;
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

	/**
	 * 获取订单项
	 *
	 * @param sn
	 *            商品编号
	 * @return 订单项
	 */
	@Transient
	public OrderItem getOrderItem(String sn) {
		if (StringUtils.isEmpty(sn) || CollectionUtils.isEmpty(getOrderItems())) {
			return null;
		}
		for (OrderItem orderItem : getOrderItems()) {
			if (orderItem != null && StringUtils.equalsIgnoreCase(orderItem.getSn(), sn)) {
				return orderItem;
			}
		}
		return null;
	}

	/**
	 * 持久化前处理
	 */
	@PrePersist
	public void prePersist() {
		if (getArea() != null) {
			setAreaName(getArea().getFullName());
		}
		if (getPaymentMethod() != null) {
			setPaymentMethodName(getPaymentMethod().getName());
			setPaymentMethodType(getPaymentMethod().getType());
		}
		if (getShippingMethod() != null) {
			setShippingMethodName(getShippingMethod().getName());
		}
	}

	/**
	 * 更新前处理
	 */
	@PreUpdate
	public void preUpdate() {
		if (getArea() != null) {
			setAreaName(getArea().getFullName());
		}
		if (getPaymentMethod() != null) {
			setPaymentMethodName(getPaymentMethod().getName());
			setPaymentMethodType(getPaymentMethod().getType());
		}
		if (getShippingMethod() != null) {
			setShippingMethodName(getShippingMethod().getName());
		}
	}


    /**
     * 获取 预定订单 应付金额
     *
     * @return 应付金额
     */
    @Transient
    public BigDecimal getPreAmountPayable() {
        if (!hasExpired() && !Status.completed.equals(getStatus()) && !Status.canceled.equals(getStatus())) {
            BigDecimal amountPayable = getDeposit().subtract(getAmountPaid());
            return amountPayable.compareTo(BigDecimal.ZERO) >= 0 ? amountPayable : BigDecimal.ZERO;
        }
        return BigDecimal.ZERO;
    }

	/**
	 * 类型转换 - 促销名称
	 */
	@Converter
	public static class PromotionNameConverter extends BaseAttributeConverter<List<String>> implements AttributeConverter<Object, String> {
	}


}
