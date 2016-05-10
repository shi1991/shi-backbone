package com.puyuntech.ycmall.dao;

import java.util.List;

import com.puyuntech.ycmall.entity.Member;
import com.puyuntech.ycmall.entity.Rebates;

/**
 * 
 *   返利 . 
 * Created on 2015-9-11 下午3:31:44 
 * @author 严志森
 */
public interface RebatesDao extends BaseDao<Rebates, Long> {
	/**
	 * 查找返利
	 * 
	 * @param member
	 *            会员
	 * @return 返利
	 */
	List<Rebates> findList(Member member);

}
