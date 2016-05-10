package com.puyuntech.ycmall.entity;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
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
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.groups.Default;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.CollectionUtils;
import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.DateBridge;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.FieldBridge;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.NumericField;
import org.hibernate.search.annotations.Resolution;
import org.hibernate.search.annotations.Store;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.puyuntech.ycmall.BaseAttributeConverter;
import com.puyuntech.ycmall.BigDecimalNumericFieldBridge;
import com.puyuntech.ycmall.Setting;
import com.puyuntech.ycmall.entity.value.ParameterValue;
import com.puyuntech.ycmall.entity.value.ProductImage;
import com.puyuntech.ycmall.entity.value.SpecificationItem;
import com.puyuntech.ycmall.entity.value.SpecificationValue;
import com.puyuntech.ycmall.util.SystemUtils;

/**
 * 
 * Entity - 商品 . Created on 2015-8-27 下午3:25:10
 * 
 * @author 施长成
 * 
 *         商品和订单是一对多的关系 商品和购物车是 一对多的关系
 */
@Indexed
@Entity
@Table(name = "t_product")
public class Product extends BaseEntity<Long> {

	private static final long serialVersionUID = 4979454533832727241L;

	/** 属性值属性个数 */
	public static final int ATTRIBUTE_VALUE_PROPERTY_COUNT = 20;

	/** 属性值属性名称前缀 */
	public static final String ATTRIBUTE_VALUE_PROPERTY_NAME_PREFIX = "attributeValue";

	/**
	 * 类型 0普通商品 1兑换商品 2赠品
	 */
	public enum Type {

		/** 兑换商品 */
		exchange,

		/** 普通商品 */
		general,

		/** 赠品 */
		gift ,
		
		/** 手机号 **/
		phoneNum
	}

	/**
	 * 静态生成方式
	 */
	public enum GenerateMethod {

		/** 无 */
		none,

		/** 即时 */
		eager,

		/** 延时 */
		lazy
	}

	/**
	 * 排序类型
	 */
	public enum OrderType {

		/** 置顶降序 */
		topDesc,

		/** 价格升序 */
		priceAsc,

		/** 价格降序 */
		priceDesc,

		/** 销量降序 */
		salesDesc,

		/** 评分降序 */
		scoreDesc,

		/** 日期降序 */
		dateDesc
	}

	/**
	 * 普通商品验证组
	 */
	public interface General extends Default {

	}

	/**
	 * 兑换商品验证组
	 */
	public interface Exchange extends Default {

	}

	/**
	 * 赠品验证组
	 */
	public interface Gift extends Default {

	}

	/** 编号 */
	private String sn;

	/** 名称 */
	private String name;

	/** 副标题 */
	private String caption;

	/** 类型 */
	private Product.Type type;

	/** 销售价 */
	private BigDecimal price;

	/** 成本价 */
	private BigDecimal cost;

	/** 市场价 */
	private BigDecimal marketPrice;

	/** 展示图片 */
	private String image;

	/** 单位 */
	private String unit;

	/** 重量 */
	private Integer weight;

	/** 是否上架 */
	private Boolean isMarketable;

	/** 是否列出 */
	private Boolean isList;

	/** 是否置顶 */
	private Boolean isTop;

	/** 是否需要物流 */
	private Boolean isDelivery;

	/** 介绍 */
	private String introduction;

	/** 售后 */
	private String saleSupport;

	/** 备注 */
	private String memo;

	/** 搜索关键词 */
	private String keyword;

	/** 页面标题 */
	private String seoTitle;

	/** 页面关键词 */
	private String seoKeywords;

	/** 页面描述 */
	private String seoDescription;

	/** 静态生成方式 */
	private Product.GenerateMethod generateMethod;

	/** 属性值0 */
	private String attributeValue0;

	/** 属性值1 */
	private String attributeValue1;

	/** 属性值2 */
	private String attributeValue2;

	/** 属性值3 */
	private String attributeValue3;

	/** 属性值4 */
	private String attributeValue4;

	/** 属性值5 */
	private String attributeValue5;

	/** 属性值6 */
	private String attributeValue6;

	/** 属性值7 */
	private String attributeValue7;

	/** 属性值8 */
	private String attributeValue8;

	/** 属性值9 */
	private String attributeValue9;

	/** 属性值10 */
	private String attributeValue10;

	/** 属性值11 */
	private String attributeValue11;

	/** 属性值12 */
	private String attributeValue12;

	/** 属性值13 */
	private String attributeValue13;

	/** 属性值14 */
	private String attributeValue14;

	/** 属性值15 */
	private String attributeValue15;

	/** 属性值16 */
	private String attributeValue16;

	/** 属性值17 */
	private String attributeValue17;

	/** 属性值18 */
	private String attributeValue18;

	/** 属性值19 */
	private String attributeValue19;

	/** 商品分类 */
	private ProductCategory productCategory;

	/** 品牌 */
	private Brand brand;

	/** 型号 */
	private ProductModel productModel;

	/** 商品图片 */
	private List<ProductImage> productImages = new ArrayList<ProductImage>();

	/** 参数值 */
	private List<ParameterValue> parameterValues = new ArrayList<ParameterValue>();

	/** 规格项 通过【SpecificationItem】 转换成 key value的json数据保存到数据库 */
	private List<SpecificationItem> specificationItems = new ArrayList<SpecificationItem>();

	/** 促销 */
	private Set<Promotion> promotions = new HashSet<Promotion>();

	/** 评论 */
	private Set<Review> reviews = new HashSet<Review>();

	/** 收藏会员 */
	private Set<Member> favoriteMembers = new HashSet<Member>();

	/** 赠送积分 */
	private Long rewardPoint;

	/** 兑换积分 */
	private Long exchangePoint;

	/** 库存 */
	private Integer stock;

	/** 已分配库存 */
	private Integer allocatedStock;

	/** 是否默认 */
	private Boolean isDefault;

	/** 规格值 */
	private List<SpecificationValue> specificationValues = new ArrayList<SpecificationValue>();

	/** 购物车项 */
	private Set<CartItem> cartItems = new HashSet<CartItem>();

	/** 订单项 */
	private Set<OrderItem> orderItems = new HashSet<OrderItem>();

	/** 发货项 */
	private Set<ShippingItem> shippingItems = new HashSet<ShippingItem>();

