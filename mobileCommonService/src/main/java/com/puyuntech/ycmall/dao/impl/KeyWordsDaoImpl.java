package com.puyuntech.ycmall.dao.impl;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import com.puyuntech.ycmall.Page;
import com.puyuntech.ycmall.dao.KeyWordsDao;
import com.puyuntech.ycmall.entity.KeyWords;

/**
 * 
 * Service 猜你喜欢 . 
 * Created on 2015-10-12 下午15:57:50 
 * @author 南金豆
 */
@Repository("keyWordsDaoImpl")
public class KeyWordsDaoImpl extends BaseDaoImpl<KeyWords, Long> implements KeyWordsDao {

	@Override
	public Page<KeyWords> findPage() {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<KeyWords> criteriaQuery = criteriaBuilder.createQuery(KeyWords.class);
		Root<KeyWords> root = criteriaQuery.from(KeyWords.class);
		criteriaQuery.select(root);
		return super.findPage(criteriaQuery, null);
	}

	
}
