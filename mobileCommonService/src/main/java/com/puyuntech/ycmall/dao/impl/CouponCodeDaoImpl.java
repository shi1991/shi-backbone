package com.puyuntech.ycmall.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.puyuntech.ycmall.dao.CouponCodeDao;
import com.puyuntech.ycmall.entity.CouponCode;

/**
 * 
 * 优惠券 dao. 
 * Created on 2015-9-22 上午10:35:32 
 * @author 严志森
 */
@Repository("couponCodeDaoImpl")
public class CouponCodeDaoImpl extends BaseDaoImpl<CouponCode, Long> implements CouponCodeDao {

	public void setDefault(CouponCode couponCode) {

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object> find(String id) {
		List<Object> list=null;
		List<Object> CouponCode=new ArrayList<Object>();
		//SQL语句
		String sql="SELECT a.f_id couponCodeId FROM t_coupon_code  a WHERE a.f_coupon =:id AND a.f_member IS NULL;";
		//执行sql语句
		Query query = entityManager.createNativeQuery(sql);
		query.setParameter("id", id);
		//结果集格式化
		query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		list= query.getResultList();
		if(list.size()==0){
			return CouponCode;	
		}
		CouponCode.add(list.get(0));
		return CouponCode;
	}

	

}