package com.puyuntech.ycmall.dao.impl;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.puyuntech.ycmall.Page;
import com.puyuntech.ycmall.Pageable;
import com.puyuntech.ycmall.dao.BonusLogDao;
import com.puyuntech.ycmall.entity.BonusLog;
import com.puyuntech.ycmall.entity.Member;


/**
 * 
 *  DaoImpl - 红包. 
 * Created on 2015-11-10 下午1:24:43 
 * @author 南金豆
 */
@Repository("bonusLogDaoImpl")
public class BonusLogDaoImpl extends BaseDaoImpl<BonusLog, Long> implements BonusLogDao{

	@Override
	public Page<BonusLog> findPage(Member member, Pageable pageable) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<BonusLog> criteriaQuery = criteriaBuilder.createQuery(BonusLog.class);
		Root<BonusLog> root = criteriaQuery.from(BonusLog.class);
		criteriaQuery.select(root);
		if(null != member){
			criteriaQuery.where(criteriaBuilder.equal(root.get("member"), member));
		}
		return super.findPage(criteriaQuery, pageable);
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<Object> find(String id) {
		List<Object> list=null;
		List<Object> bonusLog=new ArrayList<Object>();
		//SQL语句
		String sql="SELECT a.f_id bonusLogId FROM t_bonus_log a WHERE a.f_bonus =:id  AND a.f_member IS NULL;";
		//执行sql语句
		Query query = entityManager.createNativeQuery(sql);
		query.setParameter("id", id);
		//结果集格式化
		query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		list= query.getResultList();
		if(list.size()==0){
			return bonusLog;	
		}
		bonusLog.add(list.get(0));
		return bonusLog;
	}

	

}
