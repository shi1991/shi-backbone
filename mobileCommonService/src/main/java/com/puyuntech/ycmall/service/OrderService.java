package com.puyuntech.ycmall.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.puyuntech.ycmall.entity.Admin;
import com.puyuntech.ycmall.entity.Cart;
import com.puyuntech.ycmall.entity.ContractPhoneNumberUserInfo;
import com.puyuntech.ycmall.entity.CouponCode;
import com.puyuntech.ycmall.entity.GrabSeckill;
import com.puyuntech.ycmall.entity.Member;
import com.puyuntech.ycmall.entity.Order;
import com.puyuntech.ycmall.entity.Order.Type;
import com.puyuntech.ycmall.entity.Organization;
import com.puyuntech.ycmall.entity.Payment;
import com.puyuntech.ycmall.entity.PaymentMethod;
import com.puyuntech.ycmall.entity.Product;
import com.puyuntech.ycmall.entity.Promotion;
import com.puyuntech.ycmall.entity.Receiver;
import com.puyuntech.ycmall.entity.ShippingMethod;
import com.puyuntech.ycmall.entity.value.Invoice;

/**
 * 
 * 订单 Service . 
 * Created on 2015-9-11 下午2:44:20 
 * @author 施长成
 */
public interface OrderService extends BaseService<Order, Long> {
	
	/**
	 * 
	 * 订单生成 .
	 * <p>方法详细说明,如果要换行请使用<br>标签</p>
	 * <br>
	 * author: 施长成
	 *   date: 2015-9-11 下午2:45:48
	 * @param type
	 * @param cart
	 * @param receiver
	 * @param paymentMethod
	 * @param shippingMethod
	 * @param couponCode
	 * @param invoice
	 * @param balance
	 * @param memo
	 * @return
	 */
	Order generate(Order.Type type, Cart cart, Receiver receiver, PaymentMethod paymentMethod, ShippingMethod shippingMethod, Long[] couponCodeIds, Invoice invoice, BigDecimal balance, String memo);

	/**
	 * 
	 * 订单生成（直接生成订单的商品）.
	 * 
	 * author: 王凯斌
	 *   date: 2015-10-27 上午10:33:33
	 * @param type 订单类型
	 * @param product 商品
	 * @param receiver 地址
	 * @param paymentMethod 付款方式
	 * @param shippingMethod 送货方式
	 * @param couponCode 优惠券
	 * @param invoice 发票
	 * @param balance 余额
	 * @param memo 备注
	 * @return 生成的订单
	 */
	Order generate(Long userId,Order.Type type, Product product, Integer quantity, Receiver receiver, PaymentMethod paymentMethod, ShippingMethod shippingMethod, Long[] couponCodeIds, Invoice invoice, BigDecimal balance, String memo);
	/**
	 * 
	 * 计算订单金额 .
	 * <br>
	 * author: 施长成
	 *   date: 2015-9-11 下午3:09:55
	 * @param price
	 *            商品价格
	 * @param fee
	 *            支付手续费
	 * @param freight
	 *            运费
	 * @param tax
	 *            税金
	 * @param promotionDiscount
	 *            促销折扣
	 * @param couponDiscount
	 *            优惠券折扣
	 * @param offsetAmount
	 *            调整金额
	 * @return 订单金额
	 */
	BigDecimal calculateAmount(BigDecimal price, BigDecimal fee, BigDecimal freight, BigDecimal tax, BigDecimal promotionDiscount, BigDecimal couponDiscount, BigDecimal offsetAmount);

	
	/**
	 * 
	 * 计算订单金额.
	 * <br>
	 * author: 施长成
	 *   date: 2015-9-11 下午3:08:55
	 * @param order
	 * @return
	 */
	BigDecimal calculateAmount(Order order);

	/**
	 * 
	 * 创建订单 .
	 * <p>方法详细说明,如果要换行请使用<br>标签</p>
	 * <br>
	 * author: 施长成
	 *   date: 2015-9-13 下午2:28:40
	 * @param general 订单类型 积分订单 普通订单
	 * @param cart 当前购物
	 * @param receiver 收货人信息
	 * @param paymentMethod 支付方式
	 * @param shippingMethod 配送方式
	 * @param couponCode 优惠劵
	 * @param invoice 发票抬头
	 * @param godMoney 神币 
	 * @param memo 备注
	 * @return
	 */
	Order createOrder(Order.Type general, Cart cart, Receiver receiver,
			PaymentMethod paymentMethod, ShippingMethod shippingMethod,
			Long[] couponCodeIds, Invoice invoice, BigDecimal godMoney, String memo);
	
	
	Map<String, Object> createCartOrder(Order.Type type, Cart cart, Receiver receiver,
			PaymentMethod paymentMethod, ShippingMethod shippingMethod,
			Long[] couponCodeIds, Invoice invoice, Organization organization, String date, BigDecimal godMoney, String memo, String recommended);
	
	/**
	 * 
	 * 通过商品直接生成订单.
	 * 
	 * author: 王凯斌
	 *   date: 2015-10-28 上午9:06:05
	 * @param type 订单类型 
	 * @param product 商品
	 * @param quantity 数量
	 * @param receiver 地址
	 * @param paymentMethod 付款方式
	 * @param shippingMethod 配送方式
	 * @param couponCodeIds 优惠码
	 * @param invoice 发票
	 * @param balance 余额
	 * @param memo 备注
	 * @param recommended 
	 * @return 创建后的订单
	 */
	Order createOrder(Order.Type type, Product product, Member member, Integer quantity,  Receiver receiver,
			PaymentMethod paymentMethod, ShippingMethod shippingMethod,
			Long[] couponCodeIds, Invoice invoice, BigDecimal balance, String memo, String recommended);
	/**
	 * 
	 * 订单收款 .
	 * <p>方法详细说明,如果要换行请使用<br>标签</p>
	 * <br>
	 * author: 施长成
	 *   date: 2015-9-13 下午3:36:09
	 * @param order 订单
	 * @param payment 收款单
	 * @param operator 操作员
	 */
	void payment(Order order, Payment payment, Admin operator);

