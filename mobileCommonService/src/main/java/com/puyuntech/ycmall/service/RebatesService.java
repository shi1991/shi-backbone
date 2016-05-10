package com.puyuntech.ycmall.service;

import java.util.List;

import com.puyuntech.ycmall.entity.Member;
import com.puyuntech.ycmall.entity.Product;
import com.puyuntech.ycmall.entity.Rebates;

/**
 * 
 * 返利 Service . 
 * Created on 2015-10-19 下午7:14:20 
 * @author 严志森
 */
public interface RebatesService extends BaseService<Rebates, Long> {
	
	/**
	 * 查找返利
	 * @param member
	 *            会员
	 * @return 返利
	 */
	List<Rebates> findList(Member member);

//	String findProduct(Rebates rebates2);
}
