package com.puyuntech.ycmall.dao.impl;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import com.puyuntech.ycmall.Page;
import com.puyuntech.ycmall.Pageable;
import com.puyuntech.ycmall.dao.ReviewDao;
import com.puyuntech.ycmall.entity.Product;
import com.puyuntech.ycmall.entity.ProductModel;
import com.puyuntech.ycmall.entity.Review;

/**
 * 
 * DaoImpl - 评论. Created on 2015-12-3 上午10:11:24
 * 
 * @author 严志森
 */
@Repository("reviewDaoImpl")
public class ReviewDaoImpl extends BaseDaoImpl<Review, Long> implements
		ReviewDao {

	@Override
	public Long count(Product product) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Review> criteriaQuery = criteriaBuilder.createQuery(Review.class);
		Root<Review> root = criteriaQuery.from(Review.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		Path<Product> productPath = root.get("product");
		if (product != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(productPath, product));
		}
		
		criteriaQuery.where(restrictions);
		return super.count(criteriaQuery, null);
	}

	@Override
	public Page<Review> findPage(ProductModel model, Pageable pageable) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Review> criteriaQuery = criteriaBuilder.createQuery(Review.class);
		Root<Review> root = criteriaQuery.from(Review.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		if (model != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("productModel"), model));
		}
		criteriaQuery.where(restrictions);
		return super.findPage(criteriaQuery, pageable);
	}
}
