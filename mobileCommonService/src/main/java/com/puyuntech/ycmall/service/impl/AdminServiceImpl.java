package com.puyuntech.ycmall.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.puyuntech.ycmall.dao.AdminDao;
import com.puyuntech.ycmall.entity.Admin;
import com.puyuntech.ycmall.entity.Role;
import com.puyuntech.ycmall.service.AdminService;

/**
 * 
 * Service - 管理员. 
 * Created on 2015-8-25 下午5:19:26 
 * @author 施长成
 */
@Service("adminServiceImpl")
public class AdminServiceImpl extends BaseServiceImpl<Admin, Long> implements AdminService {

	@Resource(name = "adminDaoImpl")
	private AdminDao adminDao;

	@Transactional(readOnly = true)
	public boolean usernameExists(String username) {
		return adminDao.usernameExists(username);
	}

	
	
	@Transactional(readOnly = true)
	public boolean phoneeExists(String phone) {
		return adminDao.phoneExists(phone);
	}   
	
	
	@Transactional(readOnly = true)
	public Admin findByPosUsername(String username) {
		return adminDao.findByPosUsername(username);
	}

	
	@Transactional(readOnly = true)
	public List<String> findAuthorities(Long id) {
		List<String> authorities = new ArrayList<String>();
		Admin admin = adminDao.find(id);
		if (admin != null && admin.getRoles() != null) {
			for (Role role : admin.getRoles()) {
				if (role != null && role.getAuthorities() != null) {
					authorities.addAll(role.getAuthorities());
				}
			}
		}
		return authorities;
	}

	



	
	@Transactional(readOnly = true)
	@Cacheable(value = "loginToken")
	public String getLoginToken() {
		return DigestUtils.md5Hex(UUID.randomUUID() + RandomStringUtils.randomAlphabetic(30));
	}

	@Override
	@Transactional
	@CacheEvict(value = "authorization", allEntries = true)
	public Admin save(Admin admin) {
		return super.save(admin);
	}

	@Override
	@Transactional
	@CacheEvict(value = "authorization", allEntries = true)
	public Admin update(Admin admin) {
		return super.update(admin);
	}

	@Override
	@Transactional
	@CacheEvict(value = "authorization", allEntries = true)
	public Admin update(Admin admin, String... ignoreProperties) {
		return super.update(admin, ignoreProperties);
	}

	@Override
	@Transactional
	@CacheEvict(value = "authorization", allEntries = true)
	public void delete(Long id) {
		super.delete(id);
	}

	@Override
	@Transactional
	@CacheEvict(value = "authorization", allEntries = true)
	public void delete(Long... ids) {
		super.delete(ids);
	}

	@Override
	@Transactional
	@CacheEvict(value = "authorization", allEntries = true)
	public void delete(Admin admin) {
		super.delete(admin);
	}

	@Override
	public Admin findByJobNumber(String jobNumber) {
		return adminDao.findByJobNumber(jobNumber);
	}




}