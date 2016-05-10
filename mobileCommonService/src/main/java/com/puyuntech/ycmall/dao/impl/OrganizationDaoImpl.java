package com.puyuntech.ycmall.dao.impl;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.puyuntech.ycmall.Page;
import com.puyuntech.ycmall.dao.OrganizationDao;
import com.puyuntech.ycmall.entity.Area;
import com.puyuntech.ycmall.entity.Organization;
import com.puyuntech.ycmall.entity.Organization.Type;
import com.puyuntech.ycmall.vo.ResultVO;

/**
 * 
 *门店Dao . 
 * Created on 2015-9-24 下午4:29:17 
 * @author 施长成
 */
@Repository("organizationDaoImpl")
public class OrganizationDaoImpl extends BaseDaoImpl<Organization, Long> implements OrganizationDao {

	@Override
	public List<Organization> findList(Long organizationId, String date, Area area) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Organization> criteriaQuery = criteriaBuilder.createQuery(Organization.class);
		Root<Organization> root = criteriaQuery.from(Organization.class);
		criteriaQuery.select(root);
		
		Predicate conditions = criteriaBuilder.conjunction();
		if(null != organizationId){
			conditions = criteriaBuilder.and(conditions , criteriaBuilder.equal(root.get("id"), organizationId));
		}
		if(null != date){
			conditions = criteriaBuilder.and(conditions,criteriaBuilder.like( root.get("opening").as(String.class) , "%"+date+"%") );
		}
		if(null != area ){
			conditions = criteriaBuilder.and(conditions , criteriaBuilder.equal(root.get("area"), area ) );
		}
		criteriaQuery.where(conditions);
		
		TypedQuery<Organization> typeQuery = entityManager.createQuery(criteriaQuery);
		List<Organization> organizations = typeQuery.getResultList();
		
		return organizations;
	}

	@Override
	public Page<Organization> findPage() {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Organization> criteriaQuery = criteriaBuilder.createQuery(Organization.class);
		Root<Organization> root = criteriaQuery.from(Organization.class);
		criteriaQuery.select(root);
		return super.findPage(criteriaQuery, null);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ResultVO> findStaffByOrganizationId(String organizationId) {
		List<ResultVO> list=null;
		String sql="SELECT a.f_image img,a.f_office office,a.f_name name FROM t_admin a LEFT JOIN t_organization b ON a.f_organization=b.f_id WHERE a.f_organization=:organizationId and f_is_enabled=1 order by f_office asc";
		
		//执行sql语句
		Query query = entityManager.createNativeQuery(sql);
		query.setParameter("organizationId", organizationId);
		
		//TODO 只查最大数量为4
		query.setMaxResults( 4 );
		
		//结果集格式化
		query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		list = query.getResultList();
		return list;
	}

	@Override
	public List<ResultVO> findAddressByOrganizationId(String organizationId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Organization> findOther(Long organizationId) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Organization> criteriaQuery = criteriaBuilder.createQuery(Organization.class);
		Root<Organization> root = criteriaQuery.from(Organization.class);
		criteriaQuery.select(root);
		
		Predicate conditions = criteriaBuilder.conjunction();
		if(null != organizationId){
			conditions = criteriaBuilder.and(conditions , criteriaBuilder.notEqual(root.get("id"), organizationId));
		}
		conditions = criteriaBuilder.and(conditions , criteriaBuilder.notEqual(root.get("id"), 1l));
		criteriaQuery.where(conditions);
		
		TypedQuery<Organization> typeQuery = entityManager.createQuery(criteriaQuery);
		List<Organization> organizations = typeQuery.getResultList();
		return organizations;
	}

	@Override
	public List<Organization> findBistribution() {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Organization> criteriaQuery = criteriaBuilder.createQuery(Organization.class);
		Root<Organization> root = criteriaQuery.from(Organization.class);
		criteriaQuery.select(root);
		
		Predicate conditions = criteriaBuilder.conjunction();
		conditions = criteriaBuilder.and(conditions , criteriaBuilder.notEqual(root.get("id"), 1l));
		criteriaQuery.where(conditions);
		
		TypedQuery<Organization> typeQuery = entityManager.createQuery(criteriaQuery);
		List<Organization> organizations = typeQuery.getResultList();
		return organizations;
	}

	@Override
	public List<Organization> findZiTi(int type) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Organization> criteriaQuery = criteriaBuilder.createQuery(Organization.class);
		Root<Organization> root = criteriaQuery.from(Organization.class);
		criteriaQuery.select(root);
		
		Predicate conditions = criteriaBuilder.conjunction();
		switch (type) {
		//维修点  线上
		case 1:
			conditions = criteriaBuilder.and(conditions , criteriaBuilder.equal(root.get("id"), 100));
			break;
		
		//自提
		case 2:
			conditions = criteriaBuilder.and(conditions , criteriaBuilder.like(root.get("types").as(String.class), "%"+ Type.store +"%"));
			conditions = criteriaBuilder.and(conditions , criteriaBuilder.like(root.get("types").as(String.class), "%"+ Type.warehouse +"%"));
			break;
		//维修点  线下
		case 3:
			conditions = criteriaBuilder.and(conditions , criteriaBuilder.like(root.get("types").as(String.class), "%"+ Type.repairShop +"%"));
			break;
		default:
			break;
		}
		criteriaQuery.where(conditions);
		
		TypedQuery<Organization> typeQuery = entityManager.createQuery(criteriaQuery);
		List<Organization> organizations = typeQuery.getResultList();
		return organizations;
	}

	@Override
	public Organization findByName(String name) {
		if(StringUtils.isEmpty(name)){
			return null;
		}
		try{
			String jpql="select organizations from  Organization organizations where organizations.name=:name";
			return entityManager.createQuery(jpql,Organization.class).setParameter("name", name).getSingleResult();
		}catch(NoResultException e){
			return null;
		}
	}

}
