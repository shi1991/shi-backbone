package com.puyuntech.ycmall.service.impl;

import groovy.lang.Binding;
import groovy.lang.GroovyShell;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.puyuntech.ycmall.Page;
import com.puyuntech.ycmall.Pageable;
import com.puyuntech.ycmall.dao.CouponDao;
import com.puyuntech.ycmall.entity.Coupon;
import com.puyuntech.ycmall.entity.CouponCode;
import com.puyuntech.ycmall.entity.Member;
import com.puyuntech.ycmall.entity.Order;
import com.puyuntech.ycmall.entity.OrderItem;
import com.puyuntech.ycmall.entity.Product;
import com.puyuntech.ycmall.service.CouponService;
import com.puyuntech.ycmall.vo.CouponVO;

/**
 * 
 * Service - 优惠券. Created on 2015-10-10 上午10:57:34
 * 
 * @author 严志森
 */
@Service("couponServiceImpl")
public class CouponServiceImpl extends BaseServiceImpl<Coupon, Long> implements
		CouponService {

	/** 价格表达式变量 */
	private static final List<Map<String, Object>> PRICE_EXPRESSION_VARIABLES = new ArrayList<Map<String, Object>>();

	@Resource(name = "couponDaoImpl")
	private CouponDao couponDao;

	static {
		Map<String, Object> variable0 = new HashMap<String, Object>();
		Map<String, Object> variable1 = new HashMap<String, Object>();
		Map<String, Object> variable2 = new HashMap<String, Object>();
		variable0.put("quantity", 99);
		variable0.put("price", new BigDecimal("99"));
		variable1.put("quantity", 99);
		variable1.put("price", new BigDecimal("9.9"));
		variable2.put("quantity", 99);
		variable2.put("price", new BigDecimal("0.99"));
		PRICE_EXPRESSION_VARIABLES.add(variable0);
		PRICE_EXPRESSION_VARIABLES.add(variable1);
		PRICE_EXPRESSION_VARIABLES.add(variable2);
	}

	@Transactional(readOnly = true)
	public boolean isValidPriceExpression(String priceExpression) {
		Assert.hasText(priceExpression);

		for (Map<String, Object> variable : PRICE_EXPRESSION_VARIABLES) {
			try {
				Binding binding = new Binding();
				for (Map.Entry<String, Object> entry : variable.entrySet()) {
					binding.setVariable(entry.getKey(), entry.getValue());
				}
				GroovyShell groovyShell = new GroovyShell(binding);
				Object result = groovyShell.evaluate(priceExpression);
				new BigDecimal(result.toString());
			} catch (Exception e) {
				return false;
			}
		}
		return true;
	}

	@Transactional(readOnly = true)
	public Page<Coupon> findPage(Boolean isEnabled, Boolean isExchange,
			Boolean hasExpired, Pageable pageable) {
		return couponDao.findPage(isEnabled, isExchange, hasExpired, pageable);
	}

	public Set<CouponCode> getAvailableCoupons(Order order, Member member) {
		
		Set<CouponCode> couponCodes = new HashSet<CouponCode>();
		List<CouponVO> tempList = new ArrayList< CouponVO >();
		CouponVO tempVO = null;
		//循环遍历会员拥有优惠码并进行有效性判断 , 并判断订单中是否存在该商品
		for (CouponCode couponcode : member.getCouponCodes()) {
			if( couponcode.getIsUsed()  ){
				continue;				
			}
			
			Coupon coupon = couponcode.getCoupon();

			if (!coupon.getIsEnabled()) {
				continue;
			}
			if (!coupon.hasBegun() || coupon.hasExpired()) {
				continue;
			}
			
			Set<Product> products = coupon.getProducts();
			for(Product product : products ){
				tempVO = new CouponVO();
				tempVO.setCouponCode(couponcode);
				tempVO.setMinPrice(coupon.getMinimumPrice());
				tempVO.setProductId(product.getId());
				tempList.add(tempVO);
			}
		}
		
		for(CouponVO couponVO :  tempList){
			for( OrderItem orderItem : order.getOrderItems() ){
				//满足 商品编号 相等，并且小计大于或等于优惠最小金额
				if(couponVO.getProductId() == orderItem.getProduct().getId() &&  !(couponVO.getMinPrice().compareTo( orderItem.getSubtotal() ) >0 ) ){
					couponCodes.add( couponVO.getCouponCode()  );
					break;
				}
			}
		}
		
		return couponCodes;
	}
}