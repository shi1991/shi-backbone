
package com.puyuntech.ycmall.service;

import java.util.List;

import com.puyuntech.ycmall.entity.ProblemClassification;


/**
 * 
 * Service - 问题分类. 
 * Created on 2015-11-20 上午11:13:24 
 * @author 严志森
 */
public interface ProblemClassificationService extends BaseService<ProblemClassification, Long> {

	/**
	 * 查找顶级问题分类
	 * 
	 * @return 顶级问题分类
	 */
	List<ProblemClassification> findRoots();

	/**
	 * 查找顶级问题分类
	 * 
	 * @param count
	 *            数量
	 * @return 顶级问题分类
	 */
	List<ProblemClassification> findRoots(Integer count);

	/**
	 * 查找顶级问题分类
	 * 
	 * @param count
	 *            数量
	 * @param useCache
	 *            是否使用缓存
	 * @return 顶级问题分类
	 */
	List<ProblemClassification> findRoots(Integer count, boolean useCache);

	/**
	 * 查找上级问题分类
	 * 
	 * @param problemClassification
	 *            问题分类
	 * @param recursive
	 *            是否递归
	 * @param count
	 *            数量
	 * @return 上级问题分类
	 */
	List<ProblemClassification> findParents(ProblemClassification problemClassification, boolean recursive, Integer count);

	/**
	 * 查找上级问题分类
	 * 
	 * @param problemClassificationId
	 *            问题分类ID
	 * @param recursive
	 *            是否递归
	 * @param count
	 *            数量
	 * @param useCache
	 *            是否使用缓存
	 * @return 上级问题分类
	 */
	List<ProblemClassification> findParents(Long problemClassificationId, boolean recursive, Integer count, boolean useCache);

	/**
	 * 查找问题分类树
	 * 
	 * @return 问题分类树
	 */
	List<ProblemClassification> findTree();

	/**
	 * 查找下级问题分类
	 * 
	 * @param problemClassification
	 *            问题分类
	 * @param recursive
	 *            是否递归
	 * @param count
	 *            数量
	 * @return 下级问题分类
	 */
	List<ProblemClassification> findChildren(ProblemClassification problemClassification, boolean recursive, Integer count);

	/**
	 * 查找下级问题分类
	 * 
	 * @param problemClassificationId
	 *            问题分类ID
	 * @param recursive
	 *            是否递归
	 * @param count
	 *            数量
	 * @param useCache
	 *            是否使用缓存
	 * @return 下级问题分类
	 */
	List<ProblemClassification> findChildren(Long problemClassificationId, boolean recursive, Integer count, boolean useCache);
	
	/**
	 * 
	 * 根据级别查找问题分类.
	 * author: 严志森
	 *   date: 2015-11-22 下午5:29:00
	 * @param grade
	 * @return
	 */
	List<ProblemClassification> findByOrder(int grade);
	
	/**
	 * 查找下级问题分类
	 * 
	 * @param problemClassificationId
	 *            问题分类ID
	 * @param recursive
	 *            是否递归
	 * @param count
	 *            数量
	 * @param useCache
	 *            是否使用缓存
	 * @return 下级问题分类
	 */
	List<ProblemClassification> findChildrenByGrade(Long problemClassificationId, boolean recursive,
			Integer count, boolean useCache,int grade);

}