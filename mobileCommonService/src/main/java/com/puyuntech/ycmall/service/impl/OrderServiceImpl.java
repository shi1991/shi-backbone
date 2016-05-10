package com.puyuntech.ycmall.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.puyuntech.ycmall.Setting;
import com.puyuntech.ycmall.dao.AdminDao;
import com.puyuntech.ycmall.dao.CartItemDao;
import com.puyuntech.ycmall.dao.CouponCodeDao;
import com.puyuntech.ycmall.dao.CouponDao;
import com.puyuntech.ycmall.dao.OrderDao;
import com.puyuntech.ycmall.dao.OrderLogDao;
import com.puyuntech.ycmall.dao.PaymentDao;
import com.puyuntech.ycmall.dao.RebatesDao;
import com.puyuntech.ycmall.dao.SnDao;
import com.puyuntech.ycmall.entity.Admin;
import com.puyuntech.ycmall.entity.Cart;
import com.puyuntech.ycmall.entity.CartItem;
import com.puyuntech.ycmall.entity.ContractPhoneNumberUserInfo;
import com.puyuntech.ycmall.entity.Coupon;
import com.puyuntech.ycmall.entity.CouponCode;
import com.puyuntech.ycmall.entity.GodMoneyLog;
import com.puyuntech.ycmall.entity.GrabSeckill;
import com.puyuntech.ycmall.entity.GrabSeckillLog;
import com.puyuntech.ycmall.entity.Member;
import com.puyuntech.ycmall.entity.Order;
import com.puyuntech.ycmall.entity.Shipping;
import com.puyuntech.ycmall.entity.StockLog;
import com.puyuntech.ycmall.entity.Order.Status;
import com.puyuntech.ycmall.entity.Order.Type;
import com.puyuntech.ycmall.entity.OrderItem;
import com.puyuntech.ycmall.entity.OrderLog;
import com.puyuntech.ycmall.entity.Organization;
import com.puyuntech.ycmall.entity.Payment;
import com.puyuntech.ycmall.entity.PaymentMethod;
import com.puyuntech.ycmall.entity.PhoneNumber;
import com.puyuntech.ycmall.entity.PhoneNumber.PHONESTATE;
import com.puyuntech.ycmall.entity.PointBehavior;
import com.puyuntech.ycmall.entity.PointLog;
import com.puyuntech.ycmall.entity.PointRule;
import com.puyuntech.ycmall.entity.Product;
import com.puyuntech.ycmall.entity.Promotion;
import com.puyuntech.ycmall.entity.PromotionBind;
import com.puyuntech.ycmall.entity.Rebates;
import com.puyuntech.ycmall.entity.Receiver;
import com.puyuntech.ycmall.entity.ShippingMethod;
import com.puyuntech.ycmall.entity.Sn;
import com.puyuntech.ycmall.entity.value.CartItemBindProductValue;
import com.puyuntech.ycmall.entity.value.Invoice;
import com.puyuntech.ycmall.service.CartService;
import com.puyuntech.ycmall.service.GrabSeckillLogService;
import com.puyuntech.ycmall.service.MemberService;
import com.puyuntech.ycmall.service.OrderService;
import com.puyuntech.ycmall.service.PaymentMethodService;
import com.puyuntech.ycmall.service.PhoneNumberService;
import com.puyuntech.ycmall.service.PointBehaviorService;
import com.puyuntech.ycmall.service.PointLogService;
import com.puyuntech.ycmall.service.PointRuleService;
import com.puyuntech.ycmall.service.ProductService;
import com.puyuntech.ycmall.service.ReceiverService;
import com.puyuntech.ycmall.service.ShippingMethodService;
import com.puyuntech.ycmall.service.StockLogService;
import com.puyuntech.ycmall.util.SystemUtils;
import com.puyuntech.ycmall.util.UnivParameter;
import com.puyuntech.ycmall.vo.CartItemBindProductVO;

/**
 * 
 * Service 订单 . Created on 2015-9-11 下午2:47:35
 * 
 * @author 施长成
 */
