package com.puyuntech.ycmall.service;

import java.util.List;

import com.puyuntech.ycmall.Page;
import com.puyuntech.ycmall.Pageable;
import com.puyuntech.ycmall.entity.BonusLog;
import com.puyuntech.ycmall.entity.Member;
/**
 * 
 * Service - 红包记录. 
 * Created on 2015-11-10 上午9:51:49 
 * @author 南金豆
 */
public interface BonusLogService extends BaseService<BonusLog, Long>{

	/**
	 * 查找优惠码分页
	 * 
	 * @param member
	 *            会员
	 * @param pageable
	 *            分页信息
	 * @return 优惠码分页
	 */
	Page<BonusLog> findPage(Member member,Pageable pageable);
	
	/**
	 * 
	 * 查找没有被抢到的红包列表
	 * author: 南金豆
	 *   date: 2015-11-10 下午4:06:23
	 * @param id
	 * @return
	 */
	List<Object> find(String id);
}