	/** 库存记录 */
	private Set<StockLog> stockLogs = new HashSet<StockLog>();

	/** 赠品促销 */
	private Set<Promotion> giftPromotions = new HashSet<Promotion>();

	/** 预采购商品信息 **/
	private Set<PurchaseRequisition> purchaseRequisitions = new HashSet<PurchaseRequisition>();

	/** 采购单信息 **/
	private Set<Purchase> purchases = new HashSet<Purchase>();
	
	/** 采购验收 **/
	private Set<PurchaseCheck> purchaseChecks = new HashSet<PurchaseCheck>();
	
	/** 标签 */
	private Set<Tag> tags = new HashSet<Tag>();

	/** 运营商 不 为1时是合约机*/
	private Operator operator;

	/** 是否预定商品 */
	private Boolean isPreOrder;

	/** 预售价格 */
	private BigDecimal preOrderPrice;

	/** 发售日期 */
	private Date preOrderTime;

	/** 预售信息 */
	private String preOrderDesc;

	/** 预售最大数量 */
	private Integer preOrderMaxCount;

	/** 预售当前数量 */
	private Integer preOrderCount;

	/** 优惠券 */
	private Set<Coupon> coupons = new HashSet<Coupon>();
	
	/** 门店商品关系 */
	private Set<OrganizationProducts> organizationProducts=new HashSet<OrganizationProducts>();
	
	/** 退货项 */
	private Set<ReturnOrderItem> returnOrderItems = new HashSet<ReturnOrderItem>();

	/** 二维码 **/
	private String qRcodeImg;
	
	/**
	 * 获取编号
	 * 
	 * @return 编号
	 */
	@Field(store = Store.YES, index = Index.YES, analyze = Analyze.NO)
	@Pattern(regexp = "^[0-9a-zA-Z_-]+$")
	@Length(max = 100)
	@Column(name = "f_sn", nullable = false, updatable = false, unique = true)
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
	 * 获取销售价
	 * 
	 * @return 销售价
	 */
	@Field(store = Store.YES, index = Index.YES, analyze = Analyze.NO)
	@NumericField
	@FieldBridge(impl = BigDecimalNumericFieldBridge.class)
	@NotNull(groups = General.class)
	@Min(0)
	@Digits(integer = 12, fraction = 3)
	@Column(name = "f_price", nullable = false, precision = 21, scale = 6)
	public BigDecimal getPrice() {
		return price;
	}

	/**
	 * 设置销售价
	 * 
	 * @param price
	 *            销售价
	 */
	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	/**
	 * 获取成本价
	 * 
	 * @return 成本价
	 */
	@Min(0)
	@Digits(integer = 12, fraction = 3)
	@Column(name = "f_cost", precision = 21, scale = 6)
	public BigDecimal getCost() {
		return cost;
	}

	/**
	 * 设置成本价
	 * 
	 * @param cost
	 *            成本价
	 */
	public void setCost(BigDecimal cost) {
		this.cost = cost;
	}

	/**
	 * 获取市场价
	 * 
	 * @return 市场价
	 */
	@Field(store = Store.YES, index = Index.NO, analyze = Analyze.NO)
	@NumericField
	@FieldBridge(impl = BigDecimalNumericFieldBridge.class)
	@Min(0)
	@Digits(integer = 12, fraction = 3)
	@Column(name = "f_market_price", nullable = false, precision = 21, scale = 6)
	public BigDecimal getMarketPrice() {
		return marketPrice;
	}

	/**
	 * 设置市场价
	 * 
	 * @param marketPrice
	 *            市场价
	 */
	public void setMarketPrice(BigDecimal marketPrice) {
		this.marketPrice = marketPrice;
	}

	/**
	 * 获取赠送积分
	 * 
	 * @return 赠送积分
	 */
	@Min(0)
	@Column(name = "f_reward_point", nullable = false)
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
	@NotNull(groups = Exchange.class)
	@Min(0)
	@Column(name = "f_exchange_point", nullable = false)
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
	 * 获取库存
	 * 
	 * @return 库存
	 */
	@NotNull(groups = Save.class)
	@Min(0)
	@Column(name = "f_stock", nullable = false)
	public Integer getStock() {
		return stock;
	}

	/**
	 * 设置库存
	 * 
	 * @param stock
	 *            库存
	 */
	public void setStock(Integer stock) {
		this.stock = stock;
	}

	/**
	 * 获取已分配库存
	 * 
	 * @return 已分配库存
	 */
	@Column(name = "f_allocated_stock", nullable = false)
	public Integer getAllocatedStock() {
		return allocatedStock;
	}

	/**
	 * 设置已分配库存
	 * 
	 * @param allocatedStock
	 *            已分配库存
	 */
	public void setAllocatedStock(Integer allocatedStock) {
		this.allocatedStock = allocatedStock;
	}

	/**
	 * 获取型号
	 * 
	 * @return 型号
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "f_product_model")
	public ProductModel getProductModel() {
		return productModel;
	}

	/**
	 * 设置型号
	 * 
	 * @param model
	 *            型号
	 */
	public void setProductModel(ProductModel productModel) {
		this.productModel = productModel;
	}

	/**
	 * 获取规格值
	 * 
	 * @return 规格值
	 */
	@Valid
	@Column(name = "f_specification_values", length = 4000)
	@Convert(converter = SpecificationValueConverter.class)
	public List<SpecificationValue> getSpecificationValues() {
		return specificationValues;
	}

	/**
	 * 设置规格值
	 * 
	 * @param specificationValues
	 *            规格值
	 */
	public void setSpecificationValues(
			List<SpecificationValue> specificationValues) {
		this.specificationValues = specificationValues;
	}

	/**
	 * 获取购物车项
	 * 
	 * @return 购物车项
	 */
	@OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	public Set<CartItem> getCartItems() {
		return cartItems;
	}

	/**
	 * 设置购物车项
	 * 
	 * @param cartItems
	 *            购物车项
	 */
	public void setCartItems(Set<CartItem> cartItems) {
		this.cartItems = cartItems;
	}

	/**
	 * 获取订单项
	 * 
	 * @return 订单项
	 */
	@OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
	public Set<OrderItem> getOrderItems() {
		return orderItems;
	}

