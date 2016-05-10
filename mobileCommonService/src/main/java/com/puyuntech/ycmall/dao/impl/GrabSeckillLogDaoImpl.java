package com.puyuntech.ycmall.dao.impl;

import java.util.List;

import javax.persistence.Query;

import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.puyuntech.ycmall.dao.GrabSeckillLogDao;
import com.puyuntech.ycmall.entity.GrabSeckillLog;
import com.puyuntech.ycmall.vo.ResultVO;

/**
 * 
 * 抢购记录Dao　. 
 * Created on 2015-10-20 下午6:33:57 
 * @author 严志森
 */
@Repository("grabSeckillLogDaoImpl")
public class GrabSeckillLogDaoImpl extends BaseDaoImpl<GrabSeckillLog, Long> implements GrabSeckillLogDao {
	@SuppressWarnings("unchecked")
	@Override
	public List<ResultVO> find(int  type,String id) {
		List<ResultVO> list=null;
		String sql=null;
		switch (type) {
		case 2:
			sql="SELECT b.f_image img,b.f_content content,b.f_title title,a.f_bonus bonus,a.exchange_state iduser,b.f_bonus_type type,a.acquire_time time FROM t_bonus_log a LEFT JOIN t_bonus b ON a.f_bonus=b.f_id" +
					" WHERE a.f_member=:memberId ORDER BY time DESC";
			break;
		case 3:
			sql="SELECT b.f_image img,b.f_name name,b.f_coupon_type couponType,a.f_create_date time,b.f_begin_date bdate,b.f_end_date edate,a.f_is_used isuserd FROM t_coupon_code a LEFT JOIN " +
					"t_coupon b ON a.f_coupon=b.f_id WHERE a.f_member=:memberId ORDER BY time DESC";
			break;
		default:
			sql="SELECT d.f_name name,d.f_image img,d.f_caption caption,d.f_price yuanjia,a.f_price qiangjia,b.f_create_date time FROM t_order_item a LEFT JOIN " +
					"t_product d ON d.f_id=a.f_product LEFT JOIN t_order b ON a.f_orders = b.f_id WHERE b.f_type=4 and b.f_member=:memberId ORDER BY time DESC";
			break;
		}
		//执行sql语句
		Query query = entityManager.createNativeQuery(sql);
		query.setParameter("memberId", id);
		//结果集格式化
		query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		list = query.getResultList();
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ResultVO> findByBonusId(String bonusId) {
		List<ResultVO> list=null;
		String sql="SELECT b.f_content content, a.exchange_state iduser,b.f_bonus_type type, b.f_credit credit FROM t_bonus_log a LEFT JOIN t_bonus b ON a.f_bonus = b.f_id " +
				"WHERE a.f_bonus=:bonusId";
		//执行sql语句
		Query query = entityManager.createNativeQuery(sql);
		query.setParameter("bonusId", bonusId);
		//结果集格式化
		query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		list = query.getResultList();
		return list;
	}
}
