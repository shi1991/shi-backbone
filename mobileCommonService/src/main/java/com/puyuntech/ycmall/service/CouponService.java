package com.puyuntech.ycmall.service;

import java.util.List;
import java.util.Set;

import com.puyuntech.ycmall.Page;
import com.puyuntech.ycmall.Pageable;
import com.puyuntech.ycmall.entity.Coupon;
import com.puyuntech.ycmall.entity.CouponCode;
import com.puyuntech.ycmall.entity.Member;
import com.puyuntech.ycmall.entity.Order;


/**
 * 
 * Service - 优惠券. 
 * Created on 2015-10-10 上午10:54:16 
 * @author 严志森
 */
public interface CouponService extends BaseService<Coupon, Long> {

	/**
	 * 验证价格运算表达式
	 * 
	 * @param priceExpression
	 *            价格运算表达式
	 * @return 验证结果
	 */
	boolean isValidPriceExpression(String priceExpression);

	/**
	 * 查找优惠券分页
	 * 
	 * @param isEnabled
	 *            是否启用
	 * @param isExchange
	 *            是否允许积分兑换
	 * @param hasExpired
	 *            是否已过期
	 * @param pageable
	 *            分页信息
	 * @return 优惠券分页
	 */
	Page<Coupon> findPage(Boolean isEnabled, Boolean isExchange, Boolean hasExpired, Pageable pageable);

	/**
	 * 
	 * 获得可用优惠券.
	 * 
	 * author: 王凯斌
	 *   date: 2015-10-26 下午2:08:32
	 * @param order 订单
	 * @param member 会员
	 * @return 属于该会员下的优化码和优惠劵
	 * 	update 施长成
	 */
	Set<CouponCode> getAvailableCoupons(Order order,Member member);
	
	
	
	
}