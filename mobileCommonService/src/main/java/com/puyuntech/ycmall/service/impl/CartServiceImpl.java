package com.puyuntech.ycmall.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.puyuntech.ycmall.Setting;
import com.puyuntech.ycmall.dao.CartDao;
import com.puyuntech.ycmall.dao.CartItemDao;
import com.puyuntech.ycmall.entity.Cart;
import com.puyuntech.ycmall.entity.CartItem;
import com.puyuntech.ycmall.entity.CouponCode;
import com.puyuntech.ycmall.entity.Member;
import com.puyuntech.ycmall.entity.Order;
import com.puyuntech.ycmall.entity.Product;
import com.puyuntech.ycmall.entity.Receiver;
import com.puyuntech.ycmall.entity.value.CartItemBindProductValue;
import com.puyuntech.ycmall.service.CartItemService;
import com.puyuntech.ycmall.service.CartService;
import com.puyuntech.ycmall.service.CouponService;
import com.puyuntech.ycmall.service.MemberService;
import com.puyuntech.ycmall.service.OrderService;
import com.puyuntech.ycmall.service.PaymentMethodService;
import com.puyuntech.ycmall.service.ProductService;
import com.puyuntech.ycmall.service.ReceiverService;
import com.puyuntech.ycmall.service.ShippingMethodService;
import com.puyuntech.ycmall.util.SystemUtils;

/**
 * 
 * 购物车. 
 * Created on 2015-9-13 上午10:27:09 
 * @author 严志森
 */
@Service("cartServiceImpl")
public class CartServiceImpl extends BaseServiceImpl<Cart, Long> implements CartService {

	@Resource(name = "memberServiceImpl")
	private MemberService memberService;
	
	@Resource(name = "cartDaoImpl")
	private CartDao cartDao;
	
	@Resource(name = "cartItemDaoImpl")
	private CartItemDao cartItemDao;
	
	@Resource(name="cartItemServiceImpl")
	private CartItemService cartItemService;
	
	@Resource(name="productServiceImpl")
	private ProductService productService;
	
	@Resource(name="receiverServiceImpl")
	private ReceiverService receiverService;
	
	@Resource(name="orderServiceImpl")
	private OrderService orderService;
	
	SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	
	/**
	 * 优惠券 Service
	 */
	@Resource(name = "couponServiceImpl")
	private CouponService couponService;
	
	/**
	 * 支付方式 Service
	 */
	@Resource(name = "paymentMethodServiceImpl")
	private PaymentMethodService paymentMethodService;

	/**
	 * 购物方式 Service
	 */
	@Resource(name = "shippingMethodServiceImpl")
	private ShippingMethodService shippingMethodService;
	
	public Cart getCurrent(String userName) {
		Cart cart = null;
		Member member = memberService.findByUsername(userName);
		if (member != null) {
			cart = member.getCart();
		}
		return cart;
	}

