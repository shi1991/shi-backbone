package com.puyuntech.ycmall.service;

import java.util.List;

import com.puyuntech.ycmall.entity.Admin;

/***
 * 
 * @ClassName: AdminService
 * @Description: PC 运维端管理人员帐号管理和登录 
 * @date: 2015-8-12 下午3:12:32 
 * @author: 施长成
 */
public interface AdminService extends BaseService<Admin, Long> {

	/**
	 * 判断用户名是否存在
	 * 
	 * @param username
	 *            用户名(忽略大小写)
	 * @return 用户名是否存在
	 */
	boolean usernameExists(String username);
	
	/**
	 * 判断手机号是否存在
	 * 
	 * @param phone
	 *            手机号(忽略大小写)
	 * @return 手机号是否存在
	 */
	boolean phoneeExists(String phone);

	/**
	 * 根据用户名查找管理员
	 * 
	 * @param username
	 *            用户名(忽略大小写)
	 * @return 管理员，若不存在则返回null
	 */
	Admin findByPosUsername(String username);
	
	
	
	/**
	 * 根据工号查找管理员
	 * 
	 * @param jobNumber
	 *            用户名(忽略大小写)
	 * @return 管理员，若不存在则返回null
	 */
	Admin findByJobNumber(String jobNumber);

	/**
	 * 根据ID查找权限
	 * 
	 * @param id
	 *            ID
	 * @return 权限，若不存在则返回null
	 */
	List<String> findAuthorities(Long id);

	/**
	 * 获取登录令牌
	 * 
	 * @return 登录令牌
	 */
	String getLoginToken();
	
	
	

}