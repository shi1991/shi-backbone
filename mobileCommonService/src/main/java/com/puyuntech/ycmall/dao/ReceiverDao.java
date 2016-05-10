package com.puyuntech.ycmall.dao;

import com.puyuntech.ycmall.Page;
import com.puyuntech.ycmall.Pageable;
import com.puyuntech.ycmall.entity.Member;
import com.puyuntech.ycmall.entity.Receiver;

/**
 * 
 * 收货地址 dao. 
 * Created on 2015-9-22 上午10:34:44 
 * @author 严志森
 */
public interface ReceiverDao extends BaseDao<Receiver, Long> {


	/**
	 * 查找收货地址分页
	 * 
	 * @param member
	 *            会员
	 * @param pageable
	 *            分页信息
	 * @return 收货地址分页
	 */
	Page<Receiver> findPage(Member member, Pageable pageable);

	/**
	 * 设置默认收货地址
	 * 
	 * @param receiver
	 *            收货地址
	 */
	void setDefault(Receiver receiver);

}