	public Cart add(Product product, int quantity ,Long userId) {
		Assert.notNull(product);
		Assert.state(quantity > 0);
		Member member=memberService.find(userId);
		Cart cart = member.getCart();
		if (cart == null) {
			cart = new Cart();
			cart.setMember(member);
			cartDao.persist(cart);
		}
		if (cart.contains(product)) {
			CartItem cartItem = cart.getCartItem(product);
			cartItem.add(quantity);
		} else {
			CartItem cartItem = new CartItem();
			cartItem.setQuantity(quantity);
			cartItem.setProduct(product);
			cartItem.setCart(cart);
			cartItemDao.persist(cartItem);
			cart.getCartItems().add(cartItem);
		}
		return cart;
	}

//	public void merge(Member member, Cart cart) {
//		if (member == null || cart == null || cart.getMember() != null) {
//			return;
//		}
//		Cart memberCart = member.getCart();
//		if (memberCart != null) {
//			if (cart.getCartItems() != null) {
//				for (CartItem cartItem : cart.getCartItems()) {
//					Product product = cartItem.getProduct();
//					if (memberCart.contains(product)) {
//						CartItem memberCartItem = memberCart.getCartItem(product);
//						if (CartItem.MAX_QUANTITY != null && memberCartItem.getQuantity() + cartItem.getQuantity() > CartItem.MAX_QUANTITY) {
//							continue;
//						}
//						memberCartItem.add(cartItem.getQuantity());
//					} else {
//						if (Cart.MAX_CART_ITEM_COUNT != null && memberCart.getCartItems().size() >= Cart.MAX_CART_ITEM_COUNT) {
//							continue;
//						}
//						if (CartItem.MAX_QUANTITY != null && cartItem.getQuantity() > CartItem.MAX_QUANTITY) {
//							continue;
//						}
//						CartItem item = new CartItem();
//						item.setQuantity(cartItem.getQuantity());
//						item.setProduct(cartItem.getProduct());
//						item.setCart(memberCart);
//						cartItemDao.persist(item);
//						memberCart.getCartItems().add(cartItem);
//					}
//				}
//			}
//			cartDao.remove(cart);
//		} else {
//			cart.setMember(member);
//			member.setCart(cart);
//		}
//	}
//
//	public void evictExpired() {
//		while (true) {
//			List<Cart> carts = cartDao.findList(true, 100);
//			if (CollectionUtils.isNotEmpty(carts)) {
//				for (Cart cart : carts) {
//					cartDao.remove(cart);
//				}
//				cartDao.flush();
//				cartDao.clear();
//			}
//			if (carts.size() < 100) {
//				break;
//			}
//		}
//	}
	@Override
	public Cart add(Product product, Integer quantity , List<CartItemBindProductValue> bindProducts ,Long userId) {
		
		logger.info("CartServiceImpl add param is {"+product.getId() +","+quantity+"}");
		
		Member member = memberService.find(userId);
		
		Cart cart = getCurrent(memberService.find(userId).getUsername());
		/**
		 * 若购物不存在则新建一个购物车
		 */
		if(null == cart){
			cart = new Cart();
			if(member != null && null == member.getCart()){
				cart.setMember(member);
			}
			cartDao.persist(cart);
		}
		
		CartItem.type cartItemType = null;
		
		if(null !=bindProducts && bindProducts.size()>0){
			cartItemType = CartItem.type.mainproduct;
		}else{
			cartItemType = CartItem.type.subproduct;
		}
		
		if( cart.contains(product , cartItemType) ){
			
			CartItem cartItem = cart.getCartItem(product ,cartItemType);
			
			List<CartItemBindProductValue> tempProducts = cartItem.getBindProductIds() ;
			
			if( tempProducts.size() == 0){
				//普通商品
				cartItem.add(quantity);
				cartItem.setIsSelect(true);
				
			}else{
				//绑定商品 判断商品的和传入的内容是否一致
				boolean b = false;
				if( tempProducts.size() == bindProducts.size() ){
					Collections.sort(tempProducts, new Comparator<CartItemBindProductValue>( ) {
						@Override
						public int compare(CartItemBindProductValue o1,
								CartItemBindProductValue o2) {
							return o1.getId().compareTo(o2.getId()); 
						}
					});
					
					Collections.sort(bindProducts , new Comparator<CartItemBindProductValue>(){
						@Override
						public int compare(CartItemBindProductValue o1,
								CartItemBindProductValue o2) {
							return o1.getId().compareTo(o2.getId()); 
						}
					});
					
					for(int i=0;i<tempProducts.size() ; i++ ){
						if( !tempProducts.get(i).getId().equals( bindProducts.get(i).getId()  )){
							b = true ;
							break;
						}
					}
				}else{
					b =true;
				}
				
				//如果添加的商品一样，则更新下时间，不一样则添加一个购物车项
				if( b ){//不一样
					CartItem cartItem1 = new CartItem();
					cartItem1.setQuantity(quantity);
					cartItem1.setProduct(product);
					cartItem1.setCart(cart);
					cartItem1.setParentId(cartItemType);
					cartItem1.setBindProductIds( bindProducts );
					cartItem1.setIsSelect(true);
					cartItemDao.merge(cartItem1);
					cart.getCartItems().add(cartItem1);
				}else{
					cartItem.setQuantity( quantity+cartItem.getQuantity() );
					cartItemDao.merge(cartItem);
				}
			}
			
		}else{
			CartItem cartItem = new CartItem();
			cartItem.setQuantity(quantity);
			cartItem.setProduct(product);
			cartItem.setCart(cart);
			cartItem.setParentId(cartItemType);
			cartItem.setIsSelect(true);
			cartItem.setBindProductIds( bindProducts );
			cartItemDao.merge(cartItem);
			cart.getCartItems().add(cartItem);
		}
		return cart;
	}