	/**
	 * 设置订单项
	 * 
	 * @param orderItems
	 *            订单项
	 */
	public void setOrderItems(Set<OrderItem> orderItems) {
		this.orderItems = orderItems;
	}

	@OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
	public Set<ReturnOrderItem> getReturnOrderItems() {
		return returnOrderItems;
	}

	public void setReturnOrderItems(Set<ReturnOrderItem> returnOrderItems) {
		this.returnOrderItems = returnOrderItems;
	}

	/**
	 * 获取发货项
	 * 
	 * @return 发货项
	 */
	@OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
	public Set<ShippingItem> getShippingItems() {
		return shippingItems;
	}

	/**
	 * 设置发货项
	 * 
	 * @param shippingItems
	 *            发货项
	 */
	public void setShippingItems(Set<ShippingItem> shippingItems) {
		this.shippingItems = shippingItems;
	}

	/**
	 * 获取库存记录
	 * 
	 * @return 库存记录
	 */
	@OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	public Set<StockLog> getStockLogs() {
		return stockLogs;
	}

	/**
	 * 设置库存记录
	 * 
	 * @param stockLogs
	 *            库存记录
	 */
	public void setStockLogs(Set<StockLog> stockLogs) {
		this.stockLogs = stockLogs;
	}

	/**
	 * 获取入库验收
	 * 
	 * @return 入库验收
	 */
	@OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	public Set<PurchaseCheck> getPurchaseChecks() {
		return purchaseChecks;
	}

	public void setPurchaseChecks(Set<PurchaseCheck> purchaseChecks) {
		this.purchaseChecks = purchaseChecks;
	}

	/**
	 * 获取赠品促销
	 * 
	 * @return 赠品促销
	 */
	@ManyToMany(mappedBy = "gifts", fetch = FetchType.LAZY)
	public Set<Promotion> getGiftPromotions() {
		return giftPromotions;
	}

	/**
	 * 设置赠品促销
	 * 
	 * @param giftPromotions
	 *            赠品促销
	 */
	public void setGiftPromotions(Set<Promotion> giftPromotions) {
		this.giftPromotions = giftPromotions;
	}

	/**
	 * 
	 * 设置预采购单信息 author: 施长成 date: 2015-8-28 上午11:17:35
	 * 
	 * @return
	 */
	@OneToMany(mappedBy = "product", cascade = CascadeType.REMOVE)
	public Set<PurchaseRequisition> getPurchaseRequisitions() {
		return purchaseRequisitions;
	}

	public void setPurchaseRequisitions(
			Set<PurchaseRequisition> purchaseRequisitions) {
		this.purchaseRequisitions = purchaseRequisitions;
	}

	/**
	 * 
	 * 设置采购单信息 author: 施长成 date: 2015-8-28 上午11:17:35
	 * 
	 * @return
	 */
	@OneToMany(mappedBy = "product", cascade = CascadeType.REMOVE)
	public Set<Purchase> getPurchases() {
		return purchases;
	}

	public void setPurchases(
			Set<Purchase> purchases) {
		this.purchases = purchases;
	}
	
	/**
	 * 获取静态生成方式
	 * 
	 * @return 静态生成方式
	 */
	@Column(name = "f_generate_method", nullable = false)
	public Product.GenerateMethod getGenerateMethod() {
		return generateMethod;
	}

	/**
	 * 设置静态生成方式
	 * 
	 * @param generateMethod
	 *            静态生成方式
	 */
	public void setGenerateMethod(Product.GenerateMethod generateMethod) {
		this.generateMethod = generateMethod;
	}

	/**
	 * 获取名称
	 * 
	 * @return 名称
	 */
	@Field(store = Store.YES, index = Index.YES, analyze = Analyze.YES)
	@NotEmpty
	@Length(max = 200)
	@Column(name = "f_name", nullable = false)
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
	 * 获取副标题
	 * 
	 * @return 副标题
	 */
	@Field(store = Store.YES, index = Index.YES, analyze = Analyze.YES)
	@Length(max = 200)
	@Column(name = "f_caption")
	public String getCaption() {
		return caption;
	}

	/**
	 * 设置副标题
	 * 
	 * @param caption
	 *            副标题
	 */
	public void setCaption(String caption) {
		this.caption = caption;
	}

	/**
	 * 获取类型
	 * 
	 * @return 类型
	 */
	@Field(store = Store.YES, index = Index.YES, analyze = Analyze.NO)
	@NotNull(groups = Save.class)
	@Column(name = "type", nullable = false, updatable = false)
	public Product.Type getType() {
		return type;
	}

	/**
	 * 设置类型
	 * 
	 * @param type
	 *            类型
	 */
	public void setType(Product.Type type) {
		this.type = type;
	}

	/**
	 * 获取展示图片
	 * 
	 * @return 展示图片
	 */
	@Field(store = Store.YES, index = Index.NO, analyze = Analyze.NO)
	@Length(max = 200)
	@Pattern(regexp = "^(?i)(http:\\/\\/|https:\\/\\/|\\/).*$")
	@Column(name = "f_image")
	public String getImage() {
		return image;
	}

	/**
	 * 设置展示图片
	 * 
	 * @param image
	 *            展示图片
	 */
	public void setImage(String image) {
		this.image = image;
	}

	/**
	 * 获取单位
	 * 
	 * @return 单位
	 */
	@Field(store = Store.YES, index = Index.NO, analyze = Analyze.NO)
	@Length(max = 200)
	@Column(name = "f_unit")
	public String getUnit() {
		return unit;
	}

	/**
	 * 设置单位
	 * 
	 * @param unit
	 *            单位
	 */
	public void setUnit(String unit) {
		this.unit = unit;
	}

	/**
	 * 获取重量
	 * 
	 * @return 重量
	 */
	@Field(store = Store.YES, index = Index.NO, analyze = Analyze.NO)
	@NumericField
	@Min(0)
	@Column(name = "f_weight")
	public Integer getWeight() {
		return weight;
	}

	/**
	 * 设置重量
	 * 
	 * @param weight
	 *            重量
	 */
	public void setWeight(Integer weight) {
		this.weight = weight;
	}

