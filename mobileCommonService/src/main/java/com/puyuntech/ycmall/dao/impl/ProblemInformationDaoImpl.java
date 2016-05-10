package com.puyuntech.ycmall.dao.impl;

import java.util.List;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.puyuntech.ycmall.dao.ProblemInformationDao;
import com.puyuntech.ycmall.entity.ProblemClassification;
import com.puyuntech.ycmall.entity.ProblemInformation;

/**
 * 
 * dao 帮助中心 问题内容.
 * Created on 2015-11-20 下午2:58:04 
 * @author 严志森
 */
@Repository("problemInformationDaoImpl")
public class ProblemInformationDaoImpl extends BaseDaoImpl<ProblemInformation, Long> implements ProblemInformationDao {

	@Override
	public List<ProblemInformation> findByParent(ProblemClassification id) {
		String jpql = "select problemInformation from ProblemInformation problemInformation where problemInformation.problemClassification=:id order by problemInformation.order asc";
		TypedQuery<ProblemInformation> query = entityManager.createQuery(jpql, ProblemInformation.class).setParameter("id", id);
		return query.getResultList();
	}

}