	/**
	 * 
	 *  订单锁定 .
	 * <p>方法详细说明,如果要换行请使用<br>标签</p>
	 * <br>
	 * author: 施长成
	 *   date: 2015-9-13 下午3:37:30
	 * @param order 订单
	 * @param member 会员
	 */
	void lock(Order order, Member member);
	
	/**
	 * 查找订单
	 * 
	 * @param type
	 *            类型
	 * @param status
	 *            状态
	 * @param member
	 *            会员
	 * @param isDelete
	 *            是否被删除
	 * @return 订单
	 */
	List<Order> findList(Order.Type type,Order.Status status,Member member,Boolean isDelete);

	/**
	 * 
	 * 测试 往 SN中插入数据 .
	 * <p>方法详细说明,如果要换行请使用<br>标签</p>
	 * <br>
	 * author: 施长成
	 *   date: 2015-9-14 下午5:56:07
	 */
	void saveSn();

	/**
	 * 
	 * 根据编号查找订单.
	 * author: 严志森
	 *   date: 2015-9-25 下午3:10:23
	 * @param sn
	 * 				编号(忽略大小写)
	 * @return 		订单，若不存在则返回null
	 */
	Order findBySn(String sn);
	
	/**
	 * 
	 * 计算优惠券价格.
	 * 
	 * author: 王凯斌
	 *   date: 2015-10-27 下午2:21:14
	 * @param cart 购物车
	 * @param product 商品
	 * @param couponsIds 优惠券ids
	 * @return 优惠价格
	 */
	BigDecimal calculateCouponDiscount(BigDecimal price,Long[] couponsIds);

	/**
	 * 
	 * 计算优惠券价格.
	 * 
	 * author: 王凯斌
	 *   date: 2015-10-27 下午2:21:14
	 * @param cart 购物车
	 * @param product 商品
	 * @param couponsIds 优惠券ids
	 * @return 优惠价格
	 */
	/**
	 * 
	 * 计算优惠券价格 重载【 calculateCouponDiscount】这个方法 .
	 * <p>方法详细说明,如果要换行请使用<br>标签</p>
	 * <br>
	 * author: 施长成
	 *   date: 2015-10-30 上午11:23:07
	 * @param priceTotal
	 * @param couponCodes
	 * @return
	 */
	BigDecimal calculateCouponDiscount(BigDecimal orderPrice, List<CouponCode> couponCodes);

	/**
	 * 
	 * 取消订单.
	 * author: 严志森
	 *   date: 2015-12-3 下午1:46:16
	 * @param order
	 */
	void cancel(Order order);

	/**
	 * 
	 * 订单收货.
	 * author: 严志森
	 *   date: 2015-12-3 下午1:56:22
	 * @param order
	 * @param operator
	 */
	void receiver(Order order, Member member);
	
	/**
	 * 
	 * 抢购订单.
	 * author: 王凯斌
	 *   date: 2015-12-10 下午1:38:22
	 * @param product 秒杀商品
	 * @param member 会员
	 * @param grab 抢购
	 * @param receiver 地址
	 * @param paymentMethod 支付方式
	 * @param shippingMethod 物流方式
	 * @param invTitle 发票
	 * @param memo 备注
	 * @param bindProductIds 组合商品
	 * @param promotion 组合促销
	 * @param date 
	 * @param organization 
	 * @param grabSeckillLogId 
	 * @return
	 */
	Order createGrabOrder(Product product, Member member,GrabSeckill grab,
			Receiver receiver, PaymentMethod paymentMethod,
			ShippingMethod shippingMethod,
			String invTitle, String memo, Long[] bindProductIds,Promotion promotion,String recommended, Organization organization, String date, Long grabSeckillLogId);

	BigDecimal findFristOrderItem(Long preOrderId);

    /**
     * 订单支付前，校验订单状态
     *
     * @param orderSn 订单编号
     * @return
     */
    Order checkOrderBeforePay(String orderSn);

	Order createOrder(Long userId,Type type, Cart cart, Product product, int i,
			Receiver receiver, PaymentMethod paymentMethod,
			ShippingMethod shippingMethod, Long[] couponCodeIds, Invoice invoice,
			BigDecimal godMoney, String memo,
			ContractPhoneNumberUserInfo contractPhoneInfo,
			Organization organization, String collectTime);

	public void undoExchangePoint(Order order);
	
	/**
     * 撤销已经使用的优惠码
     * @param order
     */
    public void undoUseCouponCode(Order order);

    /**
     * 释放已经分配商品的库存
     * @param order
     */
    public void releaseAllocatedStock(Order order);

	Map<String, Object> create(com.puyuntech.ycmall.entity.Order.Type general, Cart cart,
			Product product, Integer quantity, Receiver receiver,
			PaymentMethod paymentMethod, ShippingMethod shippingMethod,
			Long[] couponCodes, Invoice invoice, BigDecimal godMoneyNum,
			String memo, ContractPhoneNumberUserInfo contractPhoneInfo,
			Organization organization, String collectTime, String recommended);
}
