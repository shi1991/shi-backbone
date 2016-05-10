package com.puyuntech.ycmall.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.puyuntech.ycmall.entity.Cart;
import com.puyuntech.ycmall.entity.Product;
import com.puyuntech.ycmall.entity.value.CartItemBindProductValue;

/**
 * 
 * 购物车. 
 * Created on 2015-9-13 上午10:25:52 
 * @author 严志森
 */
public interface CartService extends BaseService<Cart, Long> {

	/**
	 * 获取当前购物车
	 * 
	 * @return 当前购物车，若不存在则返回null
	 */
	Cart getCurrent(String userName);

	/**
	 * 
	 * 将商品添加至当前购物车 .
	 * <p>方法详细说明,如果要换行请使用<br>标签</p>
	 * <br>
	 * author: 施长成
	 *   date: 2015-9-5 下午6:00:07
	 * @param product 商品
	 * @param quantity 商品数量
	 * @param bindProducts 绑定促销的商品
	 * @return
	 */
	public Cart add(Product product, Integer quantity, List<CartItemBindProductValue> bindProducts,Long userId);

	/**
	 * 
	 * 删除订单项 或 清空购物车  .
	 * <p>方法详细说明,如果要换行请使用<br>标签</p>
	 * <br>
	 * author: 施长成
	 *   date: 2015-12-2 下午1:21:05
	 * @param userId
	 * @param cartItemIds
	 * @return
	 */
	Map<String, Object> deleteCartItem(String userId, Long[] cartItemIds);

	/**
	 * 
	 * 编辑 购物车项 数量的添加 减少.
	 * <p>方法详细说明,如果要换行请使用<br>标签</p>
	 * <br>
	 * author: 施长成
	 *   date: 2015-12-2 下午4:51:30
	 * @param userId
	 * @param id
	 * @param quantity
	 * @return
	 */
	Map<String, Object> editCartItem(String userId, Long id, Integer quantity);

	/**
	 * 
	 * 购物车 跳转到订单 .
	 * <p>方法详细说明,如果要换行请使用<br>标签</p>
	 * <br>
	 * author: 施长成
	 *   date: 2015-12-3 上午9:35:22
	 * @param userId
	 * @param ids
	 * @return
	 */
	Map<String, Object> payOrder(String userId, Long[] ids);

	boolean getIsDelivery(Long id);

    BigDecimal getEffectivePrice(Cart cart);

	Cart getPrice(Long userId, Long[] ids);
	

}