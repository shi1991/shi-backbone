package com.puyuntech.ycmall.dao;

import com.puyuntech.ycmall.entity.Admin;

/**
 *
 * Dao - 管理员 . 
 * Created on 2015-8-25 下午5:04:34 
 * @author 施长成
 */
public interface AdminDao extends BaseDao<Admin, Long> {

	/**
	 * 判断用户名是否存在
	 * 
	 * @param username
	 *            用户名(忽略大小写)
	 * @return 用户名是否存在
	 */
	boolean usernameExists(String username);

	/**
	 * 根据POS用户名查找管理员
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
	 * 判断手机号是否存在
	 * 
	 * @param phone
	 *            手机号(忽略大小写)
	 * @return 手机号是否存在
	 */
	boolean phoneExists(String phone);
}