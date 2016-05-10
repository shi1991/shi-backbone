package com.puyuntech.ycmall.service;

import java.util.List;

import com.puyuntech.ycmall.Filter;
import com.puyuntech.ycmall.Order;
import com.puyuntech.ycmall.entity.Tag;


/**
 * Service - 标签
 * 
 */
public interface TagService extends BaseService<Tag, Long> {

	/**
	 * 查找标签
	 * 
	 * @param type
	 *            类型
	 * @return 标签
	 */
	List<Tag> findList(Tag.Type type);

	/**
	 * 查找标签
	 * 
	 * @param count
	 *            数量
	 * @param filters
	 *            筛选
	 * @param orders
	 *            排序
	 * @param useCache
	 *            是否使用缓存
	 * @return 标签
	 */
	List<Tag> findList(Integer count, List<Filter> filters, List<Order> orders, boolean useCache);

}