	@Override
	public Map<String, Object> deleteCartItem(String userId, Long[] cartItemIds) {
		Map<String ,  Object > data = new HashMap<String, Object>();
		Member member = memberService.find( Long.parseLong(userId)  );
		
		/**
		 * cartItemIds 为空 或 null 则表示清空购物车
		 */
		Cart cart = member.getCart();

		List<CartItem> cartItems = null ; 
		if( null ==cartItemIds || cartItemIds.length < 1 ){
			Set<CartItem> temp = cart.getCartItems();
			cartItems = new ArrayList<CartItem>( temp );
			data.put("msg", "清空购物车项");
		}else{
			cartItems = cartItemService.findList( cartItemIds ) ;
			data.put("msg", "删除指定购物车项");
		}
		
		for(CartItem cartItem : cartItems){
			cartItemService.delete(cartItem);
			cart.getCartItems().remove(cartItem);
		}
		
		if( null !=cartItemIds && cartItemIds.length > 0 ){
			data.put("quantity", cart.getProductQuantity());// 总数
			data.put("effectiveRewardPoint", cart.getRewardPoint());// 赠送总积分
			data.put("effectivePrice", cart.getEffectivePrice());// 总价
			data.put("discountPrice", cart.getDiscount());// 折扣
		}
		
		return data;
	}

	@Override
	public Map<String, Object> editCartItem(String userId, Long id,
			Integer quantity) {
		//现在只对选择的商品做 数量校验 
		Map<String , Object> data = new HashMap<String , Object>();
		Member member = memberService.find(Long.parseLong(userId));
		Cart cart = this.getCurrent( member.getUsername() );
		
		if (null == cart || cart.isEmpty()) {
			data.put("success",  false );
			return data;
		}
		CartItem cartItem = cartItemService.find(id);
		
		if (!cart.contains(cartItem)) {
			data.put("success",  false );
			return data;
		}
		
		if (CartItem.MAX_QUANTITY != null && quantity > CartItem.MAX_QUANTITY) {
			data.put("success",  false );
			return data;
		}
		
		Product product = cartItem.getProduct();
		
		if (quantity > product.getAvailableStock()) {
			data.put("success",  false );
			return data;
		}
		
		cartItem.setQuantity(quantity);
		cartItem.setIsSelect(true);
		cartItemService.update(cartItem);
		data.put("success",  true );
		return data;
	}

