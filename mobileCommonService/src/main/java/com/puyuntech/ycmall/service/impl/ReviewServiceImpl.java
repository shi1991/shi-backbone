package com.puyuntech.ycmall.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.puyuntech.ycmall.Page;
import com.puyuntech.ycmall.Pageable;
import com.puyuntech.ycmall.dao.ReviewDao;
import com.puyuntech.ycmall.entity.Product;
import com.puyuntech.ycmall.entity.ProductModel;
import com.puyuntech.ycmall.entity.Review;
import com.puyuntech.ycmall.service.ReviewService;

/**
 * 
 * ServiceImpl - 评论. Created on 2015-12-3 上午10:11:24
 * 
 * @author 严志森
 */
@Service("reviewServiceImpl")
public class ReviewServiceImpl extends BaseServiceImpl<Review, Long> implements
		ReviewService {

	/**
	 * 引入dao对象
	 */
	@Resource(name = "reviewDaoImpl")
	private ReviewDao reviewDao;

	@Override
	public Long count(Product product) {
		return reviewDao.count(product);
	}

	@Override
	public Page<Review> findPage(ProductModel model, Pageable pageable) {
		return reviewDao.findPage(model,pageable);
	}

}
