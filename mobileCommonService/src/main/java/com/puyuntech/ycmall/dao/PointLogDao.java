package com.puyuntech.ycmall.dao;

import com.puyuntech.ycmall.Page;
import com.puyuntech.ycmall.Pageable;
import com.puyuntech.ycmall.entity.Member;
import com.puyuntech.ycmall.entity.PointLog;

/**
 * 
 * 积分记录 Dao . 
 * Created on 2015-9-13 下午4:04:47 
 * @author 施长成
 */
public interface PointLogDao extends BaseDao<PointLog, Long> {

	/**
	 * 
	 * 查找积分记录分页 .
	 * <br>
	 * author: 施长成
	 *   date: 2015-9-13 下午4:05:21
	 * @param member 会员 
	 * @param pageable 分页信息 
	 * @return 积分记录分页
	 */
	Page<PointLog> findPage(Member member, Pageable pageable);

	Long findByMemberNow(Member member);

	Long findCountByMemberNow(Member member);
	
}
