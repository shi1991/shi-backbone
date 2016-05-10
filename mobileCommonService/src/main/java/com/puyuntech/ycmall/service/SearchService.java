package com.puyuntech.ycmall.service;

import java.math.BigDecimal;

import com.puyuntech.ycmall.Page;
import com.puyuntech.ycmall.Pageable;
import com.puyuntech.ycmall.entity.Product;

/**
 * 
 * Service 搜索. 
 * Created on 2015-9-9 上午11:05:54 
 * @author 施长成
 */
public interface SearchService{
	
	/**
	 * 创建索引
	 */
	void index();

	/**
	 * 创建索引
	 * 
	 * @param type
	 *            索引类型
	 */
	void index(Class<?> type);

	/**
	 * 创建索引
	 * 
	 * @param products
	 *            商品
	 */
	void index(Product products);

	/**
	 * 删除索引
	 */
	void purge();

	/**
	 * 删除索引
	 * 
	 * @param type
	 *            索引类型
	 */
	void purge(Class<?> type);

	/**
	 * 删除索引
	 * 
	 * @param products
	 *            商品
	 */
	void purge(Product products);
	
	/**
	 * 搜索货品分页
	 * 
	 * @param keyword
	 *            关键词
	 * @param startPrice
	 *            最低价格 空值传null
	 * @param endPrice
	 *            最高价格 空值传null
	 * @param orderType
	 *            排序类型 空值传null
	 * @param pageable
	 *            分页信息
	 * @return 货品分页
	 */
	Page<Product> search(String keyword, BigDecimal startPrice, BigDecimal endPrice, Product.OrderType orderType, Pageable pageable);

}
