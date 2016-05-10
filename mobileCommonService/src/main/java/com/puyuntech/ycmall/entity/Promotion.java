package com.puyuntech.ycmall.entity;

import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * 
 * Entity - 促销实体类 . 
 * Created on 2015-8-27 下午2:25:31 
 * @author 施长成
 */
@Entity
@Table(name = "t_promotion")
public class Promotion extends OrderEntity<Long> {

	private static final long serialVersionUID = 8959589277998050649L;

	/** 路径前缀 */
	private static final String PATH_PREFIX = "/promotion/content";

	/** 路径后缀 */
	private static final String PATH_SUFFIX = ".jhtml";

	/** 名称 */
	private String name;

	/** 标题 */
	private String title;

	/** 图片 */
	private String image;

	/** 起始日期 */
	private Date beginDate;

	/** 结束日期 */
	private Date endDate;

	/** 最小商品价格 */
	private BigDecimal minimumPrice;

	/** 最大商品价格 */
	private BigDecimal maximumPrice;

	/** 最小商品数量 */
	private Integer minimumQuantity;

	/** 最大商品数量 */
	private Integer maximumQuantity;

	/** 价格运算表达式 */
	private String priceExpression;

	/** 积分运算表达式 */
	private String pointExpression;

	/** 是否免运费 */
	private Boolean isFreeShipping;

	/** 是否允许使用优惠券 */
	private Boolean isCouponAllowed;

	/** 介绍 */
	private String introduction;

	/** 允许参加会员等级 */
	private Set<MemberRank> memberRanks = new HashSet<MemberRank>();

	/** 赠品 */
	private Set<Product> gifts = new HashSet<Product>();

	/** 商品 */
	private Set<Product> products = new HashSet<Product>();

	/** 商品分类 */
	private Set<ProductCategory> productCategories = new HashSet<ProductCategory>();

	/** 促销类型  0:无促销；1:单品促销【价格优惠】；2：满减 3：满赠 4.买增 5.组合 **/
    public enum Type{
        other,
        /**
         * 单品促销
         */
        productPromotion,
        /**
         * 满减
         */
        fullCutPromotion,
        /**
         * 满赠
         */
        fullGiftsPromotion,
        /**
         * 买赠
         */
        buyGiftsPromotion,
        /**
         * 组合
         */
        bindPromotion
    }
	private Promotion.Type promotionType;

    /** 绑定促销 **/
    private PromotionBind promotionBind;

	/**
	 * 获取名称
	 * 
	 * @return 名称
	 */
	@NotEmpty
	@Length(max = 200)
	@Column(name="f_name",nullable = false)
	public String getName() {
		return name;
	}

	/**
	 * 设置名称
	 * 
	 * @param name
	 *            名称
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 获取标题
	 * 
	 * @return 标题
	 */
	@NotEmpty
	@Length(max = 200)
	@Column(name="f_title",nullable = false)
	public String getTitle() {
		return title;
	}

	/**
	 * 设置标题
	 * 
	 * @param title
	 *            标题
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * 获取图片
	 * 
	 * @return 图片
	 */
	@Length(max = 200)
	@Pattern(regexp = "^(?i)(http:\\/\\/|https:\\/\\/|\\/).*$")
	@Column(name="f_image")
	public String getImage() {
		return image;
	}

	/**
	 * 设置图片
	 * 
	 * @param image
	 *            图片
	 */
	public void setImage(String image) {
		this.image = image;
	}

	/**
	 * 获取起始日期
	 * 
	 * @return 起始日期
	 */
	@Column(name="f_begin_date")
	public Date getBeginDate() {
		return beginDate;
	}

	/**
	 * 设置起始日期
	 * 
	 * @param beginDate
	 *            起始日期
	 */
	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	/**
	 * 获取结束日期
	 * 
	 * @return 结束日期
	 */
	@Column(name="f_end_date")
	public Date getEndDate() {
		return endDate;
	}

	/**
	 * 设置结束日期
	 * 
	 * @param endDate
	 *            结束日期
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	/**
	 * 获取最小商品价格
	 * 
	 * @return 最小商品价格
	 */
	@Min(0)
	@Digits(integer = 12, fraction = 3)
	@Column(name="f_minimum_price",precision = 21, scale = 6)
	public BigDecimal getMinimumPrice() {
		return minimumPrice;
	}

	/**
	 * 设置最小商品价格
	 * 
	 * @param minimumPrice
	 *            最小商品价格
	 */
	public void setMinimumPrice(BigDecimal minimumPrice) {
		this.minimumPrice = minimumPrice;
	}

