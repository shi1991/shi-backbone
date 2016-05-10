package com.puyuntech.ycmall.dao.impl;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;



import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.puyuntech.ycmall.Page;
import com.puyuntech.ycmall.Pageable;
import com.puyuntech.ycmall.dao.MemberDao;
import com.puyuntech.ycmall.entity.Member;
import com.puyuntech.ycmall.entity.MemberAttribute;

/**
 * 
 * Dao - 会员 
 * Created on 2015-8-26 下午15:32:33 
 * @author 严志森
 */
@Repository("memberDaoImpl")
public class MemberDaoImpl extends BaseDaoImpl<Member, Long> implements MemberDao {

	public boolean usernameExists(String username) {
		if (StringUtils.isEmpty(username)) {
			return false;
		}
		String jpql = "select count(*) from Member members where lower(members.username) = lower(:username)";
		Long count = entityManager.createQuery(jpql, Long.class).setParameter("username", username).getSingleResult();
		return count > 0;
	}
	@Override
	public boolean phoneExists(String phone) {
		if (StringUtils.isEmpty(phone)) {
			return false;
		}
		String jpql = "select count(*) from Member members where lower(members.phone) = lower(:phone)";
		Long count = entityManager.createQuery(jpql, Long.class).setParameter("phone", phone).getSingleResult();
		return count > 0;
	}
	public boolean nicknameExists(String nickname) {
		if (StringUtils.isEmpty(nickname)) {
			return false;
		}
		String jpql = "select count(*) from Member members where lower(members.nickname) = lower(:nickname)";
		Long count = entityManager.createQuery(jpql, Long.class).setParameter("nickname", nickname).getSingleResult();
		return count > 0;
	}
	public boolean emailExists(String email) {
		if (StringUtils.isEmpty(email)) {
			return false;
		}
		String jpql = "select count(*) from Member members where members.email = :email";
		Long count = entityManager.createQuery(jpql, Long.class).setParameter("email", email).getSingleResult();
		return count > 0;
	}

	public Member find(String loginPluginId, String openId) {
		if (StringUtils.isEmpty(loginPluginId) || StringUtils.isEmpty(openId)) {
			return null;
		}
		try {
			String jpql = "select members from Member members where members.loginPluginId = :loginPluginId and members.openId = :openId";
			return entityManager.createQuery(jpql, Member.class).setParameter("loginPluginId", loginPluginId).setParameter("openId", openId).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public Member findByUsername(String username) {
		if (StringUtils.isEmpty(username)) {
			return null;
		}
		try {
			String jpql = "select members from Member members where lower(members.username) = lower(:username) or members.phone =:username ";
			return entityManager.createQuery(jpql, Member.class).setParameter("username", username).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
	
	public Member findByPhone(String phone) {
		if (StringUtils.isEmpty(phone)) {
			return null;
		}
		try {
			String jpql = "select members from Member members where members.phone=:phone";
			return entityManager.createQuery(jpql, Member.class).setParameter("phone", phone).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
	
	
	public List<Member> findListByEmail(String email) {
		if (StringUtils.isEmpty(email)) {
			return Collections.emptyList();
		}
		String jpql = "select members from Member members where members.email = :email";
		return entityManager.createQuery(jpql, Member.class).setParameter("email", email).getResultList();
	}

	public Page<Member> findPage(Member.RankingType rankingType, Pageable pageable) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Member> criteriaQuery = criteriaBuilder.createQuery(Member.class);
		Root<Member> root = criteriaQuery.from(Member.class);
		criteriaQuery.select(root);
		if (rankingType != null) {
			switch (rankingType) {
			case point:
				criteriaQuery.orderBy(criteriaBuilder.desc(root.get("point")));
				break;
			case godMoney:
				criteriaQuery.orderBy(criteriaBuilder.desc(root.get("godMoney")));
				break;
			case amount:
				criteriaQuery.orderBy(criteriaBuilder.desc(root.get("amount")));
				break;
			}
		}
		return super.findPage(criteriaQuery, pageable);
	}

	public Long registerMemberCount(Date beginDate, Date endDate) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Member> criteriaQuery = criteriaBuilder.createQuery(Member.class);
		Root<Member> root = criteriaQuery.from(Member.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		if (beginDate != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.greaterThanOrEqualTo(root.<Date> get("createDate"), beginDate));
		}
		if (endDate != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.lessThanOrEqualTo(root.<Date> get("createDate"), endDate));
		}
		criteriaQuery.where(restrictions);
		return super.count(criteriaQuery, null);
	}

	public void clearAttributeValue(MemberAttribute memberAttribute) {
		if (memberAttribute == null || memberAttribute.getType() == null || memberAttribute.getPropertyIndex() == null) {
			return;
		}

		String propertyName;
		switch (memberAttribute.getType()) {
		case text:
		case select:
		case checkbox:
			propertyName = Member.ATTRIBUTE_VALUE_PROPERTY_NAME_PREFIX + memberAttribute.getPropertyIndex();
			break;
		default:
			propertyName = String.valueOf(memberAttribute.getType());
			break;
		}
		String jpql = "update Member members set members." + propertyName + " = null";
		entityManager.createQuery(jpql).executeUpdate();
	}
	@Override
	public boolean userNamePhone(String userName, String phone) {
		if (StringUtils.isEmpty(userName) ||  StringUtils.isEmpty(phone)) {
			return false;
		}
		String jpql = "select count(*) from Member members where lower(members.username) = lower(:userName) and members.phone =:phone";
		Long count = entityManager.createQuery(jpql, Long.class).setParameter("userName", userName).setParameter("phone", phone).getSingleResult();
		return count > 0;
	}




}