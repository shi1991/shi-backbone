package com.puyuntech.ycmall.dao.impl;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang.StringUtils;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.puyuntech.ycmall.dao.OperatorDao;
import com.puyuntech.ycmall.entity.Operator;
import com.puyuntech.ycmall.vo.ResultVO;

/**
 * 
 * DaoImpl - 运营商. 
 * Created on 2015-10-14 下午1:06:38 
 * @author 王凯斌
 */
@Repository("operatorDaoImpl")
public class OperatorDaoImpl extends BaseDaoImpl<Operator, Long> implements OperatorDao{

	@Override
	public Operator findByName(String name) {
		if (StringUtils.isEmpty(name)) {
			logger.warn("operatorDaoImpl findByName param name is null");
		}

		String jpql = "select operators from Operator operators where operators.name = :name";

		try {
			return entityManager.createQuery(jpql, Operator.class)
					.setParameter("name", name).getSingleResult();
		} catch (NoResultException e) {
			logger.error("operatorDaoImpl findByName param name is :" + name
					+ " error is :" + e);
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ResultVO> findOther() {
		String sql="SELECT a.f_id id ,a.f_name name FROM t_operator a WHERE a.f_id not in (SELECT b.f_operator FROM t_contract b ) and a.f_id <> 1";

		//执行sql语句
		Query query = entityManager.createNativeQuery(sql);
		//结果集格式化
		query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ResultVO> findBy() {
		String sql="SELECT a.f_id id ,a.f_name name FROM t_operator a WHERE a.f_id <> 1";

		//执行sql语句
		Query query = entityManager.createNativeQuery(sql);
		//结果集格式化
		query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		return query.getResultList();
	}

	@Override
	public List<Operator> findNoFei() {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Operator> criteriaQuery = criteriaBuilder.createQuery(Operator.class);
		Root<Operator> root = criteriaQuery.from(Operator.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.notEqual(root.get("id"), 1));
		criteriaQuery.where(restrictions);
		return super.findList(criteriaQuery, null, null, null, null);
	}

}
