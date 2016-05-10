package com.puyuntech.ycmall.dao;

import java.util.List;

import com.puyuntech.ycmall.entity.ProblemClassification;

/**
 * 
 * Dao - 问题分类. Created on 2015-11-20 上午11:35:45
 * 
 * @author 严志森
 */
public interface ProblemClassificationDao extends BaseDao<ProblemClassification, Long> {

	/**
	 * 
	 * 查找顶级问题分类.
	 * 
	 * author: 严志森 date: 2015-11-20 上午11:36:04
	 * 
	 * @param count
	 *            数量
	 * @return 顶级问题分类
	 */
	List<ProblemClassification> findRoots(Integer count);

	/**
	 * 
	 * 查找上级问题分类.
	 * 
	 * author: 严志森 date: 2015-11-20 上午11:36:40
	 * 
	 * @param productCategory
	 *            问题分类
	 * @param recursive
	 *            是否递归
	 * @param count
	 *            数量
	 * @return 上级问题分类
	 */
	List<ProblemClassification> findParents(ProblemClassification problemClassification,
			boolean recursive, Integer count);

	/**
	 * 
	 * 查找下级问题分类.
	 * 
	 * author: 严志森 date: 2015-11-20 上午11:37:15
	 * 
	 * @param productCategory
	 *            问题分类
	 * @param recursive
	 *            是否递归
	 * @param count
	 *            数量
	 * @return 下级问题分类
	 */
	List<ProblemClassification> findChildren(ProblemClassification problemClassification,
			boolean recursive, Integer count);

	List<ProblemClassification> findChildrenByGrade(
			ProblemClassification problemClassification, boolean recursive,
			Integer count,int grade);

}