package com.puyuntech.ycmall.service;

import com.puyuntech.ycmall.Page;
import com.puyuntech.ycmall.Pageable;
import com.puyuntech.ycmall.entity.PointLog;
import com.puyuntech.ycmall.entity.Member;
/**
 * 
 * Service 积分 . 
 * Created on 2015-10-12 下午15:55:50 
 * @author 南金豆
 */
public interface PointLogService extends BaseService<PointLog, Long>{
	/**
	 * 查找积分分页
	 * 
	 * @return积分分页
	 */
	Page<PointLog> findPage(Member member, Pageable pageable);
	
	Long findByMemberNow(Member member);
	Long findCountByMemberNow(Member member);
}
