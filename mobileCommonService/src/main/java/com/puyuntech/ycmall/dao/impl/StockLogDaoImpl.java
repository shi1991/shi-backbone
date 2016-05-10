package com.puyuntech.ycmall.dao.impl;


import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.apache.commons.lang.StringUtils;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.puyuntech.ycmall.Page;
import com.puyuntech.ycmall.Pageable;
import com.puyuntech.ycmall.dao.StockLogDao;
import com.puyuntech.ycmall.entity.Organization;
import com.puyuntech.ycmall.entity.Product;
import com.puyuntech.ycmall.entity.StockLog;
import com.puyuntech.ycmall.vo.ResultVO;

/**
 * 
 * DaoImpl - 库存记录. 
 * Created on 2015-10-14 下午1:06:38 
 * @author 王凯斌
 */
@Repository("stockLogDaoImpl")
public class StockLogDaoImpl extends BaseDaoImpl<StockLog, Long> implements StockLogDao{
	
	@PersistenceContext
	protected EntityManager entityManager;
	
	@Override
	public StockLog findBySn(String sn) {
		if (StringUtils.isEmpty(sn)) {
			logger.warn("StockLogDaoImpl findBySn param sn is null");
		}

		String jpql = "select stockLogs from StockLog stockLogs where lower(stockLogs.productSn) = lower(:productSn)";

		try {
			return entityManager.createQuery(jpql, StockLog.class)
					.setParameter("productSn", sn).getSingleResult();
		} catch (NoResultException e) {
			logger.error("StockLogDaoImpl findBySn param sn is :" + sn
					+ " error is :" + e);
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ResultVO> findSnById(String ids) {
		try {
			String sql = "SELECT a.f_id id,a.f_product_sn sn from t_stock_log a WHERE a.f_id in ("+ids+")";
			//执行sql语句
			Query query = entityManager.createNativeQuery(sql);
			//结果集格式化
			query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			List<ResultVO> list= query.getResultList();
			return list;
		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public Page<StockLog> findPage(Pageable pageable) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<StockLog> criteriaQuery = criteriaBuilder.createQuery(StockLog.class);
		Root<StockLog> root = criteriaQuery.from(StockLog.class);
		criteriaQuery.select(root);
		return super.findPage(criteriaQuery, pageable);
	}

	@Override
	public List<StockLog> findSnByShopId(String shopid) {
		try {
			String sql = "select stockLog from StockLog  stockLog group by stockLog.product.name,stockLog.organization.name";
			//执行sql语句
			//Query query = entityManager.createNativeQuery(sql);
			
			return entityManager.createQuery(sql, StockLog.class).getResultList();
			//结果集格式化
		
		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public StockLog findBySnAndProId(Product product, String productSn, Organization organization) {
		if ( product == null ) {
			logger.warn("StockLogDaoImpl findBySnAndProId param product is null");
		}
		
		if ( StringUtils.isEmpty(productSn) ) {
			logger.warn("StockLogDaoImpl findBySnAndProId param productSn is null");
		}
		
		if ( organization == null ) {
			logger.warn("StockLogDaoImpl findBySnAndProId param organization is null");
		}
		
		String jpql = "select stockLogs from StockLog stockLogs where lower(stockLogs.productSn) = lower(:productSn) and stockLogs.product = :product and stockLogs.organization = :organization";

		try {
			return entityManager.createQuery(jpql, StockLog.class)
					.setParameter("productSn", productSn).setParameter("product", product).setParameter("organization", organization).getSingleResult();
		} catch (NoResultException e) {
			logger.error("StockLogDaoImpl findBySnAndProId param productSn is :" + productSn +" param productId is : " + product +" param organization is : " + organization
					+ " error is :" + e);
			return null;
		}
	}


	

}