	@Override
	public Map<String, Object> payOrder(String userId, Long[] ids) {
		Map<String , Object> data = new HashMap<String , Object>();
		
		Member member = memberService.find( Long.parseLong(userId) );
		if( null == member ){
			data.put("success", false);
			return data;
		}
		
		Cart cart = member.getCart();
		
		Map<Long , Integer > map = new HashMap<Long , Integer >();

		Set<CartItem> cartItems = cart.getCartItems();
		
		/**
		 * 修改购物车项是否被勾选
		 */
		for( CartItem cartItem : cartItems ){
			cartItem.setIsSelect(false);
			for( Long id : ids ){
				if( cartItem.getId().longValue() == id.longValue()  ){
					cartItem.setIsSelect(true);
					break;
				}
			}
		}
		
		Product product = null ;
		List<CartItemBindProductValue> cartItemBindProductValues = null;
		/**
		 * 循环统计 每种商品的 所需要的库存
		 */
		for(CartItem cartItem : cartItems ){
			
			if(  null !=cartItem && cartItem.getIsSelect()  ){
				product = cartItem.getProduct();
				
				if(map.containsKey( product.getId() )){
					map.put( product.getId() , cartItem.getQuantity()+map.get( product.getId() ) );	
				}else{
					map.put( product.getId() , cartItem.getQuantity() );	
				}
				/** 存在绑定商品，也需要统计绑定的商品 **/
				if( cartItem.getParentId().equals( CartItem.type.mainproduct ) ){
					cartItemBindProductValues = cartItem.getBindProductIds();
					for(CartItemBindProductValue bindProduct : cartItemBindProductValues){
						if(map.containsKey( bindProduct.getId() )){
							map.put( bindProduct.getId() , cartItem.getQuantity()+map.get( bindProduct.getId() ) );	
						}else{
							map.put( bindProduct.getId() , cartItem.getQuantity() );	
						}
					}
				}
			}
		}
		
		Long[] productIds = map.keySet().toArray( new Long[0] ); 
		
		List<Product> products = productService.findList(productIds);
		/**
		 *  循环判断库存是否充足
		 */
		List <String> productNames = new ArrayList<String>(); 
		for( Product product1 : products){
			if( map.get(product1.getId()) > product1.getAvailableStock() ){
				productNames.add(product1.getName() ) ;
			}
		}
		
		if(productNames.size() > 0){
			//存在商品库存不足
			data.put("success", false);
			data.put("message" , productNames );
			return data;
		}else{
			this.update(cart);
			data.put("success", true);
			//库存充足 ， 返回 订单需要的数据
			Map<String , Object> receiver = null;
			
			//默认收货人信息
			Receiver defaultReceiver = receiverService.findDefault(member);
			
			//创建一个订单
			Order order = orderService.generate(Order.Type.general, cart,defaultReceiver, null, null, null, null, null, null);
	
			if( null == defaultReceiver ){
				data.put("receiver", null);
			}else{
				receiver = new HashMap<String , Object>();
				receiver.put("consignee", defaultReceiver.getConsignee());
				receiver.put("id", defaultReceiver.getId());
				receiver.put("xiangxidizhi", defaultReceiver.getAreaName() + defaultReceiver.getAddress());
				receiver.put("phone", defaultReceiver.getPhone());
				data.put("receiver", receiver);
			}
			
			//优惠劵信息
			Set<CouponCode> couponCodes = couponService.getAvailableCoupons(order, member);
			List<Map<String , Object>> couponCodesDatas = new ArrayList<Map<String , Object>>();
			for(CouponCode couponCode:couponCodes){
				Map<String , Object> couponCodesItem = new HashMap<String , Object>();
				couponCodesItem.put("couponId", couponCode.getId());
				couponCodesItem.put("couponName", couponCode.getCoupon().getName());
				couponCodesItem.put("couponInfo", couponCode.getCoupon().getIntroduction());
				couponCodesItem.put("couponCountPrice", couponCode.getCoupon().getCountPrice());
				couponCodesItem.put("couponCode", couponCode.getCode());
				couponCodesItem.put("couponStartDate", format.format(couponCode.getCoupon().getBeginDate()));
				couponCodesItem.put("couponEndDate", format.format(couponCode.getCoupon().getEndDate()));				
				couponCodesDatas.add(couponCodesItem);
			}
			data.put("couponCodes", couponCodesDatas);
			//神币
			data.put("godMoney", member.getGodMoney());
			//人民币对神币的比例
			Setting setting = SystemUtils.getSetting();
			String godRate = setting.getMoneyRechargeGodMoney();
			data.put("goodRate",  godRate );
			data.put("cartToken", cart.getToken());
			
			//商品金额
			data.put("orderPrice",  cart.getPrice() );
			//应付金额
			data.put("orderamount",  order.getAmount() );
			//运费
			data.put( "freight", order.getFreight());
		}
		
		return data;
		
	}

	@Override
	public boolean getIsDelivery(Long id) {
		
		return cartDao.getIsDelivery(id);
	}

    public BigDecimal getEffectivePrice(Cart cart){
        Long cartId = cart.getId();
        Cart cart1=cartDao.find(cartId);
        return  cart1.getEffectivePrice();
    }

	@Override
	public Cart getPrice(Long userId, Long[] ids) {
		/***
		 * ids 为空，则表示所有商品都不选中
		 */
		if( null == ids || ids.length == 0 ){
//			data.put("quantity", 0 );// 总数
//			data.put("effectiveRewardPoint", 0  );// 赠送总积分
//			data.put("effectivePrice", 0 );// 总价
//			data.put("discountPrice", 0 );// 折扣
//			data.put("message", SUCCESS_MESSAGE);
//			return data;
		}
		
		Cart cart = this.getCurrent(memberService.find(userId).getUsername());
		
		Set<CartItem> cartItems = cart.getCartItems();
		
		if( null != cartItems ){
			for( CartItem cartItem : cartItems ){	
				cartItem.setIsSelect( false );
				for(Long id : ids ){
					if( id.longValue() == cartItem.getId().longValue() ){
						cartItem.setIsSelect(true);
						break;
					}
				}
			}
		}
		this.update(cart);
		return cart;
	}
}