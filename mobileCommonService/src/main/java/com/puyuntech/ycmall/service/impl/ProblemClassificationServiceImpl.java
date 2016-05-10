package com.puyuntech.ycmall.service.impl;

import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.puyuntech.ycmall.dao.ProblemClassificationDao;
import com.puyuntech.ycmall.entity.ProblemClassification;
import com.puyuntech.ycmall.service.ProblemClassificationService;

/**
 * 
 * Service - 问题分类. Created on 2015-11-20 上午11:19:24
 * 
 * @author 严志森
 */
@Service("problemClassificationServiceImpl")
public class ProblemClassificationServiceImpl extends
		BaseServiceImpl<ProblemClassification, Long> implements
		ProblemClassificationService {

	/**
	 * 引入dao层对象
	 */
	@Resource(name = "problemClassificationDaoImpl")
	private ProblemClassificationDao problemClassificationDao;

	@Transactional(readOnly = true)
	public List<ProblemClassification> findRoots() {
		return problemClassificationDao.findRoots(null);
	}

	@Transactional(readOnly = true)
	public List<ProblemClassification> findRoots(Integer count) {
		return problemClassificationDao.findRoots(count);
	}

	@Transactional(readOnly = true)
	@Cacheable(value = "problemClassification", condition = "#useCache")
	public List<ProblemClassification> findRoots(Integer count, boolean useCache) {
		return problemClassificationDao.findRoots(count);
	}

	@Transactional(readOnly = true)
	public List<ProblemClassification> findParents(ProblemClassification problemClassification,
			boolean recursive, Integer count) {
		return problemClassificationDao
				.findParents(problemClassification, recursive, count);
	}

	@Transactional(readOnly = true)
	@Cacheable(value = "problemClassification", condition = "#useCache")
	public List<ProblemClassification> findParents(Long problemClassificationId,
			boolean recursive, Integer count, boolean useCache) {

		/**
		 * 查询问题分类
		 */
		ProblemClassification problemClassification = problemClassificationDao
				.find(problemClassificationId);

		/**
		 * 判断是否为空
		 */
		if (problemClassificationId != null && problemClassification == null) {
			return Collections.emptyList();
		}
		return problemClassificationDao
				.findParents(problemClassification, recursive, count);
	}

	@Transactional(readOnly = true)
	public List<ProblemClassification> findTree() {
		return problemClassificationDao.findChildren(null, true, null);
	}

	@Transactional(readOnly = true)
	public List<ProblemClassification> findChildren(ProblemClassification problemClassification,
			boolean recursive, Integer count) {
		return problemClassificationDao.findChildren(problemClassification, recursive,
				count);
	}

	@Transactional(readOnly = true)
	@Cacheable(value = "problemClassification", condition = "#useCache")
	public List<ProblemClassification> findChildren(Long problemClassificationId,
			boolean recursive, Integer count, boolean useCache) {

		/**
		 * 查找问题分类
		 */
		ProblemClassification problemClassification = problemClassificationDao
				.find(problemClassificationId);

		/**
		 * 判断问题分类是否为空
		 */
		if (problemClassificationId != null && problemClassification == null) {
			return Collections.emptyList();
		}
		return problemClassificationDao.findChildren(problemClassification, recursive,
				count);
	}

	@Override
	@Transactional
	@CacheEvict(value = { "goods", "problemClassification" }, allEntries = true)
	public ProblemClassification save(ProblemClassification problemClassification) {

		/**
		 * 判断是否为空
		 */
		Assert.notNull(problemClassification);

		/**
		 * 生成问题分类的部分属性
		 */
		setValue(problemClassification);
		return super.save(problemClassification);
	}

	@Override
	@Transactional
	@CacheEvict(value = { "goods", "problemClassification" }, allEntries = true)
	public ProblemClassification update(ProblemClassification problemClassification) {

		/**
		 * 判断是否为空
		 */
		Assert.notNull(problemClassification);

		/**
		 * 生成问题分类的部分属性
		 */
		setValue(problemClassification);

		/**
		 * 生成问题分类下级分类的部分属性
		 */
		for (ProblemClassification children : problemClassificationDao.findChildren(
				problemClassification, true, null)) {
			setValue(children);
		}
		return super.update(problemClassification);
	}

	@Override
	@Transactional
	@CacheEvict(value = { "goods", "problemClassification" }, allEntries = true)
	public ProblemClassification update(ProblemClassification problemClassification,
			String... ignoreProperties) {
		return super.update(problemClassification, ignoreProperties);
	}

	@Override
	@Transactional
	@CacheEvict(value = { "goods", "problemClassification" }, allEntries = true)
	public void delete(Long id) {
		super.delete(id);
	}

	@Override
	@Transactional
	@CacheEvict(value = { "goods", "problemClassification" }, allEntries = true)
	public void delete(Long... ids) {
		super.delete(ids);
	}

	@Override
	@Transactional
	@CacheEvict(value = { "goods", "problemClassification" }, allEntries = true)
	public void delete(ProblemClassification problemClassification) {
		super.delete(problemClassification);
	}

	/**
	 * 设置值
	 * 
	 * @param problemClassification
	 *            问题分类
	 */
	private void setValue(ProblemClassification problemClassification) {

		/**
		 * 判断问题是否为空
		 */
		if (problemClassification == null) {
			return;
		}

		/**
		 * 获得问题分类上级分类
		 */
		ProblemClassification parent = problemClassification.getParent();

		/**
		 * 根据问题分类是否有上级分类来计算问题分类问题树路径属性
		 */
		if (parent != null) {
			problemClassification.setTreePath(parent.getTreePath() + parent.getId()
					+ ProblemClassification.TREE_PATH_SEPARATOR);
		} else {
			problemClassification.setTreePath(ProblemClassification.TREE_PATH_SEPARATOR);
		}

		/**
		 * 设置问题分类层级
		 */
		problemClassification.setGrade(problemClassification.getParentIds().length);
	}

	@Override
	public List<ProblemClassification> findByOrder(int grade) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ProblemClassification> findChildrenByGrade(Long problemClassificationId, boolean recursive,
			Integer count, boolean useCache,int grade) {
		/**
		 * 查找问题分类
		 */
		ProblemClassification problemClassification = problemClassificationDao
				.find(problemClassificationId);

		/**
		 * 判断问题分类是否为空
		 */
		if (problemClassificationId != null && problemClassification == null) {
			return Collections.emptyList();
		}
		return problemClassificationDao.findChildrenByGrade(problemClassification, recursive,
				count,grade);
	}

}