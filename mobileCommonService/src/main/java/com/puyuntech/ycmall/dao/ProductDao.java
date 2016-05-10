package com.puyuntech.ycmall.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.puyuntech.ycmall.Page;
import com.puyuntech.ycmall.Pageable;
import com.puyuntech.ycmall.entity.Attribute;
import com.puyuntech.ycmall.entity.Brand;
import com.puyuntech.ycmall.entity.ProductModel;
import com.puyuntech.ycmall.entity.Product;
import com.puyuntech.ycmall.entity.ProductCategory;
import com.puyuntech.ycmall.entity.Promotion;
import com.puyuntech.ycmall.entity.Tag;

/**
 * 
 * 商品 Dao . 
 * Created on 2015-9-6 上午10:39:21 
 * @author 施长成
 */
public interface ProductDao extends BaseDao<Product, Long> {

	/**
	 * 
	 * 根据标签获取已经上架的商品信息  .
	 * <p>方法详细说明,如果要换行请使用<br>标签</p>
	 * <br>
	 * author: 施长成
	 *   date: 2015-9-21 下午1:41:33
	 * @param tag
	 * @param isMarketable
	 * @return
	 */
	List<Product> listProductByTagType(Tag tag, Boolean isMarketable);

	/**
	 * 根据编号 查找 商品
	 * 
	 * author: 施长成
	 * 
	 * @param sn 货号 
	 * @return
	 */
	Product findBySn(String sn);

	/**
	 * 
	 * 根据 tag 类型查询 货品信息.
	 * <p> 比如查询 炫酷神器，限量神器等 </p>
	 * <br>
	 * author: 施长成
	 *   date: 2015-9-2 下午5:41:04
	 * @param tag
	 * @param isMarketable 是否上架
	 * @return
	 */
	List<Product> listGoodsByTagType(Tag tag, Boolean isMarketable);
	
	/**
	 * 清空货品属性值
	 * 
	 * @param attribute
	 *            属性
	 */
	void clearAttributeValue(Attribute attribute);
	
	/**
	 * 
	 * 商品编号是否存在.
	 * 
	 * author: 王凯斌
	 *   date: 2015-10-14 下午1:18:10
	 * @param sn
	 * @return
	 */
	boolean snExists(String sn);

	/**
	 * 
	 * 根据 model 查询同型号的商品  .
	 * <br>
	 * author: 施长成
	 *   date: 2015-10-14 下午3:20:26
	 * @param productModel 型号
	 * @return
	 */
	List<Product> listProductsByModel(ProductModel productModel);
	
	/**
	 * 
	 * 重写商品分页方法.
	 * 
	 * author: 王凯斌
	 *   date: 2015-10-15 下午2:12:57
	 * @param type
	 * @param productCategory
	 * @param brand
	 * @param promotion
	 * @param tag
	 * @param productModel
	 * @param attributeValueMap
	 * @param startPrice
	 * @param endPrice
	 * @param isMarketable
	 * @param isList
	 * @param isTop
	 * @param isOutOfStock
	 * @param isStockAlert
	 * @param hasPromotion
	 * @param orderType
	 * @param pageable
	 * @return
	 */
	Page<Product> findPage(Product.Type type, ProductCategory productCategory,
			Brand brand, Promotion promotion, Tag tag, ProductModel productModel,
			Map<Attribute, String> attributeValueMap, BigDecimal startPrice,
			BigDecimal endPrice, Boolean isMarketable, Boolean isList,
			Boolean isTop, Boolean isOutOfStock, Boolean isStockAlert,
			Boolean hasPromotion, Product.OrderType orderType, Pageable pageable);
	
	/**
	 * 
	 * 规格是否唯一.
	 * 
	 * author: 王凯斌
	 *   date: 2015-10-15 下午3:25:28
	 * @param product
	 * @return
	 */
	boolean specificationValueExists(Product product);
	
	/**
	 * 
	 * 积分商城.
	 * 
	 * author: 王凯斌
	 *   date: 2015-10-19 下午3:45:24
	 * @return
	 */
	List<Product> scoreProducts(Integer min,Integer max);

	
	/**
	 * 
	 * 商品在店面库存
	 * author: 南金豆
	 *   date: 2015-11-30 上午11:27:18
	 * @param goodsId
	 * @param shopId
	 * @return 
	 */
	String selectGoodsStock(String goodsId, String shopId);

    /**
     * 根据商品ID 或者 商品编号查询商品
     * @param productId
     * @param productSn
     * @return
     */
    public Product getProductByProductInfo( String productId , String productSn );
}