	/**
	 * 获取是否上架
	 * 
	 * @return 是否上架
	 */
	@Field(store = Store.YES, index = Index.YES, analyze = Analyze.NO)
	@NotNull
	@Column(name = "f_is_marketable", nullable = false)
	public Boolean getIsMarketable() {
		return isMarketable;
	}

	/**
	 * 设置是否上架
	 * 
	 * @param isMarketable
	 *            是否上架
	 */
	public void setIsMarketable(Boolean isMarketable) {
		this.isMarketable = isMarketable;
	}

	/**
	 * 获取是否列出
	 * 
	 * @return 是否列出
	 */
	@Field(store = Store.YES, index = Index.YES, analyze = Analyze.NO)
	@NotNull
	@Column(name = "f_is_list", nullable = false)
	public Boolean getIsList() {
		return isList;
	}

	/**
	 * 设置是否列出
	 * 
	 * @param isList
	 *            是否列出
	 */
	public void setIsList(Boolean isList) {
		this.isList = isList;
	}

	/**
	 * 获取是否置顶
	 * 
	 * @return 是否置顶
	 */
	@Field(store = Store.YES, index = Index.YES, analyze = Analyze.NO)
	@Column(name = "f_is_top")
	public Boolean getIsTop() {
		return isTop;
	}

	/**
	 * 设置是否置顶
	 * 
	 * @param isTop
	 *            是否置顶
	 */
	public void setIsTop(Boolean isTop) {
		this.isTop = isTop;
	}

	/**
	 * 获取是否需要物流
	 * 
	 * @return 是否需要物流
	 */
	@NotNull
	@Column(name = "f_is_delivery", nullable = false)
	public Boolean getIsDelivery() {
		return isDelivery;
	}

	/**
	 * 设置是否需要物流
	 * 
	 * @param isDelivery
	 *            是否需要物流
	 */
	public void setIsDelivery(Boolean isDelivery) {
		this.isDelivery = isDelivery;
	}

	/**
	 * 获取介绍
	 * 
	 * @return 介绍
	 */
	@Field(store = Store.YES, index = Index.YES, analyze = Analyze.YES)
	@Lob
	@Column(name = "f_introduction")
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
	 * 获取售后
	 * 
	 * @return 售后
	 */
	@Field(store = Store.YES, index = Index.YES, analyze = Analyze.YES)
	@Lob
	@Column(name = "f_sale_support")
	public String getSaleSupport() {
		return saleSupport;
	}

	/**
	 * 设置售后
	 * 
	 * @param saleSupport
	 *            售后
	 */
	public void setSaleSupport(String saleSupport) {
		this.saleSupport = saleSupport;
	}

	/**
	 * 获取备注
	 * 
	 * @return 备注
	 */
	@Length(max = 200)
	@Column(name = "memo")
	public String getMemo() {
		return memo;
	}

	/**
	 * 设置备注
	 * 
	 * @param memo
	 *            备注
	 */
	public void setMemo(String memo) {
		this.memo = memo;
	}

	/**
	 * 获取搜索关键词
	 * 
	 * @return 搜索关键词
	 */
	@Field(store = Store.YES, index = Index.YES, analyze = Analyze.YES)
	@Length(max = 200)
	@Column(name = "f_keyword")
	public String getKeyword() {
		return keyword;
	}

	/**
	 * 设置搜索关键词
	 * 
	 * @param keyword
	 *            搜索关键词
	 */
	public void setKeyword(String keyword) {
		if (keyword != null) {
			keyword = keyword.replaceAll("[,\\s]*,[,\\s]*", ",").replaceAll(
					"^,|,$", "");
		}
		this.keyword = keyword;
	}

	/**
	 * 获取页面标题
	 * 
	 * @return 页面标题
	 */
	@Length(max = 200)
	@Column(name = "f_seo_title")
	public String getSeoTitle() {
		return seoTitle;
	}

	/**
	 * 设置页面标题
	 * 
	 * @param seoTitle
	 *            页面标题
	 */
	public void setSeoTitle(String seoTitle) {
		this.seoTitle = seoTitle;
	}

	/**
	 * 获取页面关键词
	 * 
	 * @return 页面关键词
	 */
	@Length(max = 200)
	@Column(name = "f_seo_keywords")
	public String getSeoKeywords() {
		return seoKeywords;
	}

	/**
	 * 设置页面关键词
	 * 
	 * @param seoKeywords
	 *            页面关键词
	 */
	public void setSeoKeywords(String seoKeywords) {
		if (seoKeywords != null) {
			seoKeywords = seoKeywords.replaceAll("[,\\s]*,[,\\s]*", ",")
					.replaceAll("^,|,$", "");
		}
		this.seoKeywords = seoKeywords;
	}

	/**
	 * 获取页面描述.
	 * 
	 * @return 页面描述
	 */
	@Length(max = 200)
	@Column(name = "f_seo_description")
	public String getSeoDescription() {
		return seoDescription;
	}

	/**
	 * 设置页面描述
	 * 
	 * @param seoDescription
	 *            页面描述
	 */
	public void setSeoDescription(String seoDescription) {
		this.seoDescription = seoDescription;
	}

	/**
	 * 获取属性值0
	 * 
	 * @return 属性值0
	 */
	@Length(max = 200)
	@Column(name = "f_attribute_value0")
	public String getAttributeValue0() {
		return attributeValue0;
	}

	/**
	 * 设置属性值0
	 * 
	 * @param attributeValue0
	 *            属性值0
	 */
	public void setAttributeValue0(String attributeValue0) {
		this.attributeValue0 = attributeValue0;
	}

	/**
	 * 获取属性值1
	 * 
	 * @return 属性值1
	 */
	@Length(max = 200)
	@Column(name = "f_attribute_value1")
	public String getAttributeValue1() {
		return attributeValue1;
	}

	/**
	 * 设置属性值1
	 * 
	 * @param attributeValue1
	 *            属性值1
	 */
	public void setAttributeValue1(String attributeValue1) {
		this.attributeValue1 = attributeValue1;
	}

	/**
	 * 获取属性值2
	 * 
	 * @return 属性值2
	 */
	@Length(max = 200)
	@Column(name = "f_attribute_value2")
	public String getAttributeValue2() {
		return attributeValue2;
	}

	/**
	 * 设置属性值2
	 * 
	 * @param attributeValue2
	 *            属性值2
	 */
	public void setAttributeValue2(String attributeValue2) {
		this.attributeValue2 = attributeValue2;
	}

