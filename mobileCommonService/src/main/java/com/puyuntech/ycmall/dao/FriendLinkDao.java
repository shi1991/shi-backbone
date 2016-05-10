
package com.puyuntech.ycmall.dao;

import java.util.List;

import com.puyuntech.ycmall.entity.FriendLink;

/**
 * 
 * Dao - 友情链接. 
 * Created on 2015-8-25 下午4:08:00 
 * @author Liaozhen
 */
public interface FriendLinkDao extends BaseDao<FriendLink, Long> {

	/**
	 * 查找友情链接
	 * 
	 * @param type
	 *            类型
	 * @return 友情链接
	 */
	List<FriendLink> findList(FriendLink.Type type);

}