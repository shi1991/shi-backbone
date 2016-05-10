package com.puyuntech.ycmall.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.AttributeConverter;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Converter;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Store;
import org.hibernate.validator.constraints.Length;

import com.puyuntech.ycmall.BaseAttributeConverter;

/**
 * 退货单
 *  关联退货单项和订单
 *
 *  @author 施长成
 */
@Entity
@Table(name = "t_return_order")
public class ReturnOrder extends BaseEntity<Long> {
	
	private static final long serialVersionUID = 4959397120352476116L;

	/**
	 * 类型
	 */
	public enum Type {

		/** 退货 0*/
		returnProduct,

		/** 换货 1*/
		changeProduct,
		
		/** 维修 2**/
		fixProduct
		
	}
	
	/**
	 * 状态
	 */
	public enum Status {
		
		/** 待审核 0*/
		pendingReturn,
		
		/** 已处理 1*/
		complete,
		
		/** 拒绝 2*/
		refused,

		/** 转维修 3*/
		repaired,

		/** 待检测 4*/
		pendingCheck,
		
		/** 待客户送检 5*/
		pendingShip,
		
		/** 待退款 6 ，退款成功就是完成状态*/
		pendingRefund,

        /** 商品维修，等待客服填写维修费用 7 **/
        waitFillRepairPrice,

        /** 等待客户支付维修费用 8  **/
        waitRepairpending,

        /** 等待维修 9  **/
        waitRepair,
        
        /** 等待客户收货或自提 10  **/
        pendingRecive,
	}

    public enum PayStatus{
        /** 等待支付 */
        wait,

        /** 支付成功 */
        success,

        /** 支付失败 */
        failure
    }

	/** 售后流水编号 */
	private String sn;
	
	/** 返回商户配送方式 */
	private String storeShippingMethod;

	/** 返回商户物流公司 */
	private String storeDeliveryCorp;

    /** 返回商户物流公司代码 **/
    private String storeDeliveryCorpCode;

	/** 返回商户运单号 */
	private String storeTrackingNo;

    /** 返回商户配送方式 */
    private String memberShippingMethod;

    /** 返回用户物流公司 */
    private String memberDeliveryCorp;

    /** 返回用户物流公司代码 **/
    private String memberDeliveryCorpCode;

    /** 返回用户运单号 */
    private String memberTrackingNo;

	/** 售后类型 */
	private Type type;

	/** 售后订单状态 */
	private Status status;

    /** 退货返还神币 */
    private BigDecimal returnGodMoney;

	/** 退货返还退款 */
	private BigDecimal returnPrice;

    /** 退货实际返还退款 */
    private BigDecimal returnPriceActual;

	/** 退货返还积分 */
	private Long returnPoint;

    /** 退货返还优惠劵 **/
    private List<String> returnCouponCode = new ArrayList<String>();

    /** 优惠劵抵用金额 **/
    private BigDecimal returnCouponPrice;

    /** 维修所需费用 **/
    private BigDecimal repairPrice;

    /** 维修支付的状态 **/
    private PayStatus repairPayStatus;

    /** 支付记录 */
    private Set<PaymentLog> paymentLogs = new HashSet<PaymentLog>();

	/** 退换订单 */
	private Order order;

	/** 申请人 */
	private Member member;

	/** 联系方式 */
	private String phone;

	/** 联系人 */
	private String contacter;

	/** 联系地址 */
	private String address;

	/** 问题处理 */
	private String describe;

	/** 处理结果 */
	private String result;

	/** 退货图片 */
	private List<String> image = new ArrayList<String>();

	/** 备注 */
	private List<String> memo;

	/** 申请门店 */
	private Organization organization;

	/** 预约时间 */
	private Date orderDate;

	/** 退订单项 **/
	private Set<ReturnOrderItem> returnOrderItems;

	/** 退单日志 **/
	private Set<ReturnOrderLog> returnOrderLogs;

	/** 库存记录 */
	private Set<StockLog> stockLogs = new HashSet<StockLog>();

    /** 维修信息记录 **/
    private String repairMemo;

	@Column(name="f_sn")
	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	/**
	 * 获取类型
	 *
	 * @return 类型
	 */
	@Field(store = Store.YES, index = Index.YES, analyze = Analyze.NO)
	@Column(name="f_type",nullable = false)
	public Type getType() {
		return type;
	}

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

	public void setStatus(Status status) {
		this.status = status;
	}