	/**
	 * 获取属性值3
	 * 
	 * @return 属性值3
	 */
	@Length(max = 200)
	@Column(name = "f_attribute_value3")
	public String getAttributeValue3() {
		return attributeValue3;
	}

	/**
	 * 设置属性值3
	 * 
	 * @param attributeValue3
	 *            属性值3
	 */
	public void setAttributeValue3(String attributeValue3) {
		this.attributeValue3 = attributeValue3;
	}

	/**
	 * 获取属性值4
	 * 
	 * @return 属性值4
	 */
	@Length(max = 200)
	@Column(name = "f_attribute_value4")
	public String getAttributeValue4() {
		return attributeValue4;
	}

	/**
	 * 设置属性值4
	 * 
	 * @param attributeValue4
	 *            属性值4
	 */
	public void setAttributeValue4(String attributeValue4) {
		this.attributeValue4 = attributeValue4;
	}

	/**
	 * 获取属性值5
	 * 
	 * @return 属性值5
	 */
	@Length(max = 200)
	@Column(name = "f_attribute_value5")
	public String getAttributeValue5() {
		return attributeValue5;
	}

	/**
	 * 设置属性值5
	 * 
	 * @param attributeValue5
	 *            属性值5
	 */
	public void setAttributeValue5(String attributeValue5) {
		this.attributeValue5 = attributeValue5;
	}

	/**
	 * 获取属性值6
	 * 
	 * @return 属性值6
	 */
	@Length(max = 200)
	@Column(name = "f_attribute_value6")
	public String getAttributeValue6() {
		return attributeValue6;
	}

	/**
	 * 设置属性值6
	 * 
	 * @param attributeValue6
	 *            属性值6
	 */
	public void setAttributeValue6(String attributeValue6) {
		this.attributeValue6 = attributeValue6;
	}

	/**
	 * 获取属性值7
	 * 
	 * @return 属性值7
	 */
	@Length(max = 200)
	@Column(name = "f_attribute_value7")
	public String getAttributeValue7() {
		return attributeValue7;
	}

	/**
	 * 设置属性值7
	 * 
	 * @param attributeValue7
	 *            属性值7
	 */
	public void setAttributeValue7(String attributeValue7) {
		this.attributeValue7 = attributeValue7;
	}

	/**
	 * 获取属性值8
	 * 
	 * @return 属性值8
	 */
	@Length(max = 200)
	@Column(name = "f_attribute_value8")
	public String getAttributeValue8() {
		return attributeValue8;
	}

	/**
	 * 设置属性值8
	 * 
	 * @param attributeValue8
	 *            属性值8
	 */
	public void setAttributeValue8(String attributeValue8) {
		this.attributeValue8 = attributeValue8;
	}

	/**
	 * 获取属性值9
	 * 
	 * @return 属性值9
	 */
	@Length(max = 200)
	@Column(name = "f_attribute_value9")
	public String getAttributeValue9() {
		return attributeValue9;
	}

	/**
	 * 设置属性值9
	 * 
	 * @param attributeValue9
	 *            属性值9
	 */
	public void setAttributeValue9(String attributeValue9) {
		this.attributeValue9 = attributeValue9;
	}

	/**
	 * 获取属性值10
	 * 
	 * @return 属性值10
	 */
	@Length(max = 200)
	@Column(name = "f_attribute_value10")
	public String getAttributeValue10() {
		return attributeValue10;
	}

	/**
	 * 设置属性值10
	 * 
	 * @param attributeValue10
	 *            属性值10
	 */
	public void setAttributeValue10(String attributeValue10) {
		this.attributeValue10 = attributeValue10;
	}

	/**
	 * 获取属性值11
	 * 
	 * @return 属性值11
	 */
	@Length(max = 200)
	@Column(name = "f_attribute_value11")
	public String getAttributeValue11() {
		return attributeValue11;
	}

	/**
	 * 设置属性值11
	 * 
	 * @param attributeValue11
	 *            属性值11
	 */
	public void setAttributeValue11(String attributeValue11) {
		this.attributeValue11 = attributeValue11;
	}

	/**
	 * 获取属性值12
	 * 
	 * @return 属性值12
	 */
	@Length(max = 200)
	@Column(name = "f_attribute_value12")
	public String getAttributeValue12() {
		return attributeValue12;
	}

	/**
	 * 设置属性值12
	 * 
	 * @param attributeValue12
	 *            属性值12
	 */
	public void setAttributeValue12(String attributeValue12) {
		this.attributeValue12 = attributeValue12;
	}

	/**
	 * 获取属性值13
	 * 
	 * @return 属性值13
	 */
	@Length(max = 200)
	@Column(name = "f_attribute_value13")
	public String getAttributeValue13() {
		return attributeValue13;
	}

	/**
	 * 设置属性值13
	 * 
	 * @param attributeValue13
	 *            属性值13
	 */
	public void setAttributeValue13(String attributeValue13) {
		this.attributeValue13 = attributeValue13;
	}

	/**
	 * 获取属性值14
	 * 
	 * @return 属性值14
	 */
	@Length(max = 200)
	@Column(name = "f_attribute_value14")
	public String getAttributeValue14() {
		return attributeValue14;
	}

	/**
	 * 设置属性值14
	 * 
	 * @param attributeValue14
	 *            属性值14
	 */
	public void setAttributeValue14(String attributeValue14) {
		this.attributeValue14 = attributeValue14;
	}

	/**
	 * 获取属性值15
	 * 
	 * @return 属性值15
	 */
	@Length(max = 200)
	@Column(name = "f_attribute_value15")
	public String getAttributeValue15() {
		return attributeValue15;
	}

	/**
	 * 设置属性值15
	 * 
	 * @param attributeValue15
	 *            属性值15
	 */
	public void setAttributeValue15(String attributeValue15) {
		this.attributeValue15 = attributeValue15;
	}

	/**
	 * 获取属性值16
	 * 
	 * @return 属性值16
	 */
	@Length(max = 200)
	@Column(name = "f_attribute_value16")
	public String getAttributeValue16() {
		return attributeValue16;
	}

	/**
	 * 设置属性值16
	 * 
	 * @param attributeValue16
	 *            属性值16
	 */
	public void setAttributeValue16(String attributeValue16) {
		this.attributeValue16 = attributeValue16;
	}

