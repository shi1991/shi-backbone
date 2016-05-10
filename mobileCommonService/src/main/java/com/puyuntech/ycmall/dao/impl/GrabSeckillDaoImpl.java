package com.puyuntech.ycmall.dao.impl;

import java.util.Arrays;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.puyuntech.ycmall.dao.GrabSeckillDao;
import com.puyuntech.ycmall.entity.GrabSeckill;
import com.puyuntech.ycmall.entity.GrabSeckill.GoodsTypeEnmu;
import com.puyuntech.ycmall.entity.GrabSeckill.GrabSecKillTypeEnmu;

/**
 * 抢购 Dao
 * Created on 2015-11-10 下午1:54:11 
 * @author 南金豆
 */
@Repository("grabSeckillDaoImpl")
public class GrabSeckillDaoImpl extends BaseDaoImpl<GrabSeckill, Long> implements GrabSeckillDao {
	@Override
	public List<GrabSeckill> listGrabSeckillBySns(String[] sns,
			GrabSecKillTypeEnmu type, GoodsTypeEnmu goodsType) {

		if(ArrayUtils.isEmpty(sns)){
			return null;
		}
		
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<GrabSeckill> criteriaQuery = criteriaBuilder.createQuery(GrabSeckill.class);
		
		Root<GrabSeckill> root = criteriaQuery.from(GrabSeckill.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		restrictions = criteriaBuilder.and(restrictions ,  criteriaBuilder.in(root.get("goods")).value(Arrays.asList(sns) ));
		
		criteriaQuery.where(restrictions);
		TypedQuery<GrabSeckill> typedQuery = entityManager.createQuery(criteriaQuery);
		List<GrabSeckill> list = typedQuery.getResultList();
		return list ;
	}

	@Override
	public GrabSeckill listGrabSeckillBySn(String sn, GrabSecKillTypeEnmu type,
			GoodsTypeEnmu goodsType) {
		if(StringUtils.isEmpty(sn)){
			return null;
		}
		
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<GrabSeckill> criteriaQuery = criteriaBuilder.createQuery(GrabSeckill.class);
		
		Root<GrabSeckill> root = criteriaQuery.from(GrabSeckill.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		
		restrictions = criteriaBuilder.and(restrictions ,  criteriaBuilder.equal(root.get("goods") , sn) );
		
		criteriaQuery.where(restrictions);
		TypedQuery<GrabSeckill> typedQuery = entityManager.createQuery(criteriaQuery);
		GrabSeckill grabSeckill = null;
		try{
			grabSeckill = typedQuery.getSingleResult();
		}catch (NoResultException e) {
			logger.error("listGrabSeckillBySn get value is null , param is sn = "+sn );
			grabSeckill = null;
		}
		return grabSeckill ;
	}

	@Override
	public GrabSeckill findByPosition(int i) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<GrabSeckill> criteriaQuery = criteriaBuilder.createQuery(GrabSeckill.class);
		
		Root<GrabSeckill> root = criteriaQuery.from(GrabSeckill.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		
		restrictions = criteriaBuilder.and(restrictions ,  criteriaBuilder.equal(root.get("position") , i) );
		
		criteriaQuery.where(restrictions);
		TypedQuery<GrabSeckill> typedQuery = entityManager.createQuery(criteriaQuery);
		if(typedQuery.getResultList().size() == 0){
			return null ;
		}else{
			GrabSeckill grabSeckill = typedQuery.getSingleResult();
		 	return grabSeckill ;
		}
	 	
	}
}
