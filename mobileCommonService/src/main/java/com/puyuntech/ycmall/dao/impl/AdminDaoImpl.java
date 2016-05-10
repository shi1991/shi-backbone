package com.puyuntech.ycmall.dao.impl;

import javax.persistence.NoResultException;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.puyuntech.ycmall.dao.AdminDao;
import com.puyuntech.ycmall.entity.Admin;

/**
 * 
 * Dao - 管理员. 
 * Created on 2015-8-25 下午5:07:47 
 * @author 施长成
 */
@Repository("adminDaoImpl")
public class AdminDaoImpl extends BaseDaoImpl<Admin, Long> implements AdminDao {

	public boolean usernameExists(String username) {
		if (StringUtils.isEmpty(username)) {
			return false;
		}
		String jpql = "select count(*) from Admin admin where lower(admin.webUsername) = lower(:username)";
		Long count = entityManager.createQuery(jpql, Long.class).setParameter("username", username).getSingleResult();
		return count > 0;
	}
	
	@Override
	public boolean phoneExists(String phone) {
		if (StringUtils.isEmpty(phone)) {
			return false;
		}
		String jpql = "select count(*) from Admin admin where lower(admin.phone) = lower(:phone)";
		Long count = entityManager.createQuery(jpql, Long.class).setParameter("phone", phone).getSingleResult();
		return count > 0;
	}
	
	public Admin findByPosUsername(String username) {
		if (StringUtils.isEmpty(username)) {
			return null;
		}
		try {
			String jpql = "select admin from Admin admin where lower(admin.posUsername) = lower(:username)";
			return entityManager.createQuery(jpql, Admin.class).setParameter("username", username).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public Admin findByJobNumber(String jobNumber) {
		if (StringUtils.isEmpty(jobNumber)) {
			return null;
		}
		try {
			String jpql = "select admin from Admin admin where lower(admin.jobNumber) = lower(:jobNumber)";
			return entityManager.createQuery(jpql, Admin.class).setParameter("jobNumber", jobNumber).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}



}