	/**
	 * 获取属性值17
	 * 
	 * @return 属性值17
	 */
	@Length(max = 200)
	@Column(name = "f_attribute_value17")
	public String getAttributeValue17() {
		return attributeValue17;
	}

	/**
	 * 设置属性值17
	 * 
	 * @param attributeValue17
	 *            属性值17
	 */
	public void setAttributeValue17(String attributeValue17) {
		this.attributeValue17 = attributeValue17;
	}

	/**
	 * 获取属性值18
	 * 
	 * @return 属性值18
	 */
	@Length(max = 200)
	@Column(name = "f_attribute_value18")
	public String getAttributeValue18() {
		return attributeValue18;
	}

	/**
	 * 设置属性值18
	 * 
	 * @param attributeValue18
	 *            属性值18
	 */
	public void setAttributeValue18(String attributeValue18) {
		this.attributeValue18 = attributeValue18;
	}

	/**
	 * 获取属性值19
	 * 
	 * @return 属性值19
	 */
	@Length(max = 200)
	@Column(name = "f_attribute_value19")
	public String getAttributeValue19() {
		return attributeValue19;
	}

	/**
	 * 设置属性值19
	 * 
	 * @param attributeValue19
	 *            属性值19
	 */
	public void setAttributeValue19(String attributeValue19) {
		this.attributeValue19 = attributeValue19;
	}

	/**
	 * 获取品牌
	 * 
	 * @return 品牌
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "f_brand")
	public Brand getBrand() {
		return brand;
	}

	/**
	 * 设置品牌
	 * 
	 * @param brand
	 *            品牌
	 */
	public void setBrand(Brand brand) {
		this.brand = brand;
	}

