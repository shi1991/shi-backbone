package com.puyuntech.ycmall.entity;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.AttributeConverter;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Converter;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.puyuntech.ycmall.BaseAttributeConverter;
import com.puyuntech.ycmall.Setting;
import com.puyuntech.ycmall.entity.value.CartItemBindProductValue;
import com.puyuntech.ycmall.util.SystemUtils;
import com.puyuntech.ycmall.vo.CartItemBindProductVO;

/**
 * 
 *  Entity - 购物车项 . 
 * Created on 2015-8-26 下午1:53:13 
 * @author 施长成
 */
@Entity
@Table(name = "t_cart_item")
public class CartItem extends BaseEntity<Long> {

	private static final long serialVersionUID = 7723575831051854260L;

	/** 最大数量 */
	public static final Integer MAX_QUANTITY = 10000;

	/** 数量 */
	private Integer quantity;

	/** 商品 */
	private Product product;
	
	/** 区分商品是否为 单品还是绑定商品  **/
	public enum type{
		/**
         * 存在绑定商品
         */
		mainproduct,
        /**
         * 单品
         */
		subproduct,
		/**
		 * 手机号
		 */
		phoneNum
	}
	
	public CartItem.type parentId;
	

	/** 该商品存的 绑定促销的商品  */
	private List<CartItemBindProductValue> bindProductIds = new ArrayList<CartItemBindProductValue>();
	
	/** 购物车 */
	private Cart cart;
	
	/** 商品是否选中 **/
	private Boolean isSelect;
	
	/** 购物车中商品的绑定商品 **/
	private List<CartItemBindProductVO> cartItemBindProductVOs = new ArrayList<CartItemBindProductVO>();
	

	/**
	 * 获取数量
	 * 
	 * @return 数量
	 */
	@Column(name="f_quantity",nullable = false)
	public Integer getQuantity() {
		return quantity;
	}

	/**
	 * 设置数量
	 * 
	 * @param quantity
	 *            数量
	 */
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	/**
	 * 获取商品
	 * 
	 * @return 商品
	 */
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false, updatable = false)
	public Product getProduct() {
		return product;
	}

	/**
	 * 设置商品
	 * @param product
	 *            商品
	 */
	public void setProduct(Product product) {
		this.product = product;
	}
	
	/**
	 * 获取购物车
	 * 
	 * @return 购物车
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="f_cart",nullable = false, updatable = false)
	public Cart getCart() {
		return cart;
	}

	/**
	 * 设置购物车
	 * 
	 * @param cart
	 *            购物车
	 */
	public void setCart(Cart cart) {
		this.cart = cart;
	}

	/**
	 * 获取商品重量
	 * 
	 * @return 商品重量
	 */
	@Transient
	public int getWeight() {
		if (getProduct() != null && getProduct().getWeight() != null && getQuantity() != null) {
			return getProduct().getWeight() * getQuantity();
		} else {
			return 0;
		}
	}

	/**
	 * 获取赠送积分
	 * 
	 * @return 赠送积分
	 */
	@Transient
	public long getRewardPoint() {
		if (getProduct() != null && getProduct().getRewardPoint() != null && getQuantity() != null) {
			return getProduct().getRewardPoint() * getQuantity();
		} else {
			return 0L;
		}
	}

	/**
	 * 获取兑换积分
	 * 
	 * @return 兑换积分
	 */
	@Transient
	public long getExchangePoint() {
		if (getProduct() != null && getProduct().getExchangePoint() != null && getQuantity() != null) {
			return getProduct().getExchangePoint() * getQuantity();
		} else {
			return 0L;
		}
	}

	/**
	 * 获取价格
	 * 
	 * @return 价格
	 */
	@Transient
	public BigDecimal getPrice() {
		 if (getProduct() != null && getProduct().getPrice() != null) {
			Setting setting = SystemUtils.getSetting();
			return setting.setScale(getProduct().getPrice());
		} else {
			return BigDecimal.ZERO;
		}
	}

	/**
	 * 获取小计
	 * 
	 * @return 小计
	 */
	@Transient
	public BigDecimal getSubtotal() {
		if (getQuantity() != null) {
			return getPrice().multiply(new BigDecimal(getQuantity()));
		} else {
			return BigDecimal.ZERO;
		}
	}

	/**
	 * 获取是否上架
	 * 
	 * @return 是否上架
	 */
	@Transient
	public boolean getIsMarketable() {
		return getProduct() != null && getProduct().getIsMarketable();
	}

	/**
	 * 获取是否需要物流
	 * 
	 * @return 是否需要物流
	 */
	@Transient
	public boolean getIsDelivery() {
		return getProduct() != null && getProduct().getIsDelivery();
	}

	/**
	 * 获取是否库存不足
	 * 
	 * @return 是否库存不足
	 */
	@Transient
	public boolean getIsLowStock() {
		return getQuantity() != null && getProduct() != null && getQuantity() > getProduct().getAvailableStock();
	}

	/**
	 * 增加商品数量
	 * 
	 * @param quantity
	 *            数量
	 */
	@Transient
	public void add(int quantity) {
		if (quantity < 1) {
			return;
		}
		if (getQuantity() != null) {
			setQuantity(getQuantity() + quantity);
		} else {
			setQuantity(quantity);
		}
	}
	
	/**
	 * 获取区分主副商品
	 * 
	 * @return 类型
	 */
	@Column(name="f_parent_id",nullable = true)
	public CartItem.type getParentId() {
		return parentId;
	}
	
	/**
	 * 设置数量
	 * 
	 * @param parentId
	 *            类型
	 */
	public void setParentId(CartItem.type parentId) {
		this.parentId = parentId;
	}
	
	/**
	 * 
	 * 一个购物车项，可能存在多个绑定促销商品  .
	 * <p>方法详细说明,如果要换行请使用<br>标签</p>
	 * <br>
	 * author: 施长成
	 *   date: 2015-10-22 下午2:01:45
	 * @return
	 */
	@Column(name="f_bind_product_items",length = 4000)
	@Convert(converter = BindProductItemConverter.class)
	public List<CartItemBindProductValue> getBindProductIds() {
		return bindProductIds;
	}

	public void setBindProductIds(List<CartItemBindProductValue> bindProductIds) {
		this.bindProductIds = bindProductIds;
	}

	@Column(name="f_is_select")
	public Boolean getIsSelect() {
		return isSelect;
	}

	public void setIsSelect(Boolean isSelect) {
		this.isSelect = isSelect;
	}
	
	/**
	 * 类型转换 - 绑定促销商品
	 */
	@Converter
	public static class BindProductItemConverter extends BaseAttributeConverter<List<CartItemBindProductValue>> implements AttributeConverter<Object, String> {
		
	}

	@Transient
	public List<CartItemBindProductVO> getCartItemBindProductVOs() {
		return cartItemBindProductVOs;
	}

	public void setCartItemBindProductVOs(
			List<CartItemBindProductVO> cartItemBindProductVOs) {
		this.cartItemBindProductVOs = cartItemBindProductVOs;
	}
	
	
	
}