	/**
	 * 获取最大商品价格
	 * 
	 * @return 最大商品价格
	 */
	@Min(0)
	@Digits(integer = 12, fraction = 3)
	@Column(name="f_maximum_price",precision = 21, scale = 6)
	public BigDecimal getMaximumPrice() {
		return maximumPrice;
	}

	/**
	 * 设置最大商品价格
	 * 
	 * @param maximumPrice
	 *            最大商品价格
	 */
	public void setMaximumPrice(BigDecimal maximumPrice) {
		this.maximumPrice = maximumPrice;
	}

	/**
	 * 获取最小商品数量
	 * 
	 * @return 最小商品数量
	 */
	@Min(0)
	@Column(name="f_minimum_quantity")
	public Integer getMinimumQuantity() {
		return minimumQuantity;
	}

	/**
	 * 设置最小商品数量
	 * 
	 * @param minimumQuantity
	 *            最小商品数量
	 */
	public void setMinimumQuantity(Integer minimumQuantity) {
		this.minimumQuantity = minimumQuantity;
	}

	/**
	 * 获取最大商品数量
	 * 
	 * @return 最大商品数量
	 */
	@Min(0)
	@Column(name="f_maximum_quantity")
	public Integer getMaximumQuantity() {
		return maximumQuantity;
	}

	/**
	 * 设置最大商品数量
	 * 
	 * @param maximumQuantity
	 *            最大商品数量
	 */
	public void setMaximumQuantity(Integer maximumQuantity) {
		this.maximumQuantity = maximumQuantity;
	}

	/**
	 * 获取价格运算表达式
	 * 
	 * @return 价格运算表达式
	 */
	@Column(name="f_price_expression")
	public String getPriceExpression() {
		return priceExpression;
	}

	/**
	 * 设置价格运算表达式
	 * 
	 * @param priceExpression
	 *            价格运算表达式
	 */
	public void setPriceExpression(String priceExpression) {
		this.priceExpression = priceExpression;
	}

	/**
	 * 获取积分运算表达式
	 * 
	 * @return 积分运算表达式
	 */
	@Column(name="f_point_expression")
	public String getPointExpression() {
		return pointExpression;
	}

	/**
	 * 设置积分运算表达式
	 * 
	 * @param pointExpression
	 *            积分运算表达式
	 */
	public void setPointExpression(String pointExpression) {
		this.pointExpression = pointExpression;
	}

	/**
	 * 获取是否免运费
	 * 
	 * @return 是否免运费
	 */
	@NotNull
	@Column(name="f_is_free_shipping",nullable = false)
	public Boolean getIsFreeShipping() {
		return isFreeShipping;
	}

	/**
	 * 设置是否免运费
	 * 
	 * @param isFreeShipping
	 *            是否免运费
	 */
	public void setIsFreeShipping(Boolean isFreeShipping) {
		this.isFreeShipping = isFreeShipping;
	}

	/**
	 * 获取是否允许使用优惠券
	 * 
	 * @return 是否允许使用优惠券
	 */
	@NotNull
	@Column(name="f_is_coupon_allowed",nullable = false)
	public Boolean getIsCouponAllowed() {
		return isCouponAllowed;
	}

	/**
	 * 设置是否允许使用优惠券
	 * 
	 * @param isCouponAllowed
	 *            是否允许使用优惠券
	 */
	public void setIsCouponAllowed(Boolean isCouponAllowed) {
		this.isCouponAllowed = isCouponAllowed;
	}

	/**
	 * 获取介绍
	 * 
	 * @return 介绍
	 */
	@Lob
	@Column(name="f_introduction")
	public String getIntroduction() {
		return introduction;
	}