	/**
	 * 获取商品分类
	 * 
	 * @return 商品分类
	 */
	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false)
	public ProductCategory getProductCategory() {
		return productCategory;
	}

	/**
	 * 设置商品分类
	 * 
	 * @param productCategory
	 *            商品分类
	 */
	public void setProductCategory(ProductCategory productCategory) {
		this.productCategory = productCategory;
	}

	/**
	 * 获取商品图片
	 * 
	 * @return 商品图片
	 */
	@Valid
	@Column(name = "f_product_images", length = 4000)
	@Convert(converter = ProductImageConverter.class)
	public List<ProductImage> getProductImages() {
		return productImages;
	}

	/**
	 * 设置商品图片
	 * 
	 * @param productImages
	 *            商品图片
	 */
	public void setProductImages(List<ProductImage> productImages) {
		this.productImages = productImages;
	}

	/**
	 * 获取参数值
	 * 
	 * @return 参数值
	 */
	@Valid
	@Column(name = "f_parameter_values", length = 4000)
	@Convert(converter = ParameterValueConverter.class)
	public List<ParameterValue> getParameterValues() {
		return parameterValues;
	}

	/**
	 * 设置参数值
	 * 
	 * @param parameterValues
	 *            参数值
	 */
	public void setParameterValues(List<ParameterValue> parameterValues) {
		this.parameterValues = parameterValues;
	}

	/**
	 * 获取规格项
	 * 
	 * @return 规格项
	 */
	@Valid
	@Column(name = "f_specification_items", length = 4000)
	@Convert(converter = SpecificationItemConverter.class)
	public List<SpecificationItem> getSpecificationItems() {
		return specificationItems;
	}

	/**
	 * 设置规格项
	 * 
	 * @param specificationItems
	 *            规格项
	 */
	public void setSpecificationItems(List<SpecificationItem> specificationItems) {
		this.specificationItems = specificationItems;
	}

	/**
	 * 获取促销
	 * 
	 * @return 促销
	 */
	@ManyToMany(mappedBy = "products", fetch = FetchType.LAZY)
	public Set<Promotion> getPromotions() {
		return promotions;
	}

	/**
	 * 设置促销
	 * 
	 * @param promotions
	 *            促销
	 */
	public void setPromotions(Set<Promotion> promotions) {
		this.promotions = promotions;
	}

	/**
	 * 获取评论
	 * 
	 * @return 评论
	 */
	@OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	public Set<Review> getReviews() {
		return reviews;
	}

	/**
	 * 设置评论
	 * 
	 * @param reviews
	 *            评论
	 */
	public void setReviews(Set<Review> reviews) {
		this.reviews = reviews;
	}

	/**
	 * 获取运营商.
	 * 
	 * @return 运营商
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "f_operator")
	public Operator getOperator() {
		return operator;
	}

	/**
	 * 设置运营商.
	 * 
	 * @param operator
	 *            运营商
	 */
	public void setOperator(Operator operator) {
		this.operator = operator;
	}

	/**
	 * 获取是否为预定
	 * 
	 * @return 是否预定
	 */
	@Column(name = "f_is_pre_order")
	public Boolean getIsPreOrder() {
		return isPreOrder;
	}

	/**
	 * 设置是否为预定
	 * 
	 * @param 是否预定
	 */
	public void setIsPreOrder(Boolean isPreOrder) {
		this.isPreOrder = isPreOrder;
	}

	/**
	 * 获取预售价格.
	 * 
	 * @return 预售价格
	 */
	@Min(0)
	@Digits(integer = 12, fraction = 3)
	@Column(name = "f_pre_order_price", precision = 21, scale = 6)
	public BigDecimal getPreOrderPrice() {
		return preOrderPrice;
	}

	/**
	 * 设置预售价格
	 * 
	 * @param 预售价格
	 */
	public void setPreOrderPrice(BigDecimal preOrderPrice) {
		this.preOrderPrice = preOrderPrice;
	}

	/**
	 * 获取发售日期
	 * 
	 * @return 发售日期
	 */
	@Field(store = Store.YES, index = Index.YES, analyze = Analyze.NO)
	@DateBridge(resolution = Resolution.SECOND)
	@Column(name = "f_pre_order_time")
	public Date getPreOrderTime() {
		return preOrderTime;
	}

	/**
	 * 设置发售日期
	 * 
	 * @param createDate
	 *            发售日期
	 */
	public void setPreOrderTime(Date preOrderTime) {
		this.preOrderTime = preOrderTime;
	}

	/**
	 * 获取预售描述
	 * 
	 * @return 预售描述
	 */
	@Length(max = 200)
	@Column(name = "f_pre_order_desc")
	public String getPreOrderDesc() {
		return preOrderDesc;
	}

	/**
	 * 设置预售描述.
	 * 
	 * @param desc
	 *            预售描述
	 */
	public void setPreOrderDesc(String preOrderDesc) {
		this.preOrderDesc = preOrderDesc;
	}

	/**
	 * 获取收藏会员
	 * 
	 * @return 收藏会员
	 */
	@ManyToMany(mappedBy = "favoriteGoods", fetch = FetchType.LAZY)
	public Set<Member> getFavoriteMembers() {
		return favoriteMembers;
	}

	/**
	 * 设置收藏会员
	 * 
	 * @param favoriteMembers
	 *            收藏会员
	 */
	public void setFavoriteMembers(Set<Member> favoriteMembers) {
		this.favoriteMembers = favoriteMembers;
	}

	/**
	 * 获取最大预售数量
	 * 
	 * @return 最大预售数量
	 */
	@Min(0)
	@Column(name = "f_pre_order_max_count")
	public Integer getPreOrderMaxCount() {
		return preOrderMaxCount;
	}

	/**
	 * 
	 * 设置最大预售数量.
	 * 
	 * @param preOrderMaxCount
	 *            最大预售数量
	 */
	public void setPreOrderMaxCount(Integer preOrderMaxCount) {
		this.preOrderMaxCount = preOrderMaxCount;
	}

	/**
	 * 获取剩余预售数量
	 * 
	 * @return 剩余预售数量
	 */
	@Min(0)
	@Column(name = "f_pre_order_count")
	public Integer getPreOrderCount() {
		return preOrderCount;
	}

	/**
	 * 
	 * 获取优惠券.
	 * 
	 * @return
	 */
	@ManyToMany(mappedBy = "products", fetch = FetchType.LAZY)
	public Set<Coupon> getCoupons() {
		return coupons;
	}

	/**
	 * 
	 * 设置优惠券.
	 * 
	 * @param coupons
	 */
	public void setCoupons(Set<Coupon> coupons) {
		this.coupons = coupons;
	}

	/**
	 * 
	 * 设置剩余预售数量.
	 * 
	 * @param preOrderCount
	 *            剩余预售数量
	 */
	public void setPreOrderCount(Integer preOrderCount) {
		this.preOrderCount = preOrderCount;
	}

	/**
	 * 获取路径
	 * 
	 * @return 路径
	 */
	@Transient
	public String getPath() {
		return null;
	}

	/**
	 * 获取URL
	 * 
	 * @return URL
	 */
	@Transient
	public String getUrl() {
		Setting setting = SystemUtils.getSetting();
		return setting.getSiteUrl() + getPath();
	}

	/**
	 * 获取缩略图
	 * 
	 * @return 缩略图
	 */
	@Transient
	public String getThumbnail() {
		if (CollectionUtils.isEmpty(getProductImages())) {
			return null;
		}
		return getProductImages().get(0).getThumbnail();
	}

	/**
	 * 获取可用库存
	 * 
	 * @return 可用库存
	 */
	@Transient
	public int getAvailableStock() {
		int availableStock = getStock() - getAllocatedStock();
		return availableStock >= 0 ? availableStock : 0;
	}

	/**
	 * 获取是否库存警告
	 * 
	 * @return 是否库存警告
	 */
	@Transient
	public boolean getIsStockAlert() {
		Setting setting = SystemUtils.getSetting();
		return setting.getStockAlertCount() != null
				&& getAvailableStock() <= setting.getStockAlertCount();
	}

	/**
	 * 获取是否缺货
	 * 
	 * @return 是否缺货
	 */
	@Transient
	public boolean getIsOutOfStock() {
		return getAvailableStock() <= 0;
	}

	/**
	 * 获取规格值ID
	 * 
	 * @return 规格值ID
	 */
	@Transient
	public List<Integer> getSpecificationValueIds() {
		List<Integer> specificationValueIds = new ArrayList<Integer>();
		if (CollectionUtils.isNotEmpty(getSpecificationValues())) {
			for (SpecificationValue specificationValue : getSpecificationValues()) {
				specificationValueIds.add(specificationValue.getId());
			}
		}
		return specificationValueIds;
	}

	/**
	 * 获取规格
	 * 
	 * @return 规格
	 */
	@Transient
	public List<String> getSpecifications() {
		List<String> specifications = new ArrayList<String>();
		if (CollectionUtils.isNotEmpty(getSpecificationValues())) {
			for (SpecificationValue specificationValue : getSpecificationValues()) {
				specifications.add(specificationValue.getValue());
			}
		}
		return specifications;
	}

	/**
	 * 获取有效促销
	 * 
	 * @return 有效促销
	 */
	@Transient
	public Set<Promotion> getValidPromotions() {

		if (!Product.Type.general.equals(getType())
				|| CollectionUtils.isEmpty(getPromotions())) {
			return Collections.emptySet();
		}
		Set<Promotion> promotions = new HashSet<Promotion>();

		for (Promotion promotion : getPromotions()) {
			if (promotion != null && promotion.hasBegun()
					&& !promotion.hasEnded()) {
				promotions.add(promotion);
			}
		}

		return promotions;
	}

	/**
	 * 是否存在规格
	 * 
	 * @return 是否存在规格
	 */
	@Transient
	public boolean hasSpecification() {
		return CollectionUtils.isNotEmpty(getSpecificationValues());
	}

	/**
	 * 判断促销是否有效
	 * 
	 * @param promotion
	 *            促销
	 * @return 促销是否有效
	 */
	@Transient
	public boolean isValid(Promotion promotion) {
		if (!Product.Type.general.equals(getType()) || promotion == null
				|| !promotion.hasBegun() || promotion.hasEnded()) {
			return false;
		}
		if (getValidPromotions().contains(promotion)) {
			return true;
		}
		return false;
	}

	/**
	 * 获取属性值
	 * 
	 * @param attribute
	 *            属性
	 * @return 属性值
	 */
	@Transient
	public String getAttributeValue(Attribute attribute) {
		if (attribute == null || attribute.getPropertyIndex() == null) {
			return null;
		}

		try {
			String propertyName = ATTRIBUTE_VALUE_PROPERTY_NAME_PREFIX
					+ attribute.getPropertyIndex();
			return (String) PropertyUtils.getProperty(this, propertyName);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e.getMessage(), e);
		} catch (InvocationTargetException e) {
			throw new RuntimeException(e.getMessage(), e);
		} catch (NoSuchMethodException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	/**
	 * 设置属性值
	 * 
	 * @param attribute
	 *            属性
	 * @param attributeValue
	 *            属性值
	 */
	@Transient
	public void setAttributeValue(Attribute attribute, String attributeValue) {
		if (attribute == null || attribute.getPropertyIndex() == null) {
			return;
		}

		try {
			String propertyName = ATTRIBUTE_VALUE_PROPERTY_NAME_PREFIX
					+ attribute.getPropertyIndex();
			PropertyUtils.setProperty(this, propertyName, attributeValue);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e.getMessage(), e);
		} catch (InvocationTargetException e) {
			throw new RuntimeException(e.getMessage(), e);
		} catch (NoSuchMethodException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	/**
	 * 移除所有属性值
	 */
	@Transient
	public void removeAttributeValue() {
		for (int i = 0; i < ATTRIBUTE_VALUE_PROPERTY_COUNT; i++) {
			String propertyName = ATTRIBUTE_VALUE_PROPERTY_NAME_PREFIX + i;
			try {
				PropertyUtils.setProperty(this, propertyName, null);
			} catch (IllegalAccessException e) {
				throw new RuntimeException(e.getMessage(), e);
			} catch (InvocationTargetException e) {
				throw new RuntimeException(e.getMessage(), e);
			} catch (NoSuchMethodException e) {
				throw new RuntimeException(e.getMessage(), e);
			}
		}
	}

	/**
	 * 持久化前处理
	 */
	@PrePersist
	public void prePersist() {
		if (CollectionUtils.isNotEmpty(getProductImages())) {
			Collections.sort(getProductImages());
		}
	}

	/**
	 * 更新前处理
	 */
	@PreUpdate
	public void preUpdate() {
		if (CollectionUtils.isNotEmpty(getProductImages())) {
			Collections.sort(getProductImages());
		}
	}

	/**
	 * 删除前处理
	 */
	@PreRemove
	public void preRemove() {
		Set<OrderItem> orderItems = getOrderItems();
		if (orderItems != null) {
			for (OrderItem orderItem : orderItems) {
				orderItem.setProduct(null);
			}
		}
		Set<ShippingItem> shippingItems = getShippingItems();
		if (shippingItems != null) {
			for (ShippingItem shippingItem : getShippingItems()) {
				shippingItem.setProduct(null);
			}
		}
		Set<Promotion> giftPromotions = getGiftPromotions();
		if (giftPromotions != null) {
			for (Promotion giftPromotion : giftPromotions) {
				giftPromotion.getGifts().remove(this);
			}
		}
		/**
		 * TODO
		 */
		Set<Member> favoriteMembers = getFavoriteMembers();
		if (favoriteMembers != null) {
			for (Member favoriteMember : favoriteMembers) {
				favoriteMember.getFavoriteGoods().remove(this);
			}
		}
	}

	/**
	 * 类型转换 - 规格值
	 * 
	 */
	@Converter
	public static class SpecificationValueConverter extends
			BaseAttributeConverter<List<SpecificationValue>> implements
			AttributeConverter<Object, String> {
	}

	/**
	 * 类型转换 - 商品图片
	 * 
	 */
	@Converter
	public static class ProductImageConverter extends
			BaseAttributeConverter<List<ProductImage>> implements
			AttributeConverter<Object, String> {
	}

	/**
	 * 类型转换 - 参数值
	 * 
	 */
	@Converter
	public static class ParameterValueConverter extends
			BaseAttributeConverter<List<ParameterValue>> implements
			AttributeConverter<Object, String> {
	}

	/**
	 * 获取标签
	 * 
	 * @return 标签
	 */
	@ManyToMany(mappedBy = "products", fetch = FetchType.LAZY)
	public Set<Tag> getTags() {
		return tags;
	}

	/**
	 * 设置标签
	 * 
	 * @param tags
	 *            标签
	 */
	public void setTags(Set<Tag> tags) {
		this.tags = tags;
	}

	/**
	 * 类型转换 - 规格项
	 */
	@Converter
	public static class SpecificationItemConverter extends
			BaseAttributeConverter<List<SpecificationItem>> implements
			AttributeConverter<Object, String> {
	}
	
	
	/**
	 * 获取门店商品关系
	 * 
	 * @return 门店商品关系
	 */
	@OneToMany(mappedBy = "productId", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	public Set<OrganizationProducts> getOrganizationProducts() {
		return organizationProducts;
	}

	public void setOrganizationProducts(
			Set<OrganizationProducts> organizationProducts) {
		this.organizationProducts = organizationProducts;
	}
	
	/**
	 * 
	 * 获取二维码图片 .
	 * <p>方法详细说明,如果要换行请使用<br>标签</p>
	 * <br>
	 * author: 施长成
	 *   date: 2015-11-23 下午1:54:08
	 * @return
	 */
	@Column(name="f_qrcodeimg" )
	public String getqRcodeImg() {
		return qRcodeImg;
	}

	/**
	 * 
	 * 设置二维码图片 .
	 * <p>方法详细说明,如果要换行请使用<br>标签</p>
	 * <br>
	 * author: 施长成
	 *   date: 2015-11-23 下午1:54:23
	 * @param qRcodeImg
	 */
	public void setqRcodeImg(String qRcodeImg) {
		this.qRcodeImg = qRcodeImg;
	}

	/**
	 * 
	 * 判断预定商品目前是否处于 预定状态 .
	 * <p>方法详细说明,如果要换行请使用<br>标签</p>
	 * <br>
	 * author: 施长成
	 *   date: 2015-11-6 上午11:03:30
	 * @return
	 */
	@Transient
	public boolean hasPrepreOrderIng(){
		
		if( getIsPreOrder()  ){
			if( new Date().before( getPreOrderTime() ) ){
				return true;
			}else{
				return false;
			}
		}
		return false;
	}

	public Boolean getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(Boolean isDefault) {
		this.isDefault = isDefault;
	}
	
	
	
}