	@ManyToOne
	@JoinColumn(name="f_order",nullable = false, updatable = false)
	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="f_member", updatable = false)
	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}
	
	@Length(max = 200)
	@Column(name="f_phone")
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Column(name="f_contacter")
	public String getContacter() {
		return contacter;
	}

	public void setContacter(String contacter) {
		this.contacter = contacter;
	}

	@Column(name="f_address")
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Column(name="f_describe")
	public String getDescribe() {
		return describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}

	@Column(name="f_result")
	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	/**
	 * 获取图片
	 * 
	 * @return 图片
	 */
	@Column(name="f_image", length = 4000)
	@Convert(converter = ImagesConverter.class)
	public List<String> getImage() {
		return image;
	}

	/**
	 * 
	 * 设置图片.
	 * 
	 * @param images 图片
	 */
	public void setImage(List<String> image) {
		this.image = image;
	}
	
	@Column(name="f_memo", length = 4000)
	@Convert(converter = MemoConverter.class)
	public List<String> getMemo() {
		return memo;
	}

	public void setMemo(List<String> memo) {
		this.memo = memo;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="f_organization")
	public Organization getOrganization() {
		return organization;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
	}

	@Column(name="f_order_date")
	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}
	
	@OneToMany(mappedBy = "returnOrder", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	@OrderBy("createDate asc")
	public Set<ReturnOrderItem> getReturnOrderItems() {
		return returnOrderItems;
	}

	public void setReturnOrderItems(Set<ReturnOrderItem> returnOrderItems) {
		this.returnOrderItems = returnOrderItems;
	}
	
	@OneToMany(mappedBy = "returnOrder", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	@OrderBy("createDate asc")
	public Set<ReturnOrderLog> getReturnOrderLogs() {
		return returnOrderLogs;
	}

	public void setReturnOrderLogs(Set<ReturnOrderLog> returnOrderLogs) {
		this.returnOrderLogs = returnOrderLogs;
	}
	
	@ManyToMany
	@JoinTable(name="t_returnOrder_stockLog",joinColumns = {@JoinColumn(name = "f_returnOrder", referencedColumnName = "f_id") }, inverseJoinColumns = { @JoinColumn(name = "f_stockLog", referencedColumnName = "f_id") } )
	public Set<StockLog> getStockLogs() {
		return stockLogs;
	}

	public void setStockLogs(Set<StockLog> stockLogs) {
		this.stockLogs = stockLogs;
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
     * 获取返回商户配送方式
     * @return
     */
    @Column(name="f_store_shipping_method")
    public String getStoreShippingMethod() {
        return storeShippingMethod;
    }

    /**
     * 设置返回商户配送方式
     * @param storeShippingMethod
     */
    public void setStoreShippingMethod(String storeShippingMethod) {
        this.storeShippingMethod = storeShippingMethod;
    }

    /**
     * 获取返回商户物流公司
     * @return
     */
    @Column(name="f_store_delivery_corp")
    public String getStoreDeliveryCorp() {
        return storeDeliveryCorp;
    }

    /**
     * 设置返回商户物流公司
     * @param storeDeliveryCorp
     */
    public void setStoreDeliveryCorp(String storeDeliveryCorp) {
        this.storeDeliveryCorp = storeDeliveryCorp;
    }

    /**
     * 获取返回商户物流公司代码
     * @return
     */
    @Column(name="f_store_deliver_corp_code")
    public String getStoreDeliveryCorpCode() {
        return storeDeliveryCorpCode;
    }

    /**
     * 设置返回商户物流公司代码
     * @param storeDeliveryCorpCode
     */
    public void setStoreDeliveryCorpCode(String storeDeliveryCorpCode) {
        this.storeDeliveryCorpCode = storeDeliveryCorpCode;
    }

    /**
     * 获取返回商户运单号
     * @return
     */
    @Column(name="f_store_tracking_no")
    public String getStoreTrackingNo() {
        return storeTrackingNo;
    }

    /**
     * 设置返回商户运单号
     * @param storeTrackingNo
     */
    public void setStoreTrackingNo(String storeTrackingNo) {
        this.storeTrackingNo = storeTrackingNo;
    }

    /**
     * 获取返回 用户配送方式
     * @return
     */
    @Column(name="f_member_shipping_method")
    public String getMemberShippingMethod() {
        return memberShippingMethod;
    }

    /**
     * 设置返回用户配送方式
     * @param memberShippingMethod
     */
    public void setMemberShippingMethod(String memberShippingMethod) {
        this.memberShippingMethod = memberShippingMethod;
    }

    /**
     * 获取返回用户物流公司
     * @return
     */
    @Column(name="f_member_delivery_corp")
    public String getMemberDeliveryCorp() {
        return memberDeliveryCorp;
    }

    /**
     * 设置返回用户物流公司
     * @param memberDeliveryCorp
     */
    public void setMemberDeliveryCorp(String memberDeliveryCorp) {
        this.memberDeliveryCorp = memberDeliveryCorp;
    }

    /**
     * 获取返回用户物流公司代码
     * @return
     */
    @Column(name="f_member_delivery_corp_code")
    public String getMemberDeliveryCorpCode() {
        return memberDeliveryCorpCode;
    }

    /**
     * 设置返回用户物流公司代码
     * @param memberDeliveryCorpCode
     */
    public void setMemberDeliveryCorpCode(String memberDeliveryCorpCode) {
        this.memberDeliveryCorpCode = memberDeliveryCorpCode;
    }

    /**
     * 获取返回用户物流运单号
     * @return
     */
    @Column(name="f_member_tracking_no")
    public String getMemberTrackingNo() {
        return memberTrackingNo;
    }

    /**
     * 设置返回用户物流运单号
     * @param memberTrackingNo
     */
    public void setMemberTrackingNo(String memberTrackingNo) {
        this.memberTrackingNo = memberTrackingNo;
    }

    /**
     * 获取退款金额
     * @return
     */
    @Column(name="f_return_price")
    public BigDecimal getReturnPrice() {
        return returnPrice;
    }

    /**
     * 设置退款金额
     * @param returnPrice
     */
    public void setReturnPrice(BigDecimal returnPrice) {
        this.returnPrice = returnPrice;
    }

    /**
     * 获取实际退款金额
     * @return
     */
    @Column(name="f_return_price_actual")
    public BigDecimal getReturnPriceActual() {
        return returnPriceActual;
    }

    /**
     * 设置实际退款金额
     * @param returnPriceActual
     */
    public void setReturnPriceActual(BigDecimal returnPriceActual) {
        this.returnPriceActual = returnPriceActual;
    }

    /**
     * 获取退货返还的积分
     * @return
     */
    @Column(name="f_return_point")
    public Long getReturnPoint() {
        return returnPoint;
    }

    /**
     * 设置退货返还的积分
     * @param returnPoint
     */
    public void setReturnPoint(Long returnPoint) {
        this.returnPoint = returnPoint;
    }

    /**
     * 获取退款返还的优惠劵
     * @return
     */
    @Column(name="f_return_coupon_code")
    @Convert(converter =  ReturnOrderCouponCodeCanverter.class)
    public List<String> getReturnCouponCode() {
        return returnCouponCode;
    }

    /**
     * 设置退款返还的优惠劵
     * @param returnCouponCode
     */
    public void setReturnCouponCode(List<String> returnCouponCode) {
        this.returnCouponCode = returnCouponCode;
    }

    /**
     * 获取退货时，优惠劵抵扣的金额
     * @return
     */
    @Column(name="f_return_coupon_price")
    public BigDecimal getReturnCouponPrice() {
        return returnCouponPrice;
    }

    /**
     * 获取退货时，优惠劵抵扣的金额
     * @param returnCouponPrice
     */
    public void setReturnCouponPrice(BigDecimal returnCouponPrice) {
        this.returnCouponPrice = returnCouponPrice;
    }

    /**
     * 获取维修所需费用
     * @return
     */
    @Column(name="f_repair_price")
    public BigDecimal getRepairPrice() {
        return repairPrice;
    }

    /**
     * 设置维修所需费用
     * @param repairPrice
     */
    public void setRepairPrice(BigDecimal repairPrice) {
        this.repairPrice = repairPrice;
    }

    /**
     * 获取维修支付的状态
     * @return
     */
    @Column(name="f_repair_pay_status")
    public PayStatus getRepairPayStatus() {
        return repairPayStatus;
    }

    /**
     * 设置维修支付的状态
     * @param repairPayStatus
     */
    public void setRepairPayStatus(PayStatus repairPayStatus) {
        this.repairPayStatus = repairPayStatus;
    }

    /**
     * 获取 维修记录信息
     * @return
     */
    @Column(name="f_repair_memo")
    public String getRepairMemo() {
        return repairMemo;
    }

    public void setRepairMemo(String repairMemo) {
        this.repairMemo = repairMemo;
    }


    @Converter
    public static class ReturnOrderCouponCodeCanverter extends BaseAttributeConverter<List<String>> implements AttributeConverter<Object, String> {

    }

    @Converter
    public static class ImagesConverter extends BaseAttributeConverter<List<String>> implements AttributeConverter<Object, String> {

    }
    
    @Converter
    public static class MemoConverter extends BaseAttributeConverter<List<String>> implements AttributeConverter<Object, String> {

    }
    /**
     * 获取退还神币
     * @return
     */
    @Column( name="f_return_godmonty" )
    public BigDecimal getReturnGodMoney() {
        return returnGodMoney;
    }

    /**
     * 设置退还神币
     * @param returnGodMoney
     */
    public void setReturnGodMoney(BigDecimal returnGodMoney) {
        this.returnGodMoney = returnGodMoney;
    }
}