@Service("orderServiceImpl")
public class OrderServiceImpl extends BaseServiceImpl<Order, Long> implements
		OrderService {
	private Logger LOGGER = Logger.getLogger(OrderServiceImpl.class);
	@Resource(name = "orderDaoImpl")
	private OrderDao orderDao;
	@Resource(name = "orderLogDaoImpl")
	private OrderLogDao orderLogDao;
	@Resource(name = "snDaoImpl")
	private SnDao snDao;
	@Resource(name = "memberServiceImpl")
	private MemberService memberService;
	@Resource(name = "cartItemDaoImpl")
	private CartItemDao cartItemDao;
	@Resource(name = "grabSeckillLogServiceImpl")
	private GrabSeckillLogService grabSeckillLogService;
	@Resource(name = "pointRuleServiceImpl")
	private PointRuleService pointRuleService;
	@Resource(name = "pointBehaviorServiceImpl")
	private PointBehaviorService pointBehaviorService;
	@Resource(name = "orderServiceImpl")
	private OrderService orderService;
	/**
	 * 收货地址 Service
	 */
	@Resource(name = "receiverServiceImpl")
	private ReceiverService receiverService;
	
	/**
	 * 支付方式 Service
	 */
	@Resource(name = "paymentMethodServiceImpl")
	private PaymentMethodService paymentMethodService;
	
	@Resource(name = "adminDaoImpl")
	private AdminDao adminDao;
	
	SimpleDateFormat format2=new SimpleDateFormat("yyyy-MM-dd");

	/**
	 * 购物方式 Service
	 */
	@Resource(name = "shippingMethodServiceImpl")
	private ShippingMethodService shippingMethodService;
	@Resource(name = "productServiceImpl")
	private ProductService productService;
	@Resource(name = "paymentDaoImpl")
	private PaymentDao paymentDao;
	@Resource(name = "couponCodeDaoImpl")
	private CouponCodeDao couponCodeDao;
	@Resource(name = "couponDaoImpl")
	private CouponDao couponDao;
	@Resource(name = "pointLogServiceImpl")
	private PointLogService pointLogService;
	
	@Resource(name = "stockLogServiceImpl")
	private StockLogService stockLogService;
	/**
	 * 购物车Service
	 */
	@Resource(name = "cartServiceImpl")
	private CartService cartService;
	@Resource(name="phoneNumberServiceImpl")
	private PhoneNumberService phoneNumberService;
	
	/**
	 * Service - 返利
	 */
	@Resource(name="rebatesDaoImpl")
	private RebatesDao rebatesDao;

	@Override
	@Transactional(readOnly = true)
	public Order generate(Order.Type type, Cart cart, Receiver receiver,
			PaymentMethod paymentMethod, ShippingMethod shippingMethod,
			Long[] couponCodeIds, Invoice invoice, BigDecimal balance,
			String memo) {
		Setting setting = SystemUtils.getSetting();
		Member member = cart.getMember();

		Order order = new Order();
		order.setType(type);
		order.setPrice(cart.getPrice());
		order.setFee(BigDecimal.ZERO);
		order.setPromotionDiscount(cart.getDiscount());

		order.setRefundAmount(BigDecimal.ZERO);
		order.setRewardPoint(cart.getEffectiveRewardPoint());
		order.setExchangePoint(cart.getExchangePoint());
		order.setWeight(cart.getWeight());
		order.setQuantity(cart.getQuantity());
		order.setShippedQuantity(0);
		order.setReturnedQuantity(0);
		order.setMemo(memo);
		order.setIsUseCouponCode(false);
		order.setIsExchangePoint(false);
		order.setIsAllocatedStock(false);
		order.setInvoice(setting.getIsInvoiceEnabled() ? invoice : null);
		order.setPaymentMethod(paymentMethod);
		order.setMember(member);
		order.setCoupons(new ArrayList<Coupon>(cart.getCoupons()));

		order.setFreight(BigDecimal.ZERO);
		order.setShippingMethod(null);

		// 优惠劵
		BigDecimal couponDiscount = new BigDecimal(0);
		order.setCouponDiscount(couponDiscount.compareTo(BigDecimal.ZERO) >= 0 ? couponDiscount
				: BigDecimal.ZERO);

		order.setTax(BigDecimal.ZERO);
		order.setAmount(calculateAmount(order));
		if (balance != null && balance.compareTo(BigDecimal.ZERO) > 0
				&& balance.compareTo(member.getGodMoney()) <= 0
				&& balance.compareTo(order.getAmount()) <= 0) {
			order.setAmount(balance);
		} else {
			order.setAmountPaid(BigDecimal.ZERO);
		}

		if (receiver != null) {
			order.setConsignee(receiver.getConsignee());
			order.setAreaName(receiver.getAreaName());
			order.setAddress(receiver.getAddress());
			order.setZipCode(receiver.getZipCode());
			order.setPhone(receiver.getPhone());
			order.setArea(receiver.getArea());
		}

		List<OrderItem> orderItems = order.getOrderItems();
		Set<Promotion> fullGiftsPromotions = new HashSet<Promotion>();
        Map<Product , Integer > giftInfo = new HashMap<Product , Integer>();

        Set<Promotion> productValidPromotion = null;
        Set<CartItem> cartItemSet = cart.getCartItems();
		for (CartItem cartItem : cartItemSet) {

			if( !cartItem.getIsSelect() ){
                continue;
            }
			
			Product product = cartItem.getProduct();

			if (product != null) {
				OrderItem orderItem = new OrderItem();
				orderItem.setSn(product.getSn());
				orderItem.setName(product.getName());
				orderItem.setType(product.getType());
				orderItem.setPrice(cartItem.getPrice());
				orderItem.setWeight(product.getWeight());
				orderItem.setIsDelivery(product.getIsDelivery());
				orderItem.setThumbnail(product.getThumbnail());
				orderItem.setQuantity(cartItem.getQuantity());
				orderItem.setShippedQuantity(0);
				orderItem.setReturnedQuantity(0);
				orderItem.setProduct(cartItem.getProduct());
				orderItem.setOrder(order);
				orderItem.setSpecifications(product.getSpecifications());

                if (CartItem.type.mainproduct == cartItem.getParentId()) {
                    List<CartItemBindProductVO> vos = new ArrayList<CartItemBindProductVO>();
                    CartItemBindProductVO vo = null;
                    List<CartItemBindProductValue> bindProducts = cartItem
                            .getBindProductIds();
                    Product bindTempProduct = null ;
                    for (CartItemBindProductValue bindProduct : bindProducts) {
                        vo = new CartItemBindProductVO();
                        vo.setPrice(bindProduct.getPrice());
                        vo.setBindQuantity(cartItem.getQuantity());
                        vo.setBindTotalPrice(bindProduct.getPrice().multiply(new BigDecimal(cartItem.getQuantity())));
                        bindTempProduct = productService.find(bindProduct.getId());
                        vo.setProduct( bindTempProduct );
                        vos.add(vo);
                    }
                    cartItem.setCartItemBindProductVOs(vos);
                }

                orderItems.add(orderItem);

                //获取购物车项单品促销的赠品信息
                productValidPromotion = product.getValidPromotions();
                if( null != productValidPromotion ){
                    for( Promotion tempProm : productValidPromotion ){
                        if( tempProm.getPromotionType() == Promotion.Type.buyGiftsPromotion ){
                            Set<Product> tempGifts = tempProm.getGifts();
                            if( null != tempGifts ){
                                for( Product tempGift : tempGifts ){
                                    if( giftInfo.containsKey(tempGift) ){
                                        giftInfo.put( tempGift , (giftInfo.get(tempGift)+cartItem.getQuantity())  );
                                    }else{
                                        giftInfo.put( tempGift ,  cartItem.getQuantity() );
                                    }
                                }
                            }
                        }else if( tempProm.getPromotionType() == Promotion.Type.fullGiftsPromotion ){
                        	//将满赠优惠促销加到Set中
                            fullGiftsPromotions.add( tempProm );
                        }
                    }
                }
			}
		}
		//添加满赠的赠品
        List<CartItem> productList = null;
        BigDecimal fullGiftsPromotionPrice = null ;
        for( Promotion promotion : fullGiftsPromotions ){
            productList = new ArrayList<CartItem>();
            fullGiftsPromotionPrice = new BigDecimal( 0 );
            for( CartItem cartItem : cartItemSet ){
                Set<Promotion> allPromotions = new HashSet<Promotion>();

                if (cartItem.getProduct() != null  && null!=cartItem.getIsSelect() && cartItem.getIsSelect() ) {
                    allPromotions.addAll(cartItem.getProduct().getValidPromotions());
                }
                if( null != allPromotions && allPromotions.contains( promotion ) ){
                    productList.add( cartItem );
                    fullGiftsPromotionPrice = fullGiftsPromotionPrice.add( cartItem.getSubtotal() );
                }
            }

            if( fullGiftsPromotionPrice.compareTo( promotion.getMinimumPrice() ) >= 0 ){
                //记录满减的促销方法
                Set<Product> tempGifts = promotion.getGifts();
                if( null != tempGifts ){
                    for( Product tempGift : tempGifts ){
                        if( giftInfo.containsKey(tempGift) ){
                            giftInfo.put( tempGift , (giftInfo.get(tempGift)+ 1 ) );
                        }else{
                            giftInfo.put( tempGift , 1 );
                        }
                    }
                }
            }
        }
        
        Product gift = null ;
        for (Map.Entry<Product, Integer> entry : giftInfo.entrySet()) {
            gift = entry.getKey();
            OrderItem orderItem = new OrderItem();
            orderItem.setSn(gift.getSn());
            orderItem.setName(gift.getName());
            orderItem.setType(gift.getType());
            orderItem.setPrice(BigDecimal.ZERO);
            orderItem.setWeight(gift.getWeight());
            orderItem.setIsDelivery(gift.getIsDelivery());
            orderItem.setThumbnail(gift.getThumbnail());
            orderItem.setQuantity(entry.getValue());
            orderItem.setShippedQuantity(0);
            orderItem.setReturnedQuantity(0);
            orderItem.setProduct(gift);
            orderItem.setOrder(order);
            orderItem.setSpecifications(gift.getSpecifications());
            orderItems.add(orderItem);
        }

		return order;
	}

	@Transactional(readOnly = true)
	public BigDecimal calculateAmount(Order order) {
		return calculateAmount(order.getPrice(), order.getFee(),
				order.getFreight(), order.getTax(),
				order.getPromotionDiscount(), order.getCouponDiscount(),
				order.getOffsetAmount());
	}

	@Override
	@Transactional(readOnly = true)
	public BigDecimal calculateAmount(BigDecimal price, BigDecimal fee,
			BigDecimal freight, BigDecimal tax, BigDecimal promotionDiscount,
			BigDecimal couponDiscount, BigDecimal offsetAmount) {
		Setting setting = SystemUtils.getSetting();
		BigDecimal amount = price;
		if (fee != null) {
			amount = amount.add(fee);
		}
		if (freight != null) {
			amount = amount.add(freight);
		}
		if (tax != null) {
			amount = amount.add(tax);
		}
		if (promotionDiscount != null) {
			amount = amount.subtract(promotionDiscount);
		}
		if (couponDiscount != null) {
			amount = amount.subtract(couponDiscount);
		}
		if (offsetAmount != null) {
			amount = amount.add(offsetAmount);
		}
		return amount.compareTo(BigDecimal.ZERO) >= 0 ? setting
				.setScale(amount) : BigDecimal.ZERO;
	}

	/**
	 * 
	 * <p>从购物车中生成并确认订单.</P>
	 * <p>Description: </P>
	 * @param type
	 * @param cart
	 * @param receiver
	 * @param paymentMethod
	 * @param shippingMethod
	 * @param couponCodeIds
	 * @param invoice
	 * @param godMoney
	 * @param memo
	 * @return
	 * @see com.puyuntech.ycmall.service.OrderService#createOrder(com.puyuntech.ycmall.entity.Order.Type, com.puyuntech.ycmall.entity.Cart, com.puyuntech.ycmall.entity.Receiver, com.puyuntech.ycmall.entity.PaymentMethod, com.puyuntech.ycmall.entity.ShippingMethod, java.lang.Long[], com.puyuntech.ycmall.entity.value.Invoice, java.math.BigDecimal, java.lang.String)
	 */
	@Override
	public Order createOrder(Type type, Cart cart, Receiver receiver,
			PaymentMethod paymentMethod, ShippingMethod shippingMethod,
			Long[] couponCodeIds, Invoice invoice, BigDecimal godMoney,
			String memo) {
		
		Setting setting = SystemUtils.getSetting();
		Member member = cart.getMember();

		Order order = new Order();

		order.setIsDelete(false);
		order.setPrice(cart.getPrice());
		order.setSn(snDao.generate(Sn.Type.order));
		order.setType(type);
		order.setFee(BigDecimal.ZERO);
		order.setFreight(BigDecimal.ZERO);// TODO 现在快递默认为0
		order.setPromotionDiscount( cart.getDiscount() );
		order.setOffsetAmount(BigDecimal.ZERO);
		order.setAmountPaid(BigDecimal.ZERO);
		order.setRefundAmount(BigDecimal.ZERO);
		order.setRewardPoint(cart.getEffectiveRewardPoint());
		order.setExchangePoint(cart.getExchangePoint());
		order.setWeight(cart.getWeight());
		order.setQuantity(cart.getQuantity());
		order.setShippedQuantity(0);
		order.setReturnedQuantity(0);

        order.setConsignee(receiver.getConsignee());
        order.setAreaName(receiver.getAreaName());
        order.setAddress(receiver.getAddress());
        order.setZipCode(receiver.getZipCode());
        order.setPhone(receiver.getPhone());
        order.setArea(receiver.getArea());

		order.setMemo(memo);
		
		order.setIsUseCouponCode(false);
		
		order.setIsExchangePoint(false);
		order.setIsAllocatedStock(false);
		order.setShippingMethod(shippingMethod);
		order.setMember(member);
		
		order.setCoupons(new ArrayList<Coupon>(cart.getCoupons()));
		
		List<CouponCode> couponCodes = new ArrayList<CouponCode>();
		//设置 使用 优惠劵 减少的价格
		if (null != couponCodeIds) {
			BigDecimal couponDiscount = new BigDecimal(0); ;
			for (Long id : couponCodeIds) {
				CouponCode couponCode = couponCodeDao.find(id);

				if (!cart.isCouponAllowed() || !cart.isValid(couponCode)) {
					logger.error("service createOrder  param coupponCode is error");
					throw new IllegalArgumentException();
				}
				//优惠劵 金额累加
				BigDecimal d = cart.getEffectivePrice().subtract(couponCode.getCoupon().calculatePrice(cart.getEffectivePrice(),cart.getProductQuantity()));
				couponDiscount = couponDiscount.add( d );
				
				couponCodes.add(couponCode);
			}
			
			order.setCouponDiscount(couponDiscount.compareTo(BigDecimal.ZERO) >= 0 ? couponDiscount: BigDecimal.ZERO);
			
		} else {
			order.setCouponDiscount(BigDecimal.ZERO);
		}
		
		order.setCouponCode(couponCodes);
		
		//设置优惠码使用
		useCouponCode(order);
		
		order.setTax(BigDecimal.ZERO);// TODO 税金 目前为 0
		order.setAmount(calculateAmount(order));

		// 余额 是否充足
		if (godMoney != null
				&& (godMoney.compareTo(BigDecimal.ZERO) < 0
						|| godMoney.compareTo(member.getGodMoney() ) > 0 || godMoney
						.compareTo(order.getAmount()) > 0)) {
			logger.error("service createOrder  param balance is error");
			throw new IllegalArgumentException();
		}
		
		//将神币根据转换比例转换成人民币  
		BigDecimal amountPayable = godMoney != null ? order.getAmount()
				.subtract( godMoney.multiply( new BigDecimal( setting.getMoneyRechargeGodMoney() )) ) : order.getAmount();

        if (paymentMethod == null) {
            logger.error("service createOrder  param paymentMethod is null");
            throw new IllegalArgumentException();
        }

        order.setPaymentMethod( paymentMethod );

        if (amountPayable.compareTo(BigDecimal.ZERO) > 0) {

            // 订单状态 默认为 等待付款
            order.setStatus(Order.Status.pendingPayment);

            //在线支付 才存在订单有效时间
            if (shippingMethod.getId() == 1l && paymentMethod.getTimeout() != null
                    && Order.Status.pendingPayment.equals(order.getStatus())) {
                order.setExpire(DateUtils.addMinutes(new Date(),
                        paymentMethod.getTimeout()));
            }

            if (PaymentMethod.Method.online.equals(paymentMethod.getMethod())) {
                lock(order, member);
            }
        } else {
            // 神币 大于 需要支付的商品价值
            if( order.getType() == Type.reserve ){
                order.setStatus(Order.Status.pendingPayment);
            }else{
                order.setStatus(Order.Status.pendingShipment);

                if( !(shippingMethod.getId() == 1l) ){
                    //门店自提，改为待自提状态
                    order.setStatus(Order.Status.daiziti);
                }
            }
        }

		List<OrderItem> orderItems = order.getOrderItems();
		// 购物车中选中的商品
		OrderItem orderItem = null;
		for (CartItem cartItem : cart.getCartItems()) {
			if (!cartItem.getIsSelect()) {
                continue;
			}
			Product product = cartItem.getProduct();
			orderItem = new OrderItem();
			orderItem.setSn(product.getSn());
			orderItem.setName(product.getName());
			orderItem.setType(product.getType());
			orderItem.setPrice(cartItem.getPrice());
			orderItem.setWeight(product.getWeight());
			orderItem.setIsDelivery(product.getIsDelivery());
			orderItem.setThumbnail(product.getThumbnail());
			orderItem.setQuantity(cartItem.getQuantity());
			orderItem.setShippedQuantity(0);
			orderItem.setReturnedQuantity(0);
			orderItem.setProduct(cartItem.getProduct());
			orderItem.setOrder(order);
			orderItem.setSpecifications(product.getSpecifications());
			orderItems.add(orderItem);
			// 若该商品存在绑定促销商品
			if (null != cartItem.getBindProductIds()
					&& cartItem.getBindProductIds().size() > 0) {
				Long[] ids = new Long[cartItem.getBindProductIds().size()];
				for (int i = 0; i < cartItem.getBindProductIds().size(); i++) {
					ids[i] = cartItem.getBindProductIds().get(i).getId();
				}

				List<CartItemBindProductValue> cartItemBindProductValueList = cartItem.getBindProductIds();

				List<Product> bindProducts = productService.findList(ids);
				if (null != bindProducts) {
					for (Product bindProduct : bindProducts) {
						orderItem = new OrderItem();
                        for(CartItemBindProductValue cartItemBindProductValue : cartItemBindProductValueList  ){
                            if( cartItemBindProductValue.getId() == bindProduct.getId() ){
                                orderItem.setPrice(cartItemBindProductValue.getPrice());
                                break;
                            }
                        }
						orderItem.setSn(bindProduct.getSn());
						orderItem.setName(bindProduct.getName());
						orderItem.setType(bindProduct.getType());
						orderItem.setWeight(bindProduct.getWeight());
						orderItem.setIsDelivery(bindProduct.getIsDelivery());
						orderItem.setThumbnail(bindProduct.getThumbnail());
						orderItem.setQuantity(cartItem.getQuantity());
						orderItem.setShippedQuantity(0);
						orderItem.setReturnedQuantity(0);
						orderItem.setProduct(bindProduct);
						orderItem.setOrder(order);
						orderItem
								.setSpecifications(product.getSpecifications());
						orderItems.add(orderItem);
					}
				}
			}
		}

		for (Product gift : cart.getGifts()) {
			orderItem = new OrderItem();
			orderItem.setSn(gift.getSn());
			orderItem.setName(gift.getName());
			orderItem.setType(gift.getType());
			orderItem.setPrice(BigDecimal.ZERO);
			orderItem.setWeight(gift.getWeight());
			orderItem.setIsDelivery(gift.getIsDelivery());
			orderItem.setThumbnail(gift.getThumbnail());
			orderItem.setQuantity(1);
			orderItem.setShippedQuantity(0);
			orderItem.setReturnedQuantity(0);
			orderItem.setProduct(gift);
			orderItem.setOrder(order);
			orderItem.setSpecifications(gift.getSpecifications());
			orderItems.add(orderItem);
		}

		orderDao.persist(order);

		// 订单日志
		OrderLog orderLog = new OrderLog();
		orderLog.setType(OrderLog.Type.create);
		orderLog.setOrder(order);
		orderLogDao.persist(orderLog);

		exchangePoint(order);

		//  判断是否在下订单的时候锁定库存 
		if (Setting.StockAllocationTime.order.equals(setting.getStockAllocationTime())
				|| (Setting.StockAllocationTime.payment.equals(setting.getStockAllocationTime()) && (order.getAmountPaid().compareTo(BigDecimal.ZERO) > 0 || order.getExchangePoint() > 0 || order.getAmountPayable().compareTo(BigDecimal.ZERO) <= 0))) {
			allocateStock(order);
		}

		 //使用神币支付 生成一条支付记录
		if (godMoney != null && godMoney.compareTo(BigDecimal.ZERO) > 0) {
			Payment payment = new Payment();
			payment.setType(Payment.Type.payment);
			payment.setMethod(Payment.Method.deposit);
			payment.setFee(BigDecimal.ZERO);
			payment.setAmount(godMoney);
			payment.setOrder(order);
			payment(order, payment, null);
		}
		
		
		/* 清除 已经完结的商品项 */
		for (CartItem cartItem : cart.getCartItems()) {
			if (cartItem.getIsSelect()) {
				cartItemDao.remove(cartItem);
			}
		}

		return order;
	}
	
	/**
	 * 优惠码使用
	 * 
	 * @param order
	 *            订单
	 */
	private void useCouponCode(Order order) {
		if (order == null
				|| BooleanUtils.isNotFalse(order.getIsUseCouponCode())
				|| order.getCouponCode() == null) {
			return;
		}

		List<CouponCode> couponCodes = order.getCouponCode();
		for (CouponCode couponCode : couponCodes) {
			couponCode.setIsUsed(true);
			couponCode.setUsedDate(new Date());
			couponCode.setOrder(order);
		}
		order.setIsUseCouponCode(true);
	}

	/**
	 * 
	 * 锁定 该订单和会员 .
	 * <p>
	 * 方法详细说明,如果要换行请使用<br>
	 * 标签
	 * </p>
	 * <br>
	 * author: 施长成 date: 2015-9-13 下午3:15:48
	 * 
	 * @param order
	 * @param member
	 */
	@Override
	public void lock(Order order, Member member) {
		boolean isLocked = order.getLockExpire() != null
				&& order.getLockExpire().after(new Date())
				&& StringUtils.isNotEmpty(order.getLockKey())
				&& !StringUtils.equals(order.getLockKey(), member.getLockKey());
		if (!isLocked && StringUtils.isNotEmpty(member.getLockKey())) {
			order.setLockKey(member.getLockKey());
			order.setLockExpire(DateUtils.addSeconds(new Date(),
					Order.LOCK_EXPIRE));
		}
	}

	/**
	 * 
	 * 扣除用户积分，并将订单状态修改为已兑换状态.
	 * <p>方法详细说明,如果要换行请使用<br>标签</p>
	 * <br>
	 * author: 施长成
	 *   date: 2015-11-2 下午1:17:31
	 * @param order
	 */
	private void exchangePoint(Order order) {
		if (order == null
				|| BooleanUtils.isNotFalse(order.getIsExchangePoint())
				|| order.getExchangePoint() <= 0 || order.getMember() == null) {
			return;
		}
		memberService.addPoint(order.getMember(), -order.getExchangePoint(),
				PointLog.Type.exchange, null, null);
		order.setIsExchangePoint(true);
		order.setStatus(Order.Status.pendingShipment);
		order.setPaymentMethod(null);
	}

	/**
	 * 分配库存
	 * 
	 * @param order
	 *            订单
	 */
	private void allocateStock(Order order) {
		if (order == null
				|| BooleanUtils.isNotFalse(order.getIsAllocatedStock())) {
			return;
		}
		if (order.getOrderItems() != null) {
			for (OrderItem orderItem : order.getOrderItems()) {

				Product product = orderItem.getProduct();

				if (product != null) {
					productService.addAllocatedStock(
							product,
							orderItem.getQuantity()
									- orderItem.getShippedQuantity());
				}
			}
		}
		order.setIsAllocatedStock(true);
	}

	@Override
	public void payment(Order order, Payment payment, Admin operator) {

		payment.setSn(snDao.generate(Sn.Type.payment));
		payment.setOrder(order);
		paymentDao.persist(payment);
		
		//减去用户使用的神币
		if (order.getMember() != null && Payment.Method.deposit.equals(payment.getMethod())) {
			memberService.addGodMoney(order.getMember(), payment.getEffectiveAmount().negate(), GodMoneyLog.Type.payment, operator, null);
		}

		Setting setting = SystemUtils.getSetting();
		if (Setting.StockAllocationTime.payment.equals(setting.getStockAllocationTime())) {
			allocateStock(order);
		}

		order.setAmountPaid(order.getAmountPaid().add(payment.getEffectiveAmount()));
		order.setFee(order.getFee().add(payment.getFee()));
		if (!order.hasExpired() && Order.Status.pendingPayment.equals(order.getStatus()) && order.getAmountPayable().compareTo(BigDecimal.ZERO) <= 0) {
            //如果订单为预定订单，支付成功后类型仍然为 等待付款，若普通订单则为等待发货
            if( order.getType() == Type.reserve ){
                order.setStatus(Order.Status.pendingPayment);
            }else{
                if( order.getShippingMethod().getId() == 1 ){
                    //快递配送
                    order.setStatus(Order.Status.pendingShipment);
                }else{
                    //门店自提
                    order.setStatus(Order.Status.daiziti);
                }
            }
			order.setExpire(null);
		}

		OrderLog orderLog = new OrderLog();
		orderLog.setType(OrderLog.Type.payment);
		orderLog.setOperator(operator);
		orderLog.setOrder(order);
		orderLogDao.persist(orderLog);
	}

	@Transactional(readOnly = true)
	public List<Order> findList(Order.Type type, Order.Status status,
			Member member, Boolean isDelete) {
		return orderDao.findList(type, status, member, isDelete);
	}

	@Override
	public void saveSn() {
		snDao.generate(Sn.Type.goods);
	}

	@Override
	public Order findBySn(String sn) {
		return orderDao.findBySn(sn);
	}

	public Order generate(Long userId,Type type, Product product, Integer quantity,
			Receiver receiver, PaymentMethod paymentMethod,
			ShippingMethod shippingMethod, Long[] couponCodeIds,
			Invoice invoice, BigDecimal balance, String memo) {
		Setting setting = SystemUtils.getSetting();
		Member member = memberService.find(userId);

		Order order = new Order();
		order.setType(type);
		if (!product.getIsPreOrder()) {
			order.setPrice(product.getPrice());
		} else {
			order.setPrice(product.getPreOrderPrice());
		}
		order.setFee(BigDecimal.ZERO);
		order.setPromotionDiscount(BigDecimal.ZERO);
		order.setOffsetAmount(BigDecimal.ZERO);
		order.setRefundAmount(BigDecimal.ZERO);
		order.setRewardPoint(product.getRewardPoint());
		order.setExchangePoint(product.getExchangePoint() * quantity);
		order.setWeight(product.getWeight());
		order.setQuantity(quantity);
		order.setShippedQuantity(0);
		order.setReturnedQuantity(0);
		order.setMemo(memo);
		order.setIsUseCouponCode(false);
		order.setIsExchangePoint(false);
		order.setIsAllocatedStock(false);
		order.setInvoice(setting.getIsInvoiceEnabled() ? invoice : null);
		order.setPaymentMethod(paymentMethod);
		order.setMember(member);

		order.setFreight(BigDecimal.ZERO);
		order.setShippingMethod(null);
		order.setCoupons(null);
		order.setCouponDiscount(BigDecimal.ZERO);
		order.setTax(BigDecimal.ZERO);
		order.setAmount(calculateAmount(order));

		if (balance != null && balance.compareTo(BigDecimal.ZERO) > 0
				&& balance.compareTo(member.getGodMoney()) <= 0
				&& balance.compareTo(order.getAmount()) <= 0) {
			order.setAmount(balance);
		} else {
			order.setAmountPaid(BigDecimal.ZERO);
		}

		if (receiver != null) {
			order.setConsignee(receiver.getConsignee());
			order.setAreaName(receiver.getAreaName());
			order.setAddress(receiver.getAddress());
			order.setZipCode(receiver.getZipCode());
			order.setPhone(receiver.getPhone());
			order.setArea(receiver.getArea());
		}

		List<OrderItem> orderItems = order.getOrderItems();

		OrderItem orderItem = new OrderItem();
		orderItem.setSn(product.getSn());
		orderItem.setName(product.getName());
		orderItem.setType(product.getType());
		if (!product.getIsPreOrder()) {
			orderItem.setPrice(product.getPrice());
		} else {
			orderItem.setPrice(product.getPreOrderPrice());
		}
		orderItem.setWeight(product.getWeight());
		orderItem.setIsDelivery(product.getIsDelivery());
		orderItem.setThumbnail(product.getThumbnail());
		orderItem.setQuantity(quantity);
		orderItem.setShippedQuantity(0);
		orderItem.setReturnedQuantity(0);
		orderItem.setProduct(product);
		orderItem.setOrder(order);
		orderItem.setSpecifications(product.getSpecifications());
		orderItems.add(orderItem);

		return order;
	}

	public BigDecimal calculateCouponDiscount(BigDecimal price, Long[] couponIds) {
		BigDecimal couponDiscount = new BigDecimal(0);
		if (null == couponIds) {
			return BigDecimal.ZERO;
		}
		if (null != price) {
			for (Long id : couponIds) {
				Coupon coupon = couponDao.find(id);
				if (coupon != null) {
					couponDiscount = couponDiscount.add(price.subtract(coupon
							.calculatePrice(price, 1)));
				}
			}
		}
		return couponDiscount;
	}

	public Order createOrder(Type type, Product product, Member member,Integer quantity,
			Receiver receiver, PaymentMethod paymentMethod,
			ShippingMethod shippingMethod, Long[] couponCodeIds,
			Invoice invoice, BigDecimal balance, String memo,String recommended) {

		Setting setting = SystemUtils.getSetting();

		Order order = new Order();
		order.setInvoice(invoice);
		order.setIsDelete(false);
		order.setSn(snDao.generate(Sn.Type.order));
		order.setRecommended(memberService.findByPhone(recommended));
		order.setType(type);

        if( type == Type.reserve ){
            //预定订单，订单金额 = 预定订单
            order.setDeposit( product.getPreOrderPrice() );
        }

		if (!product.getIsPreOrder()) {
			order.setPrice(product.getPrice());
		} else {
			order.setPrice(product.getPreOrderPrice());
		}
		order.setIsOnline(true);
		order.setFee(BigDecimal.ZERO);// TODO 现在手续默认为0
		order.setFreight(BigDecimal.ZERO);// TODO 现在快递默认为0
		
		order.setPromotionDiscount(BigDecimal.ZERO);// TODO 促销未完成
		
		order.setOffsetAmount(BigDecimal.ZERO);
		
		order.setAmountPaid(BigDecimal.ZERO);
		
		order.setRefundAmount(BigDecimal.ZERO);
		
		order.setRewardPoint(product.getRewardPoint() * quantity);
		
		order.setExchangePoint(product.getExchangePoint() * quantity);
		
		order.setWeight(product.getWeight());
		
		order.setQuantity(quantity);
		
		order.setShippedQuantity(0);
		
		order.setReturnedQuantity(0);

        order.setConsignee(receiver.getConsignee());
        order.setAreaName(receiver.getAreaName());
        order.setAddress(receiver.getAddress());
        order.setZipCode(receiver.getZipCode());
        order.setPhone(receiver.getPhone());
        order.setArea(receiver.getArea());

		order.setMemo(memo);
		order.setIsUseCouponCode(false);
		order.setIsExchangePoint(false);
		order.setIsAllocatedStock(false);
		order.setShippingMethod(shippingMethod);
		order.setMember(member);
		order.setCoupons(null);
		order.setCouponDiscount(BigDecimal.ZERO);
		order.setTax(BigDecimal.ZERO);// TODO 税金 目前为 0
		order.setAmount(calculateAmount(order));

		// 余额 是否充足
		if (balance != null
				&& (balance.compareTo(BigDecimal.ZERO) < 0
						|| balance.compareTo(member.getGodMoney()) > 0 || balance
						.compareTo(order.getAmount()) > 0)) {
			logger.error("service createOrder  param balance is error");
			throw new IllegalArgumentException();
		}
		BigDecimal amountPayable = balance != null ? order.getAmount()
				.subtract(balance) : order.getAmount();
		if (amountPayable.compareTo(BigDecimal.ZERO) > 0) {
			if (paymentMethod == null) {
				logger.error("service createOrder  param paymentMethod is null");
			}
			// 订单状态 默认为待审核状态
			order.setStatus(PaymentMethod.Type.deliveryAgainstPayment
					.equals(paymentMethod.getType()) ? Order.Status.pendingPayment
					: Order.Status.pendingShipment);
			order.setPaymentMethod(paymentMethod);
			if (paymentMethod.getTimeout() != null
					&& Order.Status.pendingPayment.equals(order.getStatus())) {
				order.setExpire(DateUtils.addMinutes(new Date(),
						paymentMethod.getTimeout()));
			}
			if (PaymentMethod.Method.online.equals(paymentMethod.getMethod())) {
				lock(order, member);
			}
		} else {
			order.setStatus(Order.Status.pendingPayment);
			order.setPaymentMethod(null);
		}

		if(type.equals(Order.Type.reserve)){
			order.setGodMoneyCount(BigDecimal.ZERO);
			order.setGodMoneyPaid(BigDecimal.ZERO);
		}
		List<OrderItem> orderItems = order.getOrderItems();
		OrderItem orderItem = null;
		orderItem = new OrderItem();
		orderItem.setSn(product.getSn());
		orderItem.setName(product.getName());
		orderItem.setType(product.getType());
		if (!product.hasPrepreOrderIng()) {
            //非预定
            orderItem.setPrice(   product.getPrice()  );
            //退款金额为 商品金额 减去 单个商品的平均优化金额

            BigDecimal returnDisCount = BigDecimal.ZERO;
            if( null != order.getPromotionDiscount() ){
                returnDisCount = order.getPromotionDiscount().divide(new BigDecimal( quantity ) , 2, BigDecimal.ROUND_DOWN);
            }
            BigDecimal returnBackPrice = product.getPrice().subtract( returnDisCount );

            returnBackPrice =  setting.setScale( returnBackPrice );

            orderItem.setReturnPrice( returnBackPrice ); //订单项单个商品退款金额
		} else {
			orderItem.setPrice(product.getPrice());
		}

		orderItem.setWeight(product.getWeight());
		orderItem.setIsDelivery(product.getIsDelivery());
		orderItem.setThumbnail(product.getThumbnail());
		orderItem.setQuantity(quantity);
		orderItem.setShippedQuantity(0);
		orderItem.setReturnedQuantity(0);
		orderItem.setProduct(product);
		orderItem.setOrder(order);
		orderItem.setSpecifications(product.getSpecifications());
		orderItems.add(orderItem);

		orderDao.persist(order);

		// 订单日志
		OrderLog orderLog = new OrderLog();
		orderLog.setType(OrderLog.Type.create);
		orderLog.setOrder(order);
		orderLogDao.persist(orderLog);

		/* 下订单的时候锁定库存 */
		if (Setting.StockAllocationTime.order.equals(setting
				.getStockAllocationTime())
				|| (Setting.StockAllocationTime.payment.equals(setting
						.getStockAllocationTime()) && (order.getAmountPaid()
						.compareTo(BigDecimal.ZERO) > 0
						|| order.getExchangePoint() > 0 || order
						.getAmountPayable().compareTo(BigDecimal.ZERO) <= 0))) {

			allocateStock(order);

		}

		if (balance != null && balance.compareTo(BigDecimal.ZERO) > 0) {
			Payment payment = new Payment();
			payment.setType(Payment.Type.prepayment);
			payment.setMethod(Payment.Method.deposit);
			payment.setFee(BigDecimal.ZERO);
			payment.setAmount(balance);
			payment.setOrder(order);
			payment(order, payment, null);
		}

		/*
		 * TODO 邮件 和 SMS 发送未做 mailService.sendCreateOrderMail(order);
		 * smsService.sendCreateOrderSms(order);
		 */
		return order;

	}

	
	@Override
	public BigDecimal calculateCouponDiscount(BigDecimal orderPrice,
			List<CouponCode> couponCodes) {
		
		BigDecimal couponDiscount = new BigDecimal(0);
		
		if (null == couponCodes) {
			return BigDecimal.ZERO;
		}
		
		if (null != orderPrice) {
			Coupon coupon = null ;
			for(CouponCode couponCode : couponCodes ){
				coupon= couponCode.getCoupon();
				if (coupon != null) {
					couponDiscount = couponDiscount.add(orderPrice.subtract( coupon.calculatePrice(orderPrice, 1) ));
				}
			}
		}
		return couponDiscount;
	}
	
	@Override
	public void cancel(Order order2) {
		Order order=orderDao.find(order2.getId());
		order.setStatus(Order.Status.canceled );
		order.setExpire(null);
		
		undoUseCouponCode(order);
		undoExchangePoint(order);
		releaseAllocatedStock(order);
		
		//返回神币
		if( order.getGodMoneyCount().compareTo(BigDecimal.ZERO ) > 0){
			memberService.addBalance(order.getMember(), order.getGodMoneyCount() ,GodMoneyLog.Type.cancelment ,null , null);
		}
		
		OrderLog orderLog = new OrderLog();
		orderLog.setType(OrderLog.Type.cancel);
		orderLog.setOrder(order);
		orderLogDao.persist(orderLog);
		
	}
	
	/**
	 * 
	 * 撤销已经使用的优惠码 .
	 * <p>方法详细说明,如果要换行请使用<br>标签</p>
	 * <br>
	 * author: 施长成
	 *   date: 2015-11-10 下午3:01:55
	 * @param order
	 */
    @Override
	public void undoUseCouponCode( Order order ){
		//订单为空，未使优惠码 ，则不进行优惠码撤销操作
		if( order == null || BooleanUtils.isNotTrue( order.getIsUseCouponCode()  ) || order.getCouponCode() == null ){
			return;
		}
		List<CouponCode> couponCodes = order.getCouponCode();
		for (CouponCode couponCode : couponCodes) {
			couponCode.setIsUsed(false);
			couponCode.setUsedDate(null);
			couponCode.setOrder( null );
		}
		order.setIsUseCouponCode(false);
	}
	
	/**
     * 将订单中的积分退回到用户账户
     *
     * @param order
     */
    @Override
	public void undoExchangePoint(Order order){
		//订单为空，未使用积分，订单中无会员信息 则不进行积分撤销操作
		if( null == order || BooleanUtils.isNotTrue(order.getIsExchangePoint()) || order.getExchangePoint()<=0 || null == order.getMember() ){
			return ;
		}
		//会员账户增加相应的积分
		memberService.addPoint(order.getMember(), order.getExchangePoint(), PointLog.Type.undoExchange, null, null);
		order.setIsExchangePoint(false);
	}
	
    /**
	 * 
	 * 释放已经分配商品的库存.
	 * <p>方法详细说明,如果要换行请使用<br>标签</p>
	 * <br>
	 * author: 施长成
	 *   date: 2015-11-10 下午3:10:16
	 * @param order
	 */
    public void releaseAllocatedStock(Order order){
		if(order == null || BooleanUtils.isNotTrue( order.getIsAllocatedStock() )){
		return ;
		}
		if( order.getOrderItems() != null ){
			for( OrderItem orderItem : order.getOrderItems() ){
				Product product = orderItem.getProduct();
				//商品存在则回退商品
				if (product != null) {
					productService.addAllocatedStock(product, -(orderItem.getQuantity() - orderItem.getShippedQuantity()));
				}
				//若是合约机 则回退手机号
				if( null != orderItem.getPhoneNumInfo() ){
					ContractPhoneNumberUserInfo  conPhone =  orderItem.getPhoneNumInfo();
					PhoneNumber phoneNum = conPhone.getPhoneNumber();
					//合约套餐中的会员 非手机号中锁定的会员则取消解锁操作
					if( null == conPhone.getMember() ||  null == phoneNum.getMember() ||  !(phoneNum.getMember().equals( conPhone.getMember() ) && phoneNum.getIsSold() == PHONESTATE.locked  )){
						continue;
					}else{
						phoneNumberService.rollback( phoneNum );
					}
				}
			}
		}
		order.setIsAllocatedStock(false);
	}
    @Transactional
	@Override
	public void receiver(Order order2, Member operator2) {
		Order order=orderDao.find(order2.getId());
		
		Member operator=memberService.find(operator2.getId());
		order.setStatus( Order.Status.completed );
		order.setCompleteDate(new Date());
		Member member = order.getMember();
		OrderLog orderLog = new OrderLog();
		orderLog.setType(OrderLog.Type.receive);
		orderLog.setOperator(operator);
		orderLog.setOrder(order);
		orderLogDao.persist(orderLog);
		
		Long maxValue = null;
		Long maxCount = null;
		Long value = null;
		Long pointLogSum = pointLogService.findByMemberNow(member);
		Long pointLogCount = pointLogService.findCountByMemberNow(member);
		
		PointBehavior behavior = pointBehaviorService.find(3l);
		Iterator<PointRule> iterator = behavior.getPointRules().iterator();
		while (iterator.hasNext()) {
			PointRule pointRule = iterator.next();
			if(pointRule.getType() == PointRule.Type.MaxSumDaily){
				maxValue = pointRule.getValue();
			}
			if(pointRule.getType() == PointRule.Type.MaxCountDaily){
				maxCount = pointRule.getValue();
			}
			if(pointRule.getType() == PointRule.Type.ValueEach){
				value = pointRule.getValue();
			}
		}
		
		Long rewardPoint = order.getRewardPoint();
		
		if(maxCount == null && maxValue == null){
			if(value == null){
				member.setPoint(member.getPoint() + rewardPoint);
			}else{
				member.setPoint(member.getPoint() + rewardPoint + value);
			}
		}else if(maxCount == null){
			if(pointLogSum < maxValue){
				if(value == null){
					member.setPoint(member.getPoint() + rewardPoint);
				}else{
					member.setPoint(member.getPoint() + rewardPoint + value);
				}
			}
		}else if(maxValue == null){
			if(pointLogCount < maxCount){
				if(value == null){
					member.setPoint(member.getPoint() + rewardPoint);
				}else{
					member.setPoint(member.getPoint() + rewardPoint + value);
				}
			}
		}else{
			if(pointLogCount < maxCount && pointLogSum < maxValue){
				if(value == null){
					member.setPoint(member.getPoint() + rewardPoint);
				}else{
					member.setPoint(member.getPoint() + rewardPoint + value);
				}
			}
		}
		memberService.update(member);
		
		PointLog log = new PointLog();
		log.setBalance(member.getPoint());
		if(value == null){
			log.setCredit(rewardPoint);
		}else{
			log.setCredit(rewardPoint + value);
		}
		
		log.setDebit(0l);
		log.setMember(member);
		log.setMemo(null);
		log.setOperator(member.getName());
		log.setType(PointLog.Type.payment);
		pointLogService.save(log);
		
		for(Shipping shipping:order.getShippings()){
            for(StockLog stockLog:shipping.getStockLogs()){
            	stockLog.setState("10");
            	stockLogService.update(stockLog);
            }
        }
		
		
		// 用户确认收货 ，给与推荐人一定的优惠 目前以积分算
		Member recomMember = order.getRecommended();
		if( null != recomMember ){
			Setting setting = SystemUtils.getSetting();
			String string = setting.getRecommendationRate();
			if(StringUtils.isEmpty(string)){
				string = "0.01";
			}
			BigDecimal rate = new BigDecimal( string );
			
			memberService.addPoint(recomMember ,  order.getPrice().multiply(rate).longValue() , PointLog.Type.commodityRecommendation, null, null);
			
//			recomMember.setPoint(order.getPrice().multiply(rate).longValue()+recomMember.getPoint());
//			memberService.update(recomMember);
			
			
//			//添加积分记录
//			PointLog pointLog = new PointLog();
//			pointLog.setType(PointLog.Type.commodityRecommendation);
//			pointLog.setCredit(order.getPrice().multiply(rate).longValue());
//			pointLog.setBalance(recomMember.getPoint());
//			pointLog.setDebit(0l);
//			pointLog.setMember(recomMember);
//			pointLogService.save(pointLog);
			
			
			
			
			
			Rebates rebatesLog = new Rebates();
			rebatesLog.setMember(recomMember);
			rebatesLog.setRebatesSpecies(Rebates.Type.point);
			rebatesLog.setRebatesCredits( order.getPrice().multiply(rate).longValue() );
			rebatesLog.setTime( new Date() );
			rebatesLog.setRebatesType((char) 2);
			rebatesDao.merge(rebatesLog);
		}
		//订单完成 给与相应的积分
        if (order.getRewardPoint() > 0) {
            memberService.addPoint(member, order.getRewardPoint(), PointLog.Type.reward, null, null);
        }

        if (order.getAmountPaid().compareTo(BigDecimal.ZERO) > 0) {
            memberService.addAmount(member, order.getAmountPaid());
        }
	}
	
	public Order createGrabOrder(Product product, Member member,GrabSeckill grab,Receiver receiver, PaymentMethod paymentMethod,ShippingMethod shippingMethod,
			String invTitle, String memo, Long[] bindProductIds,Promotion promotion,String recommended,Organization organization, String date,Long grabSeckillLogId) {

		Setting setting = SystemUtils.getSetting();
		
		// 发票 抬头
		Invoice invoice = StringUtils.isEmpty(invTitle) ? null :new Invoice(
				invTitle, null);

		Order order = new Order();
		
		order.setInvoice(invoice);
		order.setIsOnline(true);
		order.setIsDelete(false);
		order.setSn(snDao.generate(Sn.Type.order));
		order.setType(Order.Type.grab);
		order.setFee(BigDecimal.ZERO);// TODO 现在手续默认为0
		order.setFreight(BigDecimal.ZERO);// TODO 现在快递默认为0
		
		order.setOffsetAmount(BigDecimal.ZERO);
		
		order.setAmountPaid(BigDecimal.ZERO);
		
		order.setRefundAmount(BigDecimal.ZERO);
		
		order.setShippedQuantity(0);
		
		order.setReturnedQuantity(0);
		
		if( receiver != null){
	        order.setConsignee(receiver.getConsignee() );
	        order.setAreaName(receiver.getAreaName());
	        order.setAddress(receiver.getAddress());
	        order.setZipCode(receiver.getZipCode());
            order.setPhone(receiver.getPhone());
            order.setArea(receiver.getArea());     
	    }
	    order.setOrganization( organization );
        order.setCollectTime( date );
		
		order.setMemo(memo);
		order.setIsUseCouponCode(false);
		order.setIsExchangePoint(false);
		order.setIsAllocatedStock(true);
		order.setShippingMethod(shippingMethod);
		order.setMember(member);
		order.setCoupons(null);
		order.setCouponDiscount(BigDecimal.ZERO);
		order.setTax(BigDecimal.ZERO);// TODO 税金 目前为 0
		order.setGodMoneyCount(BigDecimal.ZERO);
		order.setGodMoneyPaid(BigDecimal.ZERO);
		order.setRecommended(memberService.findByPhone(recommended));
		
		if (paymentMethod == null) {
			logger.error("service createOrder  param paymentMethod is null");
		}
		// 订单状态 默认为待付款
		order.setStatus(Order.Status.pendingPayment);
		order.setPaymentMethod(paymentMethod);

		//设置超时15分钟过期
		if (Order.Status.pendingPayment.equals(order.getStatus())) {
			order.setExpire(DateUtils.addMinutes(new Date(),15));
		}
		if (PaymentMethod.Method.online.equals(paymentMethod.getMethod())) {
			lock(order, member);
		}
		List<OrderItem> orderItems = order.getOrderItems();

		OrderItem grabOrderItem = new OrderItem();
		grabOrderItem.setSn(product.getSn());
		grabOrderItem.setName(product.getName());
		grabOrderItem.setType(product.getType());

        grabOrderItem.setPrice(   grab.getPrice()  );

//        //退款金额为 商品金额 减去 单个商品的平均优化金额
//        BigDecimal returnDisCount = BigDecimal.ZERO;
//        if( null != order.getPromotionDiscount() ){
//            returnDisCount = order.getPromotionDiscount().divide(new BigDecimal( 1 ) , 2, BigDecimal.ROUND_DOWN);
//        }
//        BigDecimal returnBackPrice = product.getPrice().subtract( returnDisCount );

//        returnBackPrice =  setting.setScale( returnBackPrice );

        grabOrderItem.setReturnPrice( grab.getPrice() ); //订单项单个商品退款金额

		grabOrderItem.setWeight(product.getWeight());
		grabOrderItem.setIsDelivery(product.getIsDelivery());
		grabOrderItem.setThumbnail(product.getThumbnail());
		grabOrderItem.setQuantity(1);
		grabOrderItem.setShippedQuantity(0);
		grabOrderItem.setReturnedQuantity(0);
		grabOrderItem.setProduct(product);
		grabOrderItem.setOrder(order);
		grabOrderItem.setSpecifications(product.getSpecifications());
		orderItems.add(grabOrderItem);
		
		PromotionBind promotionBind = null;
		if(promotion!=null){
			promotionBind = promotion.getPromotionBind();
		}
		if(promotionBind!=null){
			if(bindProductIds!=null&&bindProductIds.length>0){
				for(Long bindProductId:bindProductIds){
					
					OrderItem orderItem = new OrderItem();
					Product bindProduct = productService.find(bindProductId);
					if(bindProduct==null){
						continue;
					}
					if(bindProduct.equals(promotionBind.getProduct1())){
						
						orderItem.setSn(bindProduct.getSn());
						orderItem.setName(bindProduct.getName());
						orderItem.setType(bindProduct.getType());
						orderItem.setPrice(promotionBind.getPrice1());
						orderItem.setWeight(bindProduct.getWeight());
						orderItem.setIsDelivery(bindProduct.getIsDelivery());
						orderItem.setThumbnail(bindProduct.getThumbnail());
						orderItem.setQuantity(1);
						orderItem.setShippedQuantity(0);
						orderItem.setReturnedQuantity(0);
						orderItem.setProduct(bindProduct);
						orderItem.setOrder(order);
						orderItem.setSpecifications(bindProduct.getSpecifications());
						orderItems.add(orderItem);
					}
					
					if(bindProduct.equals(promotionBind.getProduct2())){
						
						orderItem.setSn(bindProduct.getSn());
						orderItem.setName(bindProduct.getName());
						orderItem.setType(bindProduct.getType());
						orderItem.setPrice(promotionBind.getPrice2());
						orderItem.setWeight(bindProduct.getWeight());
						orderItem.setIsDelivery(bindProduct.getIsDelivery());
						orderItem.setThumbnail(bindProduct.getThumbnail());
						orderItem.setQuantity(1);
						orderItem.setShippedQuantity(0);
						orderItem.setReturnedQuantity(0);
						orderItem.setProduct(bindProduct);
						orderItem.setOrder(order);
						orderItem.setSpecifications(bindProduct.getSpecifications());
						orderItems.add(orderItem);
					}
					
					if(bindProduct.equals(promotionBind.getProduct3())){
						
						orderItem.setSn(bindProduct.getSn());
						orderItem.setName(bindProduct.getName());
						orderItem.setType(bindProduct.getType());
						orderItem.setPrice(promotionBind.getPrice3());
						orderItem.setWeight(bindProduct.getWeight());
						orderItem.setIsDelivery(bindProduct.getIsDelivery());
						orderItem.setThumbnail(bindProduct.getThumbnail());
						orderItem.setQuantity(1);
						orderItem.setShippedQuantity(0);
						orderItem.setReturnedQuantity(0);
						orderItem.setProduct(bindProduct);
						orderItem.setOrder(order);
						orderItem.setSpecifications(bindProduct.getSpecifications());
						orderItems.add(orderItem);
					}
					
					if(bindProduct.equals(promotionBind.getProduct4())){
						
						orderItem.setSn(bindProduct.getSn());
						orderItem.setName(bindProduct.getName());
						orderItem.setType(bindProduct.getType());
						orderItem.setPrice(promotionBind.getPrice4());
						orderItem.setWeight(bindProduct.getWeight());
						orderItem.setIsDelivery(bindProduct.getIsDelivery());
						orderItem.setThumbnail(bindProduct.getThumbnail());
						orderItem.setQuantity(1);
						orderItem.setShippedQuantity(0);
						orderItem.setReturnedQuantity(0);
						orderItem.setProduct(bindProduct);
						orderItem.setOrder(order);
						orderItem.setSpecifications(bindProduct.getSpecifications());
						orderItems.add(orderItem);
					}
					
					if(bindProduct.equals(promotionBind.getProduct5())){
						
						orderItem.setSn(bindProduct.getSn());
						orderItem.setName(bindProduct.getName());
						orderItem.setType(bindProduct.getType());
						orderItem.setPrice(promotionBind.getPrice5());
						orderItem.setWeight(bindProduct.getWeight());
						orderItem.setIsDelivery(bindProduct.getIsDelivery());
						orderItem.setThumbnail(bindProduct.getThumbnail());
						orderItem.setQuantity(1);
						orderItem.setShippedQuantity(0);
						orderItem.setReturnedQuantity(0);
						orderItem.setProduct(bindProduct);
						orderItem.setOrder(order);
						orderItem.setSpecifications(bindProduct.getSpecifications());
						orderItems.add(orderItem);
					}
				}
			}
		}
		
		order.setPrice(BigDecimal.ZERO);
		order.setPromotionDiscount(BigDecimal.ZERO);
		order.setRewardPoint(0L);
		order.setExchangePoint(0L);
		order.setWeight(0);
		for(OrderItem orderItem:order.getOrderItems()){
			
			order.setPrice(order.getPrice().add(orderItem.getPrice()));
			
			order.setRewardPoint(order.getRewardPoint()+orderItem.getProduct().getRewardPoint());
		
			order.setExchangePoint(order.getExchangePoint()+orderItem.getProduct().getExchangePoint());
	
			order.setWeight(order.getWeight()+ (orderItem.getWeight() == null ? 0 : orderItem.getWeight()));
	
		}
		if(bindProductIds!=null){
			order.setQuantity(bindProductIds.length+1);
		}else{
			order.setQuantity(1);
		}
		
		order.setAmount(order.getPrice());
		
		orderDao.persist(order);

		// 订单日志
		OrderLog orderLog = new OrderLog();
		orderLog.setType(OrderLog.Type.create);
		orderLog.setOrder(order);
		orderLogDao.persist(orderLog);
		
//		exchangePoint(order);

		/* 下订单的时候锁定库存 */
		if (Setting.StockAllocationTime.order.equals(setting
				.getStockAllocationTime())
				|| (Setting.StockAllocationTime.payment.equals(setting
						.getStockAllocationTime()) && (order.getAmountPaid()
						.compareTo(BigDecimal.ZERO) > 0
						|| order.getExchangePoint() > 0 || order
						.getAmountPayable().compareTo(BigDecimal.ZERO) <= 0))) {

			allocateStock(order);

		}
		
		
		GrabSeckillLog grabSeckillLog = grabSeckillLogService.find(grabSeckillLogId);
		grabSeckillLog.setGoods(grab.getGoods());
		
		if(grab.getType() == GrabSeckill.GrabSecKillTypeEnmu.GRAB){
			grabSeckillLog.setType(com.puyuntech.ycmall.entity.GrabSeckillLog.GrabSecKillTypeEnmu.GRAB);
		}
		
		if(grab.getType() == GrabSeckill.GrabSecKillTypeEnmu.OTHER){
			grabSeckillLog.setType(com.puyuntech.ycmall.entity.GrabSeckillLog.GrabSecKillTypeEnmu.OTHER);
		}
		
		if(grab.getType() == GrabSeckill.GrabSecKillTypeEnmu.SECKILL){
			grabSeckillLog.setType(com.puyuntech.ycmall.entity.GrabSeckillLog.GrabSecKillTypeEnmu.SECKILL);
		}
		
		grabSeckillLog.setPrice(grab.getPrice() == null ? BigDecimal.ZERO : grab.getPrice() );
		
		grabSeckillLog.setGrabSeckills(grab);
		
		if(grab.getGoodsTypes() == GrabSeckill.GoodsTypeEnmu.BONUS){
			grabSeckillLog.setGoodsTypes(com.puyuntech.ycmall.entity.GrabSeckillLog.GoodsTypeEnmu.BONUS);
		}
		if(grab.getGoodsTypes() == GrabSeckill.GoodsTypeEnmu.COUPONS){
			grabSeckillLog.setGoodsTypes(com.puyuntech.ycmall.entity.GrabSeckillLog.GoodsTypeEnmu.COUPONS);
		}
		if(grab.getGoodsTypes() == GrabSeckill.GoodsTypeEnmu.GOODS){
			grabSeckillLog.setGoodsTypes(com.puyuntech.ycmall.entity.GrabSeckillLog.GoodsTypeEnmu.GOODS);
		}
		if(grab.getGoodsTypes() == GrabSeckill.GoodsTypeEnmu.OTHER){
			grabSeckillLog.setGoodsTypes(com.puyuntech.ycmall.entity.GrabSeckillLog.GoodsTypeEnmu.OTHER);
		}
		
		
		grabSeckillLog.setMember(order.getMember());
		
		grabSeckillLog.setOrder(order);
		
		grabSeckillLog.setDatetime(new Date());
		
		grabSeckillLogService.update(grabSeckillLog);
		/*
		 * TODO 邮件 和 SMS 发送未做 mailService.sendCreateOrderMail(order);
		 * smsService.sendCreateOrderSms(order);
		 */


		return order;

	}

    /**
     *
     * @param type
     * @param cart
     * @param receiver
     * @param paymentMethod
     * @param shippingMethod
     * @param couponCodeIds
     * @param invoice
     * @param organization
     * @param date
     * @param godMoney
     * @param memo
     * @param recommended
     * @return
     *
     * update 施长成
     *  从购物车生成订单，对 订单项 添加 退货所需退款字段，以及值计算
     */
	@SuppressWarnings("unused")
	@Override
	public Map<String, Object> createCartOrder(Type type, Cart cart, Receiver receiver,
			PaymentMethod paymentMethod, ShippingMethod shippingMethod,
			Long[] couponCodeIds, Invoice invoice, Organization organization, String date,BigDecimal godMoney,
			String memo, String recommended) {
		Map<String, Object> result=new HashMap<String, Object>();
		try {
			Setting setting = SystemUtils.getSetting();
			Member member = cart.getMember();

			Order order = new Order();

			order.setIsDelete(false);
			order.setPrice(cart.getPrice());
			order.setSn(snDao.generate(Sn.Type.order));
			order.setType(type);
			order.setFee(BigDecimal.ZERO);
			order.setFreight(BigDecimal.ZERO);// TODO 现在快递默认为0
			order.setPromotionDiscount(cart.getDiscount());
			order.setOffsetAmount(BigDecimal.ZERO);
			order.setAmountPaid(BigDecimal.ZERO);
			order.setRefundAmount(BigDecimal.ZERO);
			order.setRewardPoint(cart.getEffectiveRewardPoint());
			order.setExchangePoint(cart.getExchangePoint());
			order.setWeight(cart.getWeight());
			order.setQuantity(cart.getQuantity());
			order.setShippedQuantity(0);
			order.setReturnedQuantity(0);

	        order.setInvoice(setting.getIsInvoiceEnabled() ? invoice : null);
	       
	        if( receiver != null){
	        	order.setConsignee(receiver.getConsignee() );
	            order.setAreaName(receiver.getAreaName());
	            order.setAddress(receiver.getAddress());
	            order.setZipCode(receiver.getZipCode());
	            order.setPhone(receiver.getPhone());
	            order.setArea(receiver.getArea());
	        }
	        order.setOrganization( organization );
            order.setCollectTime( date );

			order.setMemo(memo);

			order.setIsUseCouponCode(false);
			//添加订单推荐人信息
			Member recomMember = memberService.findByPhone( recommended );
			if( null !=  recomMember ){
				order.setRecommended(recomMember);
			}
			order.setIsExchangePoint(false);
			order.setIsAllocatedStock(false);
			order.setShippingMethod(shippingMethod);
			order.setMember(member);

			order.setCoupons(new ArrayList<Coupon>(cart.getCoupons()));

			List<CouponCode> couponCodes = new ArrayList<CouponCode>();
			// 设置 使用 优惠劵 减少的价格
			if (null != couponCodeIds) {
				BigDecimal couponDiscount = new BigDecimal(0);

				for (Long id : couponCodeIds) {
					CouponCode couponCode = couponCodeDao.find(id);

					if (!cart.isCouponAllowed() || !cart.isValid(couponCode)) {
						logger.error("service createOrder  param coupponCode is error");
						throw new IllegalArgumentException();
					}
					// 优惠劵 金额累加
					BigDecimal d = cart.getEffectivePrice().subtract(
							couponCode.getCoupon().calculatePrice(
									cart.getEffectivePrice(),
									cart.getProductQuantity()));
					couponDiscount = couponDiscount.add(d);

					couponCodes.add(couponCode);
				}

				order.setCouponDiscount(couponDiscount.compareTo(BigDecimal.ZERO) >= 0 ? couponDiscount
						: BigDecimal.ZERO);

			} else {
				order.setCouponDiscount(BigDecimal.ZERO);
			}

			order.setCouponCode(couponCodes);

			// 设置优惠码使用
			useCouponCode(order);

			order.setTax(BigDecimal.ZERO);// TODO 税金 目前为 0
			order.setAmount(calculateAmount(order));

			// 神币使用前 检查 神币使用数量 ， 用户神币数量，神币使用数量和订单金额
			if (godMoney != null
					&& ( godMoney.compareTo(BigDecimal.ZERO) < 0 || godMoney.compareTo(member.getGodMoney()) > 0 || godMoney.compareTo(order.getAmount()) > 0)) {
				logger.error("service createOrder  godmoney use is error");
				throw new IllegalArgumentException( "神币使用失败" );
			}

			// 将神币根据转换比例转换成人民币
			BigDecimal amountPayable = godMoney != null ? order.getAmount()
					.subtract(
							godMoney.multiply(new BigDecimal(setting.getMoneyRechargeGodMoney())) 
							) : order.getAmount();
			
			//使用神币数量		
			if( godMoney != null  ){
				order.setGodMoneyCount( godMoney  );
				order.setGodMoneyPaid( godMoney.multiply(new BigDecimal(setting.getMoneyRechargeGodMoney()))  );
			}else{
				order.setGodMoneyCount( BigDecimal.ZERO  );
				order.setGodMoneyPaid( BigDecimal.ZERO );
			}

            if (paymentMethod == null) {
                logger.error("service createOrder  param paymentMethod is null");
                throw new IllegalArgumentException();
            }

            order.setPaymentMethod( paymentMethod );

            if (amountPayable.compareTo(BigDecimal.ZERO) > 0) {

                // 订单状态 默认为 等待付款
                order.setStatus(Order.Status.pendingPayment);

                //在线支付 才存在订单有效时间
                if ( paymentMethod.getTimeout() != null
                        && Order.Status.pendingPayment.equals(order.getStatus())) {
                    order.setExpire(DateUtils.addMinutes(new Date(),
                            paymentMethod.getTimeout()));
                }

                if (PaymentMethod.Method.online.equals(paymentMethod.getMethod())) {
                    lock(order, member);
                }
            } else {
                // 神币 大于 需要支付的商品价值
                if( order.getType() == Type.reserve ){
                    order.setStatus(Order.Status.pendingPayment);
                }else{
                    order.setStatus(Order.Status.pendingShipment);

                    if( !(shippingMethod.getId() == 1l) ){
                        //门店自提，改为待自提状态
                        order.setStatus(Order.Status.daiziti);
                    }
                }
            }

			List<OrderItem> orderItems = order.getOrderItems();
	        //该购物车中包含的所有赠礼（不包括数量）
	        Set<Product> gifts = cart.getGifts();

	        Map<Product , Integer > giftInfo = new HashMap<Product , Integer>();
	        
	        Set<Promotion> productValidPromotion = null;

			// 购物车中选中的商品
			OrderItem orderItem = null;
			Set<Promotion> fullGiftsPromotions = new HashSet<Promotion>();
			Set<CartItem> cartItemSet = cart.getCartItems();

            Set<Promotion> promotions = null;

			for (CartItem cartItem : cartItemSet) {
				if (!cartItem.getIsSelect()) {
                    continue;
				}
				Product product = cartItem.getProduct();
				orderItem = new OrderItem();
				orderItem.setSn(product.getSn());
				orderItem.setName(product.getName());
				orderItem.setType(product.getType());
				orderItem.setPrice(cartItem.getPrice());
				orderItem.setWeight(product.getWeight());
				orderItem.setIsDelivery(product.getIsDelivery());
				orderItem.setThumbnail(product.getThumbnail());
				orderItem.setQuantity(cartItem.getQuantity());
				orderItem.setShippedQuantity(0);
				orderItem.setReturnedQuantity(0);
				orderItem.setProduct(cartItem.getProduct());

                BigDecimal returnBackPrice = cartItem.getPrice();

                //普通商品才具有促销
                promotions = product.getValidPromotions();
                for (Promotion promotion : promotions) {
                    if (promotion.getPromotionType() == Promotion.Type.fullCutPromotion) {
                        //商品存在满减
                        BigDecimal promotionPrice = cart.getPromotionAllPrice(promotion);
                        String priceExpress = promotion.getPriceExpression();
                        String[] priceExpresses = priceExpress.split("-");
                        BigDecimal promotonDisPrice = BigDecimal.ZERO;
                        //每个促销的优惠价格
                        BigDecimal disPrice = BigDecimal.ZERO;
                        try {
                            //若表达式存在异常需要捕捉出来
                            promotonDisPrice = new BigDecimal(priceExpresses[1]);

                            //折扣金额 除以 该优惠的商品总价格 乘以该商品的单价格 (disPrice / promotionPrice)*cartItem.getPrice()
                            disPrice = promotonDisPrice.multiply( cartItem.getPrice() ).divide( promotionPrice ,2, BigDecimal.ROUND_DOWN  );
                        } catch (Exception e) {
                            logger.error(e.getMessage());
                        }

                        //returnBackPrice - 减去优惠金额
                        returnBackPrice = returnBackPrice.subtract( disPrice );
                    }else if( promotion.getPromotionType() ==  Promotion.Type.productPromotion){
                        //商品存在单品促销
                        BigDecimal currentPrice = promotion.calculatePrice( cartItem.getPrice() , 1);
                        returnBackPrice = returnBackPrice.subtract( cartItem.getPrice().subtract(currentPrice) );
                    }
                }

                //根据配置获取金额的精确位
                returnBackPrice =  setting.setScale( returnBackPrice );

                orderItem.setReturnPrice( returnBackPrice ); //订单项单个商品退款金额

				orderItem.setOrder(order);
				orderItem.setSpecifications(product.getSpecifications());
				orderItems.add(orderItem);
				// 若该商品存在绑定促销商品
				if (null != cartItem.getBindProductIds()
						&& cartItem.getBindProductIds().size() > 0) {
					Long[] ids = new Long[cartItem.getBindProductIds().size()];
					for (int i = 0; i < cartItem.getBindProductIds().size(); i++) {
						ids[i] = cartItem.getBindProductIds().get(i).getId();
					}

                    List<CartItemBindProductValue> cartItemBindProductValueList = cartItem.getBindProductIds();

					List<Product> bindProducts = productService.findList(ids);
					if (null != bindProducts) {
						for (Product bindProduct : bindProducts) {
							orderItem = new OrderItem();
                            for(CartItemBindProductValue cartItemBindProductValue : cartItemBindProductValueList  ){
                                if( cartItemBindProductValue.getId() == bindProduct.getId() ){
                                    orderItem.setPrice(cartItemBindProductValue.getPrice());
                                    break;
                                }
                            }
							orderItem.setSn(bindProduct.getSn());
							orderItem.setName(bindProduct.getName());
							orderItem.setType(bindProduct.getType());
//							orderItem.setPrice(cartItem.getPrice());
							orderItem.setWeight(bindProduct.getWeight());
							orderItem.setIsDelivery(bindProduct.getIsDelivery());
							orderItem.setThumbnail(bindProduct.getThumbnail());
							orderItem.setQuantity(cartItem.getQuantity());
							orderItem.setShippedQuantity(0);
							orderItem.setReturnedQuantity(0);
							orderItem.setProduct(bindProduct);
							orderItem.setOrder(order);
                            orderItem.setSpecifications(product.getSpecifications());
							orderItems.add(orderItem);
						}
					}
				}

	            //赠品项
	            //获取购物车项单品促销的赠品信息
	            productValidPromotion = product.getValidPromotions();
	            if( null != productValidPromotion ){
	                for( Promotion tempProm : productValidPromotion ){
	                    if( tempProm.getPromotionType() == Promotion.Type.buyGiftsPromotion ){
	                        Set<Product> tempGifts = tempProm.getGifts();
	                        if( null != tempGifts ){
	                            for( Product tempGift : tempGifts ){
	                                if( giftInfo.containsKey(tempGift) ){
	                                    giftInfo.put( tempGift , (giftInfo.get(tempGift)+cartItem.getQuantity())  );
	                                }else{
	                                    giftInfo.put( tempGift ,  cartItem.getQuantity() );
	                                }
	                            }
	                        }
	                    }else if( tempProm.getPromotionType() == Promotion.Type.fullGiftsPromotion ){
	                    	//将满赠优惠促销加到Set中
                            fullGiftsPromotions.add( tempProm );
	                    }
	                }
	            }
	        }
		//添加满赠的赠品
        List<CartItem> productList = null;
        BigDecimal fullGiftsPromotionPrice = null ;
        for( Promotion promotion : fullGiftsPromotions ){
            productList = new ArrayList<CartItem>();
            fullGiftsPromotionPrice = new BigDecimal( 0 );
            for( CartItem cartItem : cartItemSet ){
                Set<Promotion> allPromotions = new HashSet<Promotion>();

                if (cartItem.getProduct() != null  && null!=cartItem.getIsSelect() && cartItem.getIsSelect() ) {
                    allPromotions.addAll(cartItem.getProduct().getValidPromotions());
                }
                if( null != allPromotions && allPromotions.contains( promotion ) ){
                    productList.add( cartItem );
                    fullGiftsPromotionPrice = fullGiftsPromotionPrice.add( cartItem.getSubtotal() );
                }
            }

            if( fullGiftsPromotionPrice.compareTo( promotion.getMinimumPrice() ) >= 0 ){
                //记录满减的促销方法
                Set<Product> tempGifts = promotion.getGifts();
                if( null != tempGifts ){
                    for( Product tempGift : tempGifts ){
                        if( giftInfo.containsKey(tempGift) ){
                            giftInfo.put( tempGift , (giftInfo.get(tempGift)+ 1 ) );
                        }else{
                            giftInfo.put( tempGift , 1 );
                        }
                    }
                }
            }
        }
	        Product gift = null ;

            int quantity = 0;
	        for (Map.Entry<Product, Integer> entry : giftInfo.entrySet()) {
	            gift = entry.getKey();
	            orderItem = new OrderItem();
	            orderItem.setSn(gift.getSn());
	            orderItem.setName(gift.getName());
	            orderItem.setType(gift.getType());
	            orderItem.setPrice(BigDecimal.ZERO);
	            orderItem.setWeight(gift.getWeight());
	            orderItem.setIsDelivery(gift.getIsDelivery());
	            orderItem.setThumbnail(gift.getThumbnail());
	            orderItem.setQuantity(entry.getValue());
	            orderItem.setShippedQuantity(0);
	            orderItem.setReturnedQuantity(0);
	            orderItem.setProduct(gift);
	            orderItem.setOrder(order);
	            orderItem.setSpecifications(gift.getSpecifications());
                quantity+=orderItem.getQuantity();
	            orderItems.add(orderItem);
	        }
            order.setQuantity( order.getQuantity() + quantity );

			//设置为线上订单
			order.setIsOnline(true);
			
			
			
			orderService.save(order);

			// 订单日志
			OrderLog orderLog = new OrderLog();
			orderLog.setType(OrderLog.Type.create);
			orderLog.setOrder(order);
			orderLogDao.persist(orderLog);

			exchangePoint(order);

			// 判断是否在下订单的时候锁定库存
			if (Setting.StockAllocationTime.order.equals(setting
					.getStockAllocationTime())
					|| (Setting.StockAllocationTime.payment.equals(setting
							.getStockAllocationTime()) && (order.getAmountPaid()
							.compareTo(BigDecimal.ZERO) > 0
							|| order.getExchangePoint() > 0 || order
							.getAmountPayable().compareTo(BigDecimal.ZERO) <= 0))) {
				allocateStock(order);
			}

			// 使用神币支付 生成一条支付记录
			if (godMoney != null && godMoney.compareTo(BigDecimal.ZERO) > 0) {
				Payment payment = new Payment();
				payment.setType(Payment.Type.payment);
				payment.setMethod(Payment.Method.deposit);
				payment.setFee(BigDecimal.ZERO);
				payment.setAmount(godMoney);
				payment.setOrder(order);
				payment(order, payment, null);
			}

			/* 清除 已经完结的商品项 */
			for (CartItem cartItem : cart.getCartItems()) {
				if (cartItem.getIsSelect()) {
					cartItemDao.remove(cartItem);
				}
			}
//			if(order.getAmountReceivable().compareTo(BigDecimal.ZERO) <= 0 && shippingMethod.getId() == 2l){
//				order.setStatus(Order.Status.daiziti);
//			}else{
//				order.setStatus(Order.Status.pendingPayment);
//			}
			result.put(UnivParameter.CODE, UnivParameter.DATA_CORRECTCODE);
			//加载逻辑处理层崩溃性MESSAGE
			result.put(UnivParameter.DATA, order);
		} catch (Exception e) {
			e.printStackTrace();
			//清空结果数据结构体-准备装载逻辑层错误信息
			result.clear();
			
			result.put(UnivParameter.CODE, UnivParameter.DATA_ERRORCODE);
			//加载逻辑处理层崩溃性MESSAGE
			result.put(UnivParameter.REASON, e.getMessage());
			
			//LOG日志文件编写
			LOGGER.error(e.getMessage(), e);
		}
		

		return result;
	}
	
	public BigDecimal getPrice(Cart cart) {
		BigDecimal price = BigDecimal.ZERO;
		if (cart.getCartItems() != null) {
			for (CartItem cartItem : cart.getCartItems()) {
				if( null !=cartItem && null!=cartItem.getIsSelect() && cartItem.getIsSelect() ){
					price = price.add(cartItem.getSubtotal());
					if( cartItem.getParentId() == CartItem.type.mainproduct ){
						BigDecimal quantity = new BigDecimal(cartItem.getQuantity());
						List<CartItemBindProductValue> bindProducts =cartItem.getBindProductIds();
						
						for( CartItemBindProductValue bindProduct : bindProducts){
							price =price.add( bindProduct.getPrice().multiply( quantity ) );
						}
					}
				}
			}
		}
		
		return price;
	}

	@Override
	public BigDecimal findFristOrderItem(Long preOrderId) {
		Order order = orderDao.find(preOrderId);
		return order.getOrderItems().get(0).getProduct().getPrice();
		
	}

    @Override
    public Order checkOrderBeforePay(String orderSn) {
        Order order = this.findBySn(orderSn );

        if (order.getPaymentMethod() == null || !PaymentMethod.Method.online.equals(order.getPaymentMethod().getMethod())) {
            return null;
        }

       /* if (order.getAmountPayable().compareTo(BigDecimal.ZERO) <= 0) {
            return null;
        }*/

        return order;
    }

	@Override
	public Order createOrder(Long userId,Type type, Cart cart, Product product, int i,
			Receiver receiver, PaymentMethod paymentMethod,
			ShippingMethod shippingMethod, Long[] couponCodeIds, Invoice invoice,
			BigDecimal godMoney, String memo,
			ContractPhoneNumberUserInfo contractPhoneInfo,
			Organization organization, String collectTime) {
		Setting setting = SystemUtils.getSetting();
		Member member = memberService.find(userId);
		Order order = new Order();
		order.setIsDelete(false);

		// 订单总金额
		BigDecimal allPrice = cart != null ? cart.getPrice() : product
				.getPrice();

        if( type == Type.reserve ){
            //预定订单，订单金额 = 预定订单
            order.setDeposit( product.getPreOrderPrice() );
        }

		if (product.hasPrepreOrderIng()) {
			allPrice = product.getPreOrderPrice();
		}

		// 合约机待手机卡时 需要加入 手机卡的价格
		if (null != contractPhoneInfo) {
			allPrice = allPrice.add(contractPhoneInfo.getPhoneNumber()
					.getPrice());
		}

		order.setPrice(allPrice);

		order.setSn(snDao.generate(Sn.Type.order));
		order.setType(type);
		order.setFee(BigDecimal.ZERO);
		order.setFreight(BigDecimal.ZERO);// TODO 现在快递默认为0
		order.setPromotionDiscount(cart != null ? cart.getDiscount()
				: BigDecimal.ZERO);
		order.setOffsetAmount(BigDecimal.ZERO);
		order.setAmountPaid(BigDecimal.ZERO);
		order.setRefundAmount(BigDecimal.ZERO);
		order.setRewardPoint(cart != null ? cart.getEffectiveRewardPoint()
				: product.getRewardPoint());
		order.setExchangePoint(cart != null ? cart.getExchangePoint() : product
				.getExchangePoint());
		if(product.getWeight()==null){
			order.setWeight(0);
		}else{
			order.setWeight(cart != null ? cart.getWeight() : product.getWeight());
		}
		order.setQuantity(cart != null ? cart.getQuantity() : i);
		order.setShippedQuantity(0);
		order.setReturnedQuantity(0);

        if(shippingMethod.getId() == 1){
        	order.setConsignee(receiver.getConsignee());
            order.setAreaName(receiver.getAreaName());
            order.setAddress(receiver.getAddress());
            order.setZipCode(receiver.getZipCode());
            order.setPhone(receiver.getPhone());
            order.setArea(receiver.getArea());
        }else{
        	order.setOrganization( organization );
            order.setCollectTime( collectTime );
        }
        

		order.setMemo(memo);

		order.setIsUseCouponCode(false);

        order.setInvoice(setting.getIsInvoiceEnabled() ? invoice : null);

		order.setIsExchangePoint(false);
		order.setIsAllocatedStock(false);
		order.setShippingMethod(shippingMethod);
		order.setMember(member);

		if (null != cart) {
			order.setCoupons(new ArrayList<Coupon>(cart.getCoupons()));
		}

		List<CouponCode> couponCodes = new ArrayList<CouponCode>();
		// 设置 使用 优惠劵 减少的价格
		if (null != couponCodeIds) {
			BigDecimal couponDiscount = new BigDecimal(0);
			;
			for (Long id : couponCodeIds) {
				CouponCode couponCode = couponCodeDao.find(id);

				if (!cart.isCouponAllowed() || !cart.isValid(couponCode)) {
					logger.error("service createOrder  param coupponCode is error");
					throw new IllegalArgumentException();
				}
				// 优惠劵 金额累加
				BigDecimal d = cart.getEffectivePrice().subtract(
						couponCode.getCoupon().calculatePrice(
								cart.getEffectivePrice(),
								cart.getProductQuantity()));
				couponDiscount = couponDiscount.add(d);

				couponCodes.add(couponCode);
			}

			order.setCouponDiscount(couponDiscount.compareTo(BigDecimal.ZERO) >= 0 ? couponDiscount
					: BigDecimal.ZERO);

		} else {
			order.setCouponDiscount(BigDecimal.ZERO);
		}

		order.setCouponCode(couponCodes);

		// 设置优惠码使用
		useCouponCode(order);

		order.setTax(BigDecimal.ZERO);// TODO 税金 目前为 0
		order.setAmount(calculateAmount(order));

		// 余额 是否充足
		if (godMoney != null
				&& (godMoney.compareTo(BigDecimal.ZERO) < 0
						|| godMoney.compareTo(member.getGodMoney()) > 0 || godMoney
						.compareTo(order.getAmount()) > 0)) {
			logger.error("service createOrder  param balance is error");
			throw new IllegalArgumentException();
		}

		//使用神币数量		
		if( godMoney != null  ){
			order.setGodMoneyCount( godMoney  );
			order.setGodMoneyPaid( godMoney.multiply(new BigDecimal(setting.getMoneyRechargeGodMoney()))  );
		}else{
			order.setGodMoneyCount( BigDecimal.ZERO  );
			order.setGodMoneyPaid( BigDecimal.ZERO );
		}
		
		// 将神币根据转换比例转换成人民币
		BigDecimal amountPayable = godMoney != null ? order.getAmount()
				.subtract(godMoney.multiply(new BigDecimal(setting.getMoneyRechargeGodMoney())))
                : order.getAmount();

        if (paymentMethod == null) {
            logger.error("service createOrder  param paymentMethod is null");
            throw new IllegalArgumentException();
        }

        order.setPaymentMethod( paymentMethod );

        if (amountPayable.compareTo(BigDecimal.ZERO) > 0) {

            // 订单状态 默认为 等待付款
            order.setStatus(Order.Status.pendingPayment);

            //在线支付 才存在订单有效时间
            if (shippingMethod.getId() == 1l && paymentMethod.getTimeout() != null
                    && Order.Status.pendingPayment.equals(order.getStatus())) {
                order.setExpire(DateUtils.addMinutes(new Date(),
                        paymentMethod.getTimeout()));
            }

            if (PaymentMethod.Method.online.equals(paymentMethod.getMethod())) {
                lock(order, member);
            }
        } else {
            // 神币 大于 需要支付的商品价值
            if( order.getType() == Type.reserve ){
                order.setStatus(Order.Status.pendingPayment);
            }else{
                order.setStatus(Order.Status.pendingShipment);

                if( !(shippingMethod.getId() == 1l) ){
                    //门店自提，改为待自提状态
                    order.setStatus(Order.Status.daiziti);
                }
            }
        }

        order.setPaymentMethod( paymentMethod );

		List<OrderItem> orderItems = order.getOrderItems();
		// 购物车中选中的商品
		OrderItem orderItem = null;

		orderItem = new OrderItem();
		orderItem.setSn(product.getSn());
		orderItem.setName(product.getName());
		orderItem.setType(product.getType());
		orderItem.setPrice(product.getPrice());
		orderItem.setWeight(product.getWeight());
		orderItem.setIsDelivery(product.getIsDelivery());
		orderItem.setThumbnail(product.getThumbnail());
		orderItem.setQuantity(i);
		orderItem.setShippedQuantity(0);
		orderItem.setReturnedQuantity(0);
		orderItem.setProduct(product);
		orderItem.setOrder(order);
		orderItem.setSpecifications(product.getSpecifications());

		// 商品为合约机 需要将 套餐 手机号等信息保存在订单中
		if (null != contractPhoneInfo) {
			orderItem.setPhoneNumInfo(contractPhoneInfo);
		}

		orderItems.add(orderItem);

		// 单个商品购买的时候 不存在绑定促销

		if (cart != null) {
			for (Product gift : cart.getGifts()) {
				orderItem = new OrderItem();
				orderItem.setSn(gift.getSn());
				orderItem.setName(gift.getName());
				orderItem.setType(gift.getType());
				orderItem.setPrice(BigDecimal.ZERO);
				orderItem.setWeight(gift.getWeight());
				orderItem.setIsDelivery(gift.getIsDelivery());
				orderItem.setThumbnail(gift.getThumbnail());
				orderItem.setQuantity(1);
				orderItem.setShippedQuantity(0);
				orderItem.setReturnedQuantity(0);
				orderItem.setProduct(gift);
				orderItem.setOrder(order);
				orderItem.setSpecifications(gift.getSpecifications());
				orderItems.add(orderItem);
			}
		}

		//设置为线上订单
		order.setIsOnline(true);
		orderDao.persist(order);

		// 订单日志
		OrderLog orderLog = new OrderLog();
		orderLog.setType(OrderLog.Type.create);
		orderLog.setOrder(order);
		orderLogDao.persist(orderLog);

		if(order.getType().equals(Order.Type.exchange)){
			exchangePoint(order);
		}
		//TODO  有问题
		if(!type.equals(Order.Type.reserve)){
			// 判断是否在下订单的时候锁定库存
			if (Setting.StockAllocationTime.order.equals(setting
					.getStockAllocationTime())
					|| (Setting.StockAllocationTime.payment.equals(setting
							.getStockAllocationTime()) && (order.getAmountPaid()
							.compareTo(BigDecimal.ZERO) > 0
							|| order.getExchangePoint() > 0 || order
							.getAmountPayable().compareTo(BigDecimal.ZERO) <= 0))) {
				allocateStock(order);
			}
		}else{
			product.setPreOrderCount(product.getPreOrderCount()-1);
			productService.update(product);
		}
		// 使用神币支付 生成一条支付记录
		if (godMoney != null && godMoney.compareTo(BigDecimal.ZERO) > 0) {
			Payment payment = new Payment();
			payment.setType(Payment.Type.payment);
			payment.setMethod(Payment.Method.deposit);
			payment.setFee(BigDecimal.ZERO);
			payment.setAmount(godMoney);
			payment.setOrder(order);
			payment(order, payment, null);
		}
		return order;
	}

	@Override
	public Map<String, Object> create(Type general, Cart cart, Product product,
			Integer quantity, Receiver receiver, PaymentMethod paymentMethod,
			ShippingMethod shippingMethod, Long[] couponCodeIds, Invoice invoice,
			BigDecimal godMoneyNum, String memo,
			ContractPhoneNumberUserInfo contractPhoneInfo,
			Organization organization, String collectTime, String recommended) {
		Map<String, Object> result=new HashMap<String, Object>();
		try {
			Setting setting = SystemUtils.getSetting();
			Member member = cart.getMember();

			Order order = new Order();

			order.setIsDelete(false);
			
			// 订单总金额
			BigDecimal allPrice =  cart != null ? cart.getPrice() : product
                    .getPrice();
			
			// 合约机待手机卡时 需要加入 手机卡的价格
			if (null != contractPhoneInfo) {
				allPrice = allPrice.add(contractPhoneInfo.getPhoneNumber()
						.getPrice());
			}
			
			order.setPrice(allPrice);

			order.setSn(snDao.generate(Sn.Type.order));
			order.setType(general);
			
			order.setFee(BigDecimal.ZERO);
			order.setFreight(BigDecimal.ZERO);// TODO 现在快递默认为0
			
			order.setPromotionDiscount(cart != null ? cart.getDiscount()
                    : BigDecimal.ZERO);//促销折扣
			
			order.setOffsetAmount(BigDecimal.ZERO);
			order.setAmountPaid(BigDecimal.ZERO);
			order.setRefundAmount(BigDecimal.ZERO);
			order.setRewardPoint(cart != null ? cart.getEffectiveRewardPoint()
					: product.getRewardPoint());
			order.setExchangePoint(cart != null ? cart.getExchangePoint() : product
					.getExchangePoint());
			if(product.getWeight()==null){
				order.setWeight(0);
			}else{
				order.setWeight(cart != null ? cart.getWeight() : product.getWeight());
			}
			order.setQuantity(cart != null ? cart.getQuantity() : quantity);
			order.setShippedQuantity(0);
			order.setReturnedQuantity(0);

	        order.setConsignee(receiver.getConsignee());
	        order.setAreaName(receiver.getAreaName());
	        order.setAddress(receiver.getAddress());
	        order.setZipCode(receiver.getZipCode());
	        order.setPhone(receiver.getPhone());
	        order.setArea(receiver.getArea());
	        order.setOrganization( organization );
	        order.setCollectTime( collectTime );

			order.setMemo(memo);

			order.setIsUseCouponCode(false);

	        order.setInvoice(setting.getIsInvoiceEnabled() ? invoice : null);

			order.setIsExchangePoint(false);
			order.setIsAllocatedStock(false);
			order.setShippingMethod(shippingMethod);
			order.setMember(member);
			
			if (null != cart) {
				order.setCoupons(new ArrayList<Coupon>(cart.getCoupons()));
			}

			List<CouponCode> couponCodes = new ArrayList<CouponCode>();
			// 设置 使用 优惠劵 减少的价格
			if (null != couponCodeIds) {
				BigDecimal couponDiscount = new BigDecimal(0);
				;
				for (Long id : couponCodeIds) {
					CouponCode couponCode = couponCodeDao.find(id);

					if (!cart.isCouponAllowed() || !cart.isValid(couponCode)) {
						logger.error("service createOrder  param coupponCode is error");
						throw new IllegalArgumentException();
					}
					// 优惠劵 金额累加
					BigDecimal d = cart.getEffectivePrice().subtract(
							couponCode.getCoupon().calculatePrice(
									cart.getEffectivePrice(),
									cart.getProductQuantity()));
					couponDiscount = couponDiscount.add(d);

					couponCodes.add(couponCode);
				}

				order.setCouponDiscount(couponDiscount.compareTo(BigDecimal.ZERO) >= 0 ? couponDiscount
						: BigDecimal.ZERO);

			} else {
				order.setCouponDiscount(BigDecimal.ZERO);
			}

			order.setCouponCode(couponCodes);
			
			// 设置优惠码使用
			useCouponCode(order);

			order.setTax(BigDecimal.ZERO);// TODO 税金 目前为 0
			order.setAmount(calculateAmount(order));

			// 余额 是否充足
			if (godMoneyNum != null
					&& (godMoneyNum.compareTo(BigDecimal.ZERO) < 0
							|| godMoneyNum.compareTo(member.getGodMoney()) > 0 || godMoneyNum
							.compareTo(order.getAmount()) > 0)) {
				logger.error("service createOrder  param balance is error");
				throw new IllegalArgumentException();
			}

			//使用神币数量		
			if( godMoneyNum != null  ){
				order.setGodMoneyCount( godMoneyNum  );
				order.setGodMoneyPaid( godMoneyNum.multiply(new BigDecimal(setting.getMoneyRechargeGodMoney()))  );
			}else{
				order.setGodMoneyCount( BigDecimal.ZERO  );
				order.setGodMoneyPaid( BigDecimal.ZERO );
			}
			
			// 将神币根据转换比例转换成人民币
			BigDecimal amountPayable = godMoneyNum != null ? order.getAmount()
					.subtract(godMoneyNum.multiply(new BigDecimal(setting.getMoneyRechargeGodMoney())))
	                : order.getAmount();

	        if (paymentMethod == null) {
	            logger.error("service createOrder  param paymentMethod is null");
	            throw new IllegalArgumentException();
	        }
	        
	        order.setPaymentMethod( paymentMethod );
			
	        if (amountPayable.compareTo(BigDecimal.ZERO) > 0) {

	            // 订单状态 默认为 等待付款
	            order.setStatus(Status.pendingPayment);

	            //在线支付 才存在订单有效时间
	            if (shippingMethod.getId() == 1l && paymentMethod.getTimeout() != null
	                    && Order.Status.pendingPayment.equals(order.getStatus())) {
	            	if( general == Order.Type.seckill ){
	        			order.setType(Order.Type.general );
	                    /*秒杀订单的失效时间为 15 分钟 */
	        			order.setExpire(DateUtils.addMinutes(new Date() , 15 ));
	        		}else{
	        			order.setExpire(DateUtils.addMinutes(new Date(),
	                            paymentMethod.getTimeout()));
	        		}
	            }

	            if (PaymentMethod.Method.online.equals(paymentMethod.getMethod())) {
	                lock(order, member);
	            }
	        } else {
	            // 神币 大于 需要支付的商品价值
	            if( order.getType() == Type.reserve ){
	                order.setStatus(Order.Status.pendingPayment);
	            }else{
	                order.setStatus(Order.Status.pendingShipment);

	                if( !(shippingMethod.getId() == 1l) ){
	                    //门店自提，改为待自提状态
	                    order.setStatus(Order.Status.daiziti);
	                }
	            }
	        }
	        
	        order.setPaymentMethod( paymentMethod );

			List<OrderItem> orderItems = order.getOrderItems();
			// 购物车中选中的商品
			OrderItem orderItem = null;

			orderItem = new OrderItem();
			orderItem.setSn(product.getSn());
			orderItem.setName(product.getName());
			orderItem.setType(product.getType());

	        orderItem.setPrice(   product.getPrice()  );
	        //退款金额为 商品金额 减去 单个商品的平均优化金额
	        BigDecimal returnDisCount = BigDecimal.ZERO;
	        if( null != order.getPromotionDiscount() ){
	        	returnDisCount = order.getPromotionDiscount().divide(new BigDecimal( quantity ) , 2, BigDecimal.ROUND_DOWN);
	        }
	        BigDecimal returnBackPrice = product.getPrice().subtract( returnDisCount );
	        returnBackPrice =  setting.setScale( returnBackPrice );

	        orderItem.setReturnPrice( returnBackPrice ); //订单项单个商品退款金额
	        
	        orderItem.setWeight(product.getWeight());
			orderItem.setIsDelivery(product.getIsDelivery());
			orderItem.setThumbnail(product.getThumbnail());
			orderItem.setQuantity(quantity);
			orderItem.setShippedQuantity(0);
			orderItem.setReturnedQuantity(0);
			orderItem.setProduct(product);
			orderItem.setOrder(order);
			orderItem.setSpecifications(product.getSpecifications());
	        
			// 商品为合约机 需要将 套餐 手机号等信息保存在订单中
			if (null != contractPhoneInfo) {
				orderItem.setPhoneNumInfo(contractPhoneInfo);
			}

			orderItems.add(orderItem);

			// 单个商品购买的时候 不存在绑定促销

			if (cart != null) {
				for (Product gift : cart.getGifts()) {
					orderItem = new OrderItem();
					orderItem.setSn(gift.getSn());
					orderItem.setName(gift.getName());
					orderItem.setType(gift.getType());
					orderItem.setPrice(BigDecimal.ZERO);
					orderItem.setWeight(gift.getWeight());
					orderItem.setIsDelivery(gift.getIsDelivery());
					orderItem.setThumbnail(gift.getThumbnail());
					orderItem.setQuantity(1);
					orderItem.setShippedQuantity(0);
					orderItem.setReturnedQuantity(0);
					orderItem.setProduct(gift);
					orderItem.setOrder(order);
					orderItem.setSpecifications(gift.getSpecifications());
					orderItems.add(orderItem);
				}
			}
	        
			//设置为线上订单
			order.setIsOnline(true);

	        //添加订单推荐人信息
	        Member recomMember = memberService.findByPhone( recommended );
	        if( null !=  recomMember ){
	            order.setRecommended(recomMember);
	        }

			orderDao.persist(order);

			// 订单日志
			OrderLog orderLog = new OrderLog();
			orderLog.setType(OrderLog.Type.create);
			orderLog.setOrder(order);
			orderLogDao.persist(orderLog);

			if(order.getType().equals(Order.Type.exchange)){
				exchangePoint(order);
			}

			if(!general.equals(Order.Type.reserve)){
				// 判断是否在下订单的时候锁定库存
				if (Setting.StockAllocationTime.order.equals(setting
						.getStockAllocationTime())
						|| (Setting.StockAllocationTime.payment.equals(setting
								.getStockAllocationTime()) && (order.getAmountPaid()
								.compareTo(BigDecimal.ZERO) > 0
								|| order.getExchangePoint() > 0 || order
								.getAmountPayable().compareTo(BigDecimal.ZERO) <= 0))) {
					allocateStock(order);
				}
			}else{
				product.setPreOrderCount(product.getPreOrderCount()-1);
				productService.update(product);
			}
			// 使用神币支付 生成一条支付记录
			if (godMoneyNum != null && godMoneyNum.compareTo(BigDecimal.ZERO) > 0) {
				Payment payment = new Payment();
				payment.setType(Payment.Type.payment);
				payment.setMethod(Payment.Method.deposit);
				payment.setFee(BigDecimal.ZERO);
				payment.setAmount(godMoneyNum);
				payment.setOrder(order);
				payment(order, payment, null);
			}
			result.put(UnivParameter.CODE, UnivParameter.DATA_CORRECTCODE);
			//加载逻辑处理层崩溃性MESSAGE
			result.put(UnivParameter.DATA, order);
		} catch (Exception e) {
			e.printStackTrace();
			//清空结果数据结构体-准备装载逻辑层错误信息
			result.clear();
			
			result.put(UnivParameter.CODE, UnivParameter.DATA_ERRORCODE);
			//加载逻辑处理层崩溃性MESSAGE
			result.put(UnivParameter.REASON, e.getMessage());
			
			//LOG日志文件编写
			LOGGER.error(e.getMessage(), e);
		}
		

		return result;
	}
}
