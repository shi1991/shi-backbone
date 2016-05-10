package com.puyuntech.ycmall.dao;

import com.puyuntech.ycmall.Page;
import com.puyuntech.ycmall.Pageable;
import com.puyuntech.ycmall.entity.Product;
import com.puyuntech.ycmall.entity.ProductModel;
import com.puyuntech.ycmall.entity.Review;

/**
 * 
 * 评论 - DAO. Created on 2015-12-3 上午10:11:24
 * 
 * @author 严志森
 */
public interface ReviewDao extends BaseDao<Review, Long> {
	
	/**
	 * 
	 * 查找商品评论数量.
	 * author: 严志森
	 *   date: 2015-12-3 上午10:11:24
	 * @param product
	 * @return
	 */
	Long count(Product product);

	Page<Review> findPage(ProductModel model, Pageable pageable);
}