	/**
	 * 设置介绍
	 * 
	 * @param introduction
	 *            介绍
	 */
	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	/**
	 * 获取允许参加会员等级
	 * 
	 * @return 允许参加会员等级
	 */
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "t_promotion_member_rank", joinColumns = { @JoinColumn(name = "f_promotions", referencedColumnName = "f_id") }, inverseJoinColumns = { @JoinColumn(name = "f_member_ranks", referencedColumnName = "f_id") })
	public Set<MemberRank> getMemberRanks() {
		return memberRanks;
	}

	/**
	 * 设置允许参加会员等级
	 * 
	 * @param memberRanks
	 *            允许参加会员等级
	 */
	public void setMemberRanks(Set<MemberRank> memberRanks) {
		this.memberRanks = memberRanks;
	}

	/**
	 * 获取赠品
	 * 
	 * @return 赠品
	 */
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "t_promotion_gift", joinColumns = { @JoinColumn(name = "f_promotions", referencedColumnName = "f_id") }, inverseJoinColumns = { @JoinColumn(name = "f_gifts", referencedColumnName = "f_id") })
	public Set<Product> getGifts() {
		return gifts;
	}

	/**
	 * 设置赠品
	 * 
	 * @param gifts
	 *            赠品
	 */
	public void setGifts(Set<Product> gifts) {
		this.gifts = gifts;
	}

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	@JoinTable(name = "t_product_promotion", joinColumns = { @JoinColumn(name = "f_promotions", referencedColumnName = "f_id") }, inverseJoinColumns = { @JoinColumn(name = "f_product", referencedColumnName = "f_id") })
	public Set<Product> getProducts() {
		return products;
	}

	public void setProducts(Set<Product> products) {
		this.products = products;
	}


	/**
	 * 获取商品分类
	 * 
	 * @return 商品分类
	 */
	@ManyToMany(mappedBy = "promotions", fetch = FetchType.LAZY)
	@OrderBy("order asc")
	public Set<ProductCategory> getProductCategories() {
		return productCategories;
	}

	/**
	 * 设置商品分类
	 * 
	 * @param productCategories
	 *            商品分类
	 */
	public void setProductCategories(Set<ProductCategory> productCategories) {
		this.productCategories = productCategories;
	}

	@Column(name="f_promotion_type")
	public Promotion.Type getPromotionType() {
		return promotionType;
	}

	public void setPromotionType(Promotion.Type promotionType) {
		this.promotionType = promotionType;
	}

    /**
     * 绑定促销
     *
     * @return
     */
    @OneToOne(mappedBy = "promotion" , targetEntity = PromotionBind.class)
    public PromotionBind getPromotionBind() {
        return promotionBind;
    }

    public void setPromotionBind(PromotionBind promotionBind) {
        this.promotionBind = promotionBind;
    }

    /**
	 * 获取路径
	 * 
	 * @return 路径
	 */
	@Transient
	public String getPath() {
		Promotion.Type type =  getPromotionType(); 
		if(type == Promotion.Type.fullGiftsPromotion || type == Promotion.Type.buyGiftsPromotion ){
			return getId() != null ? "goods/content/" + getId() + PATH_SUFFIX : null;
		}else{
			return getId() != null ? PATH_PREFIX + "/" + getId() + PATH_SUFFIX : null;
		}
	}

	/**
	 * 判断是否已开始
	 * 
	 * @return 是否已开始
	 */
	@Transient
	public boolean hasBegun() {
		return getBeginDate() == null || !getBeginDate().after(new Date());
	}

	/**
	 * 判断是否已结束
	 * 
	 * @return 是否已结束
	 */
	@Transient
	public boolean hasEnded() {
		return getEndDate() != null && !getEndDate().after(new Date());
	}

	/**
	 * 计算促销价格
	 * 
	 * @param price
	 *            商品价格
	 * @param quantity
	 *            商品数量
	 * @return 促销价格
	 */
	@Transient
	public BigDecimal calculatePrice(BigDecimal price, Integer quantity) {
		if (price == null || quantity == null || StringUtils.isEmpty(getPriceExpression())) {
			return price;
		}
		BigDecimal result = BigDecimal.ZERO;
		try {
			Binding binding = new Binding();
			binding.setVariable("quantity", quantity);
			binding.setVariable("price", price);
			GroovyShell groovyShell = new GroovyShell(binding);
			result = new BigDecimal(groovyShell.evaluate(getPriceExpression()).toString());
		} catch (Exception e) {
			return price;
		}
		if (result.compareTo(price) > 0) {
			return price;
		}
		return result.compareTo(BigDecimal.ZERO) > 0 ? result : BigDecimal.ZERO;
	}

	/**
	 * 计算促销赠送积分
	 * 
	 * @param point
	 *            赠送积分
	 * @param quantity
	 *            商品数量
	 * @return 促销赠送积分
	 */
	@Transient
	public Long calculatePoint(Long point, Integer quantity) {
		if (point == null || quantity == null || StringUtils.isEmpty(getPointExpression())) {
			return point;
		}
		Long result = 0L;
		try {
			Binding binding = new Binding();
			binding.setVariable("quantity", quantity);
			binding.setVariable("point", point);
			GroovyShell groovyShell = new GroovyShell(binding);
			result = Long.valueOf(groovyShell.evaluate(getPointExpression()).toString());
		} catch (Exception e) {
			return point;
		}
		if (result < point) {
			return point;
		}
		return result > 0L ? result : 0L;
	}

	/**
	 * 删除前处理
	 */
	@PreRemove
	public void preRemove() {
		Set<Product> goodsList = getProducts();
		if (goodsList != null) {
			for (Product product : goodsList) {
				product.getPromotions().remove(this);
			}
		}
		Set<ProductCategory> productCategories = getProductCategories();
		if (productCategories != null) {
			for (ProductCategory productCategory : productCategories) {
				productCategory.getPromotions().remove(this);
			}
		}
	}

}
