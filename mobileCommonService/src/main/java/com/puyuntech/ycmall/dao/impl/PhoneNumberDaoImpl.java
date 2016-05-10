package com.puyuntech.ycmall.dao.impl;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.puyuntech.ycmall.Page;
import com.puyuntech.ycmall.Pageable;
import com.puyuntech.ycmall.dao.PhoneNumberDao;
import com.puyuntech.ycmall.entity.Member;
import com.puyuntech.ycmall.entity.Operator;
import com.puyuntech.ycmall.entity.PhoneNumber;

/**
 * 
 * DaoImpl - 手机号码. 
 * Created on 2015-10-14 下午1:06:38 
 * @author 王凯斌
 * 
 * update 施长成
 */
@Repository("phoneNumberDaoImpl")
public class PhoneNumberDaoImpl extends BaseDaoImpl<PhoneNumber, Long> implements PhoneNumberDao{

	@Override
	public Page<PhoneNumber> findPage(String phoneNumer ,Operator operator, Pageable pageable) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<PhoneNumber> criteriaQuery = criteriaBuilder.createQuery(PhoneNumber.class);
		Root<PhoneNumber> root = criteriaQuery.from(PhoneNumber.class);
		criteriaQuery.select(root);
		Predicate conditions = criteriaBuilder.conjunction();
		
		conditions = criteriaBuilder.and(conditions , criteriaBuilder.equal(root.get("isSold"), PhoneNumber.PHONESTATE.unsold));
		
		if( null != operator ){
			conditions = criteriaBuilder.and(conditions , criteriaBuilder.equal(root.get("operator"), operator));
		}
		if(StringUtils.isNotEmpty(phoneNumer) ){
			conditions = criteriaBuilder.and(conditions , criteriaBuilder.like(root.get("number").as(String.class),  "%"+phoneNumer+"%" ));
		}
		criteriaQuery.where(conditions);
		
		return super.findPage( criteriaQuery , pageable );
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PhoneNumber> findListByMemberOrKey(Member member, String key) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<PhoneNumber> criteriaQuery = criteriaBuilder.createQuery(PhoneNumber.class);
		Root<PhoneNumber> root = criteriaQuery.from(PhoneNumber.class);
		criteriaQuery.select(root);
		
		Predicate conditions = criteriaBuilder.conjunction();
		
		//加入member条件 
		if(null != member ){
			conditions = criteriaBuilder.and(conditions , criteriaBuilder.equal(root.get("member"), member) );
		}
		
		//加入 key 条件
		if(StringUtils.isNotEmpty(key)){
			conditions = criteriaBuilder.and(conditions , criteriaBuilder.equal(root.get("phoneKey"), key) );
		}
		
		//加入 号码 当前状态
		conditions = criteriaBuilder.and(conditions , criteriaBuilder.equal(root.get("isSold"), PhoneNumber.PHONESTATE.locked ) );
		
		criteriaQuery.where(conditions);
		
		Query query = entityManager.createQuery(criteriaQuery);
		
		List<PhoneNumber> phones = query.getResultList();
		
		return phones;
	}

	@Override
	public PhoneNumber findByNumber(String number) {
		if (StringUtils.isEmpty(number)) {
			logger.warn("phoneNumberDaoImpl findByNumber param sn is null");
		}

		String jpql = "select phoneNumbers from PhoneNumber phoneNumbers where lower(phoneNumbers.number) = lower(:number)";

		try {
			return entityManager.createQuery(jpql, PhoneNumber.class)
					.setParameter("number", number).getSingleResult();
		} catch (NoResultException e) {
			logger.error("phoneNumberDaoImpl findByNumber param sn is :" + number
					+ " error is :" + e);
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PhoneNumber> findByOperator(Operator operator,int count,String key) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<PhoneNumber> criteriaQuery = criteriaBuilder.createQuery(PhoneNumber.class);
		Root<PhoneNumber> root = criteriaQuery.from(PhoneNumber.class);
		criteriaQuery.select(root);
		Predicate conditions = criteriaBuilder.conjunction();
		
		conditions = criteriaBuilder.and(conditions , criteriaBuilder.equal(root.get("isSold"), PhoneNumber.PHONESTATE.unsold));
		
		if( null != operator ){
			conditions = criteriaBuilder.and(conditions , criteriaBuilder.equal(root.get("operator"), operator));
		}
		if(StringUtils.isNotEmpty(key) ){
			conditions = criteriaBuilder.and(conditions , criteriaBuilder.like(root.get("number").as(String.class),  "%"+key+"%" ));
		}
		
		criteriaQuery.where(conditions);
		Query query = entityManager.createQuery(criteriaQuery);
		
// 		String jpql = "select  phoneNumber from PhoneNumber  phoneNumber where phoneNumber.operator=:operator and phoneNumber.isSold=:name";
//		//执行sql语句
// 		TypedQuery<PhoneNumber> query = entityManager.createQuery(jpql, PhoneNumber.class).setParameter("operator", operator).setParameter("name", PhoneNumber.PHONESTATE.locked);
 		List<PhoneNumber> q=query.getResultList();
 		int size=q.size();
 		int r=(int) Math.floor(Math.random()*size);
 		query.setMaxResults(count); 
 		if(size-r<count){
 			r=r-count;
 		}
 		if(r<0)
 			r=0;
 		query.setFirstResult(r); 
		return query.getResultList();
		
	}
		

}
