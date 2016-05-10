package com.puyuntech.ycmall.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.ui.ModelMap;

import com.puyuntech.ycmall.Page;
import com.puyuntech.ycmall.Pageable;
import com.puyuntech.ycmall.entity.Attribute;
import com.puyuntech.ycmall.entity.Brand;
import com.puyuntech.ycmall.entity.OrderItem;
import com.puyuntech.ycmall.entity.ProductModel;
import com.puyuntech.ycmall.entity.Product;
import com.puyuntech.ycmall.entity.ProductCategory;
import com.puyuntech.ycmall.entity.Promotion;
import com.puyuntech.ycmall.entity.Tag;

/**
 * 
 * Service 商品 .
 *  
 * Created on 2015-9-6 上午9:59:18 
 * @author 施长成
 */
public interface ProductService extends BaseService<Product, Long>{

	/**
	 * 
	 * 新增已分配库存 .
	 * <p>方法详细说明,如果要换行请使用<br>标签</p>
	 * <br>
	 * author: 施长成
	 *   date: 2015-9-13 下午3:56:39
	 * @param product 商品
	 * @param amount 数量
	 * 
	 * 生成订单的时候 就需要将相应数据的商品从库存转移到已分配库存中
	 */
	void addAllocatedStock(Product product, int amount);

	/**
	 * 
	 * 根据标签 获取 商品  .
	 * <p>方法详细说明,如果要换行请使用<br>标签</p>
	 * <br>
	 * author: 施长成
	 *   date: 2015-9-21 下午1:38:35
	 * @param tag
	 * @param isMarketable 是否上架 
	 * @return
	 */
	List<Product> listProductByTagType(Tag tag, boolean isMarketable);
	
	/**
	 * 根据编号查找货品 
	 * 	author: 施长成
	 * 	
	 * @param sn  
	 * 		编号(忽略大小写)
	 * @return
	 */
	Product findBySn(String sn);

	/**
	 * 
	 * 根据 货品 类型查询货品 .
	 * <p>比如查询 炫酷神器，限量神器等</p>
	 * <br>
	 * author: 施长成
	 *   date: 2015-9-2 下午5:37:29
	 * @param tag 炫酷神器 限量神器 秒杀神器 标签
	 * @param isMarketable 是否上架
	 * @return
	 */
	List<Product> listGoodsByTagType(Tag tag, boolean isMarketable);
	
	/**
	 * 
	 * 生成图片和编号 .
	 * <p>方法详细说明,如果要换行请使用<br>标签</p>
	 * <br>
	 * author: 王凯斌
	 *   date: 2015-10-13 下午1:08:04
	 * @param product
	 */
	public void setValue(Product product);

	/**
	 * 
	 * 根据 model 查找到和model一样的商品信息 .
	 * <br>
	 * author: 施长成
	 *   date: 2015-10-14 下午3:05:36
	 * @param productModel 商品型号
	 * @return
	 */
	List<Product> listProductsByModel(ProductModel productModel);
	
	/**
	 * 
	 * 重写分页查找.
	 * 
	 * author: 王凯斌
	 *   date: 2015-10-15 下午2:58:17
	 * @param type
	 * @param productCategory
	 * @param brand
	 * @param productModel
	 * @param promotion
	 * @param tag
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
	Page<Product> findPage(Product.Type type, ProductCategory productCategory, Brand brand, ProductModel productModel,Promotion promotion, Tag tag, Map<Attribute, String> attributeValueMap, BigDecimal startPrice, BigDecimal endPrice, Boolean isMarketable, Boolean isList, Boolean isTop, Boolean isOutOfStock,
			Boolean isStockAlert, Boolean hasPromotion, Product.OrderType orderType, Pageable pageable);
	
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
	 * 查选选择套餐的相关信息和数据 .
	 * <p>方法详细说明,如果要换行请使用<br>标签</p>
	 * <br>
	 * author: 施长成
	 *   date: 2015-10-18 上午11:21:11
	 * @param model
	 * @param productId
	 * @throws Exception 
	 */
	void querySelectContractInfo(ModelMap model, Long productId) throws Exception;
	
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
	 * 根据商品编号 ， 获取 商品信息 ，包括促销信息 .
	 * <p>方法详细说明,如果要换行请使用<br>标签</p>
	 * <br>
	 * author: 施长成
	 *   date: 2015-11-29 上午9:57:55
	 * @param goodsId 商品Id
	 * @return
	 */
	Map<String, Object> getProductInfo(String goodsId);

	/**
	 * 
	 * 商品规格信息 选择 .
	 * author: 施长成
	 *   date: 2015-11-29 上午11:28:22
	 * @param goodsId 商品编号
	 * @param shopId 门店编号
	 * @return
	 */
	Map<String, Object> getNewGoods(String goodsId,String shopId);

	/**
	 * 
	 * 重新选择商品（上面部分）
	 * author: 南金豆
	 *   date: 2015-11-30 下午6:59:33
	 * @param goodsId
	 * @param shopId
	 * @return
	 */
	Map<String, Object> getNewGoodsTop(String goodsId, String shopId);

	
	/**
	 * 【今日抢】抢购成功：成功界面展示接口：商品图片、简介+绑定推荐配套商品图片、名称等（绑定推荐等只限定与商品部分】 author: 南金豆
	 * date: 2015-11-11 下午4:47:07
	 * 
	 * @param grabSeckillId
	 *            抢购编号
	 * @param robGoodsId
	 *            抢购商品编号
	 * @return
	 */
	Map<String, Object> getRushGoodsRecommend(String grabSeckillId,
			String robGoodsId);

	Product findByOrderitem(OrderItem orderitem);
}
