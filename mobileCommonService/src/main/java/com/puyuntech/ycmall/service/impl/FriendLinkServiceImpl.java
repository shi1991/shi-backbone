package com.puyuntech.ycmall.service.impl;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.puyuntech.ycmall.Filter;
import com.puyuntech.ycmall.Order;
import com.puyuntech.ycmall.dao.FriendLinkDao;
import com.puyuntech.ycmall.entity.FriendLink;
import com.puyuntech.ycmall.service.FriendLinkService;

/**
 * 
 * ServiceImpl - 友情链接. 
 * Created on 2015-8-25 下午4:03:21 
 * @author Liaozhen
 */
@Service("friendLinkServiceImpl")
public class FriendLinkServiceImpl extends BaseServiceImpl<FriendLink, Long> implements FriendLinkService {

	@Resource(name = "friendLinkDaoImpl")
	private FriendLinkDao friendLinkDao;

	@PersistenceContext
	protected EntityManager entityManager;
	
	@Transactional(readOnly = true)
	public List<FriendLink> findList(FriendLink.Type type) {
		return friendLinkDao.findList(type);
	}

	@Transactional(readOnly = true)
	@Cacheable(value = "friendLink", condition = "#useCache")
	public List<FriendLink> findList(Integer count, List<Filter> filters, List<Order> orders, boolean useCache) {
		return friendLinkDao.findList(null, count, filters, orders);
	}

	@Override
	@Transactional
	@CacheEvict(value = "friendLink", allEntries = true)
	public FriendLink save(FriendLink friendLink) {
		return super.save(friendLink);
	}

	@Override
	@Transactional
	@CacheEvict(value = "friendLink", allEntries = true)
	public FriendLink update(FriendLink friendLink) {
		return super.update(friendLink);
	}

	@Override
	@Transactional
	@CacheEvict(value = "friendLink", allEntries = true)
	public FriendLink update(FriendLink friendLink, String... ignoreProperties) {
		return super.update(friendLink, ignoreProperties);
	}

	@Override
	@Transactional
	@CacheEvict(value = "friendLink", allEntries = true)
	public void delete(Long id) {
		super.delete(id);
	}

	@Override
	@Transactional
	@CacheEvict(value = "friendLink", allEntries = true)
	public void delete(Long... ids) {
		super.delete(ids);
	}

	@Override
	@Transactional
	@CacheEvict(value = "friendLink", allEntries = true)
	public void delete(FriendLink friendLink) {
		super.delete(friendLink);
	}

	@Override
	public void saveOne(FriendLink link) {
		// TODO Auto-generated method stub
		System.out.println("hello");
		entityManager.merge(link);
		System.out.println("hello --------");
		
	}

}