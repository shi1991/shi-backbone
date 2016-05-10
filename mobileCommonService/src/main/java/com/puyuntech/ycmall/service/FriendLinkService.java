package com.puyuntech.ycmall.service;

import java.util.List;

import com.puyuntech.ycmall.Filter;
import com.puyuntech.ycmall.Order;
import com.puyuntech.ycmall.entity.FriendLink;

/**
 * 
 * Service - 友情链接. 
 * Created on 2015-8-25 下午4:04:30 
 * @author Liaozhen
 */
public interface FriendLinkService extends BaseService<FriendLink, Long> {

	/**
	 * 查找友情链接
	 * 
	 * @param type
	 *            类型
	 * @return 友情链接
	 */
	List<FriendLink> findList(FriendLink.Type type);

	/**
	 * 查找友情链接
	 * 
	 * @param count
	 *            数量
	 * @param filters
	 *            筛选
	 * @param orders
	 *            排序
	 * @param useCache
	 *            是否使用缓存
	 * @return 友情链接
	 */
	List<FriendLink> findList(Integer count, List<Filter> filters, List<Order> orders, boolean useCache);

	void saveOne(FriendLink link);

}