package com.puyuntech.ycmall.dao.impl;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import com.puyuntech.ycmall.dao.ContractPackageDao;
import com.puyuntech.ycmall.entity.Contract;
import com.puyuntech.ycmall.entity.ContractItem;

/**
 * 
 * DaoImpl - 套餐. 
 * Created on 2015-10-14 下午1:06:38 
 * @author 王凯斌
 */
@Repository("contractPackageDaoImpl")
public class ContractPackageDaoImpl extends BaseDaoImpl<ContractItem, Long> implements ContractPackageDao{

	@Override
	public List<ContractItem> findByContract(Contract contract) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<ContractItem> criteriaQuery = criteriaBuilder.createQuery(ContractItem.class);
		Root<ContractItem> root = criteriaQuery.from(ContractItem.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("contract"), contract));
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("state"), true));
		criteriaQuery.where(restrictions);
		return super.findList(criteriaQuery, null, null, null, null);
	}

}
