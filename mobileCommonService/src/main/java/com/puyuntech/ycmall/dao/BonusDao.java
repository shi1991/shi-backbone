package com.puyuntech.ycmall.dao;

import com.puyuntech.ycmall.Page;
import com.puyuntech.ycmall.Pageable;
import com.puyuntech.ycmall.entity.BonusEntity;
import com.puyuntech.ycmall.entity.Member;


/**
 * 
 * 红包操作记录 Dao  . 
 * Created on 2015-11-05 下午3:52:09 
 * @author 南金豆
 */
public interface BonusDao extends BaseDao<BonusEntity, Long>{

	/**
	 * 查找红包分页
	 * 
	 * @return红包分页
	 */
	Page<BonusEntity> findPage( Member member,Pageable pageable);
}
