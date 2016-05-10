package com.puyuntech.ycmall.service;

import java.util.List;
import java.util.Map;

import com.puyuntech.ycmall.Page;
import com.puyuntech.ycmall.Pageable;
import com.puyuntech.ycmall.entity.Operator;
import com.puyuntech.ycmall.entity.PhoneNumber;

/**
 * 
 * Service - 手机号码. 
 * Created on 2015-10-14 下午1:54:24 
 * @author 王凯斌
 */
public interface PhoneNumberService extends BaseService<PhoneNumber, Long>{

	/**
	 * 
	 * 根据运营商分页查询 手机号 .
	 * <p>方法详细说明,如果要换行请使用<br>标签</p>
	 * <br>
	 * author: 施长成
	 *   date: 2015-10-19 下午6:17:32
	 * @param phoneNumer 手机号 支持模糊查询
	 * @param operator 运营商
	 * @param pageable 分页
	 * @return
	 */
	Page<PhoneNumber> findPage(String phoneNumer,Operator operator, Pageable pageable);

	/**
	 * 
	 * 选择手机号 .
	 * <br>
	 * author: 施长成
	 *   date: 2015-10-20 上午10:50:21
	 * @param phoneNumber 当前选择的手机号
	 * @param model 
	 * @return 
	 */
	Map<String, Object> selectedPhone(PhoneNumber phoneNumber ,Long userId);

	/**
	 * 
	 * 商品回退 .
	 * <p>方法详细说明,如果要换行请使用<br>标签</p>
	 * <br>
	 * author: 施长成
	 *   date: 2015-11-10 下午3:19:38
	 * @param phoneNum 手机号
	 */
	void rollback(PhoneNumber phoneNum);
	
	/**
	 * 
	 * 根据手机号查找手机号信息
	 * author: 严志森
	 *   date: 2015-11-11 下午3:27:06
	 * @param sn
	 * @return
	 */
	PhoneNumber findByNumber(String number);

	List<PhoneNumber> findByOperator(Operator operator, int count, String key);

}
