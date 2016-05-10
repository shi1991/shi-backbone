package com.puyuntech.ycmall.dao;

import java.util.List;

import com.puyuntech.ycmall.Page;
import com.puyuntech.ycmall.Pageable;
import com.puyuntech.ycmall.entity.BonusLog;
import com.puyuntech.ycmall.entity.Member;
/**
 * 
 *Dao - 红包. 
 * Created on 2015-11-10 下午1:20:13 
 * @author 南金豆
 */
public interface BonusLogDao extends BaseDao<BonusLog, Long>{
	/**
	 * 查找红包记录分页
	 * 
	 * @param member
	 *            会员
	 * @param pageable
	 *            分页信息
	 * @return 红包记录分页
	 */
	Page<BonusLog> findPage(Member member, Pageable pageable);

	/**
	 * 
	 *  查找没有被抢到的红包列表
	 * author: 南金豆
	 *   date: 2015-11-10 下午4:06:23
	 * @param member
	 * @return
	 */
	List<Object> find(String id);
}
