package com.puyuntech.ycmall.dao;

import java.util.List;

import com.puyuntech.ycmall.Page;
import com.puyuntech.ycmall.Pageable;
import com.puyuntech.ycmall.entity.Member;
import com.puyuntech.ycmall.entity.Operator;
import com.puyuntech.ycmall.entity.PhoneNumber;

/**
 * 
 * DAO - 手机号码. Created on 2015-10-12 下午6:05:29
 * 
 * @author 王凯斌
 */
public interface PhoneNumberDao extends BaseDao<PhoneNumber, Long> {

	/**
	 * 
	 * 根据运营商分页查询 手机号 .
	 * <br>
	 * author: 施长成
	 *   date: 2015-10-19 下午6:19:20
	 * @param phoneNumer 手机号支持模糊查询
	 * @param operator 运营商
	 * @param pageable 分页
	 * @return
	 */
	Page<PhoneNumber> findPage(String phoneNumer , Operator operator, Pageable pageable);

	/**
	 * 
	 * 根据用户名 ， tokenId 查找该用户已经选择，并锁定的手机号 .
	 * <br>
	 * author: 施长成
	 *   date: 2015-10-20 上午10:58:56
	 * @param member 当前用户 可为空 
	 * @param key tokenId 唯一
	 * @return
	 */
	List<PhoneNumber> findListByMemberOrKey(Member member, String key);

	PhoneNumber findByNumber(String number);

	List<PhoneNumber> findByOperator(Operator operator, int count, String key);

}
