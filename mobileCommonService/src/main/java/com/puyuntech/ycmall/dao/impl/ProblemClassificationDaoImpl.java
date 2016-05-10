
package com.puyuntech.ycmall.dao.impl;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.TypedQuery;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.builder.CompareToBuilder;
import org.springframework.stereotype.Repository;

import com.puyuntech.ycmall.dao.ProblemClassificationDao;
import com.puyuntech.ycmall.entity.ProblemClassification;

/**
 * 
 * Dao - 问题分类. 
 * Created on 2015-11-20 下午1:19:34 
 * @author 王凯斌
 */
@Repository("problemClassificationDaoImpl")
public class ProblemClassificationDaoImpl extends BaseDaoImpl<ProblemClassification, Long> implements ProblemClassificationDao {

	public List<ProblemClassification> findRoots(Integer count) {
		
		/**
		 * 定义jpql语句并且创建查询
		 */
		String jpql = "select problemClassification from ProblemClassification problemClassification where problemClassification.parent is null order by problemClassification.order asc";
		TypedQuery<ProblemClassification> query = entityManager.createQuery(jpql, ProblemClassification.class);
		
		/**
		 * 设置结果数据上限
		 */
		if (count != null) {
			query.setMaxResults(count);
		}
		return query.getResultList();
	}

	public List<ProblemClassification> findParents(ProblemClassification problemClassification, boolean recursive, Integer count) {
		
		/**
		 * 判断商品分类以及父类不为空
		 */
		if (problemClassification == null || problemClassification.getParent() == null) {
			return Collections.emptyList();
		}
		
		/**
		 * 创建查询，定义jpql语句并进行条件定义
		 */
		TypedQuery<ProblemClassification> query;
		if (recursive) {
			String jpql = "select problemClassification from ProblemClassification problemClassification where problemClassification.id in (:ids) order by problemClassification.grade asc";
			query = entityManager.createQuery(jpql, ProblemClassification.class).setParameter("ids", Arrays.asList(problemClassification.getParentIds()));
		} else {
			String jpql = "select problemClassification from ProblemClassification problemClassification where problemClassification = :problemClassification";
			query = entityManager.createQuery(jpql, ProblemClassification.class).setParameter("problemClassification", problemClassification.getParent());
		}
		
		/**
		 * 设置结果数量上限
		 */
		if (count != null) {
			query.setMaxResults(count);
		}
		return query.getResultList();
	}

	public List<ProblemClassification> findChildren(ProblemClassification problemClassification, boolean recursive, Integer count) {
		
		/**
		 * 创建查询
		 */
		TypedQuery<ProblemClassification> query;
		
		/**
		 * 根据是否递归选择是查询所有子分类还是下一级子分类
		 */
		if (recursive) {
			if (problemClassification != null) {
				String jpql = "select problemClassification from ProblemClassification problemClassification where problemClassification.treePath like :treePath order by problemClassification.grade asc, problemClassification.order asc";
				query = entityManager.createQuery(jpql, ProblemClassification.class).setParameter("treePath", "%" + ProblemClassification.TREE_PATH_SEPARATOR + problemClassification.getId() + ProblemClassification.TREE_PATH_SEPARATOR + "%");
			} else {
				String jpql = "select problemClassification from ProblemClassification problemClassification order by problemClassification.grade asc, problemClassification.order asc";
				query = entityManager.createQuery(jpql, ProblemClassification.class);
			}
			if (count != null) {
				query.setMaxResults(count);
			}
			List<ProblemClassification> result = query.getResultList();
			sort(result);
			return result;
		} else {
			String jpql = "select problemClassification from ProblemClassification problemClassification where problemClassification.parent = :parent order by problemClassification.order asc";
			query = entityManager.createQuery(jpql, ProblemClassification.class).setParameter("parent", problemClassification);
			if (count != null) {
				query.setMaxResults(count);
			}
			return query.getResultList();
		}
	}

	/**
	 * 排序商品分类
	 * 
	 * @param productCategories
	 *            商品分类
	 */
	private void sort(List<ProblemClassification> problemClassifications) {
		
		/**
		 * 判断商品分类是否为空
		 */
		if (CollectionUtils.isEmpty(problemClassifications)) {
			return;
		}
		
		/**
		 * 定义排序索引
		 */
		final Map<Long, Integer> orderMap = new HashMap<Long, Integer>();
		
		/**
		 * 商品分类id存入索引
		 */
		for (ProblemClassification problemClassification : problemClassifications) {
			orderMap.put(problemClassification.getId(), problemClassification.getOrder());
		}
		
		/**
		 * 两两比较排序
		 */
		Collections.sort(problemClassifications, new Comparator<ProblemClassification>() {
			@Override
			public int compare(ProblemClassification problemClassification1, ProblemClassification problemClassification2) {
				Long[] ids1 = (Long[]) ArrayUtils.add(problemClassification1.getParentIds(), problemClassification1.getId());
				Long[] ids2 = (Long[]) ArrayUtils.add(problemClassification2.getParentIds(), problemClassification2.getId());
				Iterator<Long> iterator1 = Arrays.asList(ids1).iterator();
				Iterator<Long> iterator2 = Arrays.asList(ids2).iterator();
				CompareToBuilder compareToBuilder = new CompareToBuilder();
				while (iterator1.hasNext() && iterator2.hasNext()) {
					Long id1 = iterator1.next();
					Long id2 = iterator2.next();
					Integer order1 = orderMap.get(id1);
					Integer order2 = orderMap.get(id2);
					compareToBuilder.append(order1, order2).append(id1, id2);
					if (!iterator1.hasNext() || !iterator2.hasNext()) {
						compareToBuilder.append(problemClassification1.getGrade(), problemClassification2.getGrade());
					}
				}
				return compareToBuilder.toComparison();
			}
		});
	}

	@Override
	public List<ProblemClassification> findChildrenByGrade(
			ProblemClassification problemClassification, boolean recursive,
			Integer count,int grade) {
		/**
		 * 创建查询
		 */
		TypedQuery<ProblemClassification> query;
		
		/**
		 * 根据是否递归选择是查询所有子分类还是下一级子分类
		 */
		if (recursive) {
			if (problemClassification != null) {
				String jpql = "select problemClassification from ProblemClassification problemClassification where problemClassification.treePath like :treePath and problemClassification.grade=:grade order by problemClassification.grade asc, problemClassification.order asc";
				query = entityManager.createQuery(jpql, ProblemClassification.class).setParameter("treePath", "%" + ProblemClassification.TREE_PATH_SEPARATOR + problemClassification.getId() + ProblemClassification.TREE_PATH_SEPARATOR + "%").setParameter("grade", grade);
			} else {
				String jpql = "select problemClassification from ProblemClassification problemClassification where problemClassification.grade=:grade order by problemClassification.grade asc, problemClassification.order asc";
				query = entityManager.createQuery(jpql, ProblemClassification.class).setParameter("grade", grade);
			}
			if (count != null) {
				query.setMaxResults(count);
			}
			List<ProblemClassification> result = query.getResultList();
			sort(result);
			return result;
		} else {
			String jpql = "select problemClassification from ProblemClassification problemClassification where problemClassification.parent = :parent and problemClassification.grade=:grade order by problemClassification.order asc";
			query = entityManager.createQuery(jpql, ProblemClassification.class).setParameter("parent", problemClassification).setParameter("grade", grade);
			if (count != null) {
				query.setMaxResults(count);
			}
			return query.getResultList();
		}
	}

}