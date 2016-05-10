package com.puyuntech.ycmall.dao;

import com.puyuntech.ycmall.Page;

import com.puyuntech.ycmall.Pageable;

import com.puyuntech.ycmall.entity.GodMoneyLog;
import com.puyuntech.ycmall.entity.Member;

/**
 * 
 * 神币操作记录 Dao  . 
 * Created on 2015-10-30 下午3:52:09 
 * @author 施长成
 */
public interface GodMoneyLogDao extends BaseDao<GodMoneyLog, Long>{
	
	/**
	 * 查找神币分页
	 * 
	 * @return神币分页
	 */
	Page<GodMoneyLog> findPage( Member member,Pageable pageable);
	
}
