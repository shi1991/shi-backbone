package com.puyuntech.ycmall.service;

import java.math.BigDecimal;
import java.util.List;

import com.puyuntech.ycmall.Page;
import com.puyuntech.ycmall.Pageable;
import com.puyuntech.ycmall.entity.Admin;
import com.puyuntech.ycmall.entity.GodMoneyLog;
import com.puyuntech.ycmall.entity.Member;
import com.puyuntech.ycmall.entity.PointLog;

/**
 * 
 * Service - 会员 . 
 * Created on 2015-9-9 下午3:05:01 
 * @author 施长成
 */
public interface MemberService extends BaseService<Member, Long> {

	/**
	 * 判断用户名是否存在
	 * 
	 * @param username
	 *            用户名(忽略大小写)
	 * @return 用户名是否存在
	 */
	boolean usernameExists(String username);

	/**
	 * 判断用户名是否禁用
	 * 
	 * @param username
	 *            用户名(忽略大小写)
	 * @return 用户名是否禁用
	 */
	boolean usernameDisabled(String username);
	/**
	 * 判断昵称是否存在
	 * 
	 * @param nickname
	 *            昵称(忽略大小写)
	 * @return 昵称是否存在
	 */
	
	boolean nicknameExists(String nickname);

	/**
	 * 判断昵称是唯一
	 * 
	 * @param nickname
	 *            昵称(忽略大小写)
	 * @return 昵称是否唯一 
	 */
	public boolean nicknameUnique(String previousNickname, String currentNickname);
	
	
	/**
	 * 判断phone是否存在
	 * 
	 * @param phone
	 *            phone(忽略大小写)
	 * @return phone是否存在
	 */
	boolean phoneExists(String phone);
	
	
	/**
	 * 判断E-mail是否存在
	 * 
	 * @param email
	 *            E-mail(忽略大小写)
	 * @return E-mail是否存在
	 */
	boolean emailExists(String email);
	/**
	 * 
	 * 判断phone是否唯一
	 * author: 南金豆
	 *   date: 2015-10-7 下午6:49:19
	 * @param previousPhone
	 * @param currentPhone
	 * @return
	 */
	boolean phoneUnique(String previousPhone, String currentPhone);
	/**
	 * 判断E-mail是否唯一
	 * 
	 * @param previousEmail
	 *            修改前E-mail(忽略大小写)
	 * @param currentEmail
	 *            当前E-mail(忽略大小写)
	 * @return E-mail是否唯一
	 */
	
	boolean emailUnique(String previousEmail, String currentEmail);

	/**
	 * 查找会员
	 * 
	 * @param loginPluginId
	 *            登录插件ID
	 * @param openId
	 *            openID
	 * @return 会员，若不存在则返回null
	 */
	Member find(String loginPluginId, String openId);

	/**
	 * 根据用户名查找会员
	 * 
	 * @param username
	 *            用户名(忽略大小写)
	 * @return 会员，若不存在则返回null
	 */
	Member findByUsername(String username);
	
	/**
	 * 根据phone查找会员
	 * 
	 * @param phone
	 *            phone(忽略大小写)
	 * @return 会员，若不存在则返回null
	 */
	Member findByPhone(String phone);
	
	/**
	 * 根据E-mail查找会员
	 * 
	 * @param email
	 *            E-mail(忽略大小写)
	 * @return 会员，若不存在则返回null
	 */
	List<Member> findListByEmail(String email);
	
	
	


	/**
	 * 查找会员分页
	 * 
	 * @param rankingType
	 *            排名类型
	 * @param pageable
	 *            分页信息
	 * @return 会员分页
	 */
	Page<Member> findPage(Member.RankingType rankingType, Pageable pageable);

	/**
	 * 判断会员是否登录
	 * 
	 * @return 会员是否登录
	 */
	boolean isAuthenticated();

	/**
	 * 获取当前登录会员
	 * 
	 * @return 当前登录会员，若不存在则返回null
	 */
	Member getCurrent();

	/**
	 * 获取当前登录会员
	 * 
	 * @param lock
	 *            是否锁定
	 * @return 当前登录会员，若不存在则返回null
	 */
	Member getCurrent(boolean lock);

	/**
	 * 获取当前登录用户名
	 * 
	 * @return 当前登录用户名，若不存在则返回null
	 */
	String getCurrentUsername();

	/**
	 * 增加余额
	 * 
	 * @param member
	 *            会员
	 * @param amount
	 *            值
	 * @param type
	 *            类型
	 * @param operator
	 *            操作员
	 * @param memo
	 *            备注
	 *           
	 */
	void addBalance(Member member, BigDecimal amount, GodMoneyLog.Type type, Admin operator, String memo);

	/**
	 * 增加积分
	 * 
	 * @param member
	 *            会员
	 * @param amount
	 *            值
	 * @param type
	 *            类型
	 * @param operator
	 *            操作员
	 * @param memo
	 *            备注
	 *            TODO
	 */
	void addPoint(Member member, long amount, PointLog.Type type, Admin operator, String memo);

	/**
	 * 增加消费金额
	 * 
	 * @param member
	 *            会员
	 * @param amount
	 *            值
	 */
	void addAmount(Member member, BigDecimal amount);

	/**
	 * 
	 * 增加积分 和 经验  .
	 * <p>方法详细说明,如果要换行请使用<br>标签</p>
	 * <br>
	 * author: 施长成
	 *   date: 2015-9-13 下午3:40:42
	 * @param member  会员
	 * @param pointAmount  积分值 
	 * @param experienceAmount 经验值 
	 * @param exchange 类型
	 * @param operator 操作员
	 * @param memo 备注
	 */
	void addPointAndExperience(Member member, long pointAmount, long experienceAmount,
			com.puyuntech.ycmall.entity.PointLog.Type exchange, Admin operator, String memo);

	/**
	 * 
	 * 
	 * @param member
	 *            会员
	 * @param amount
	 *            值
	 * @param type
	 *            类型
	 * @param operator
	 *            操作员
	 * @param memo
	 *            备注
	 */
	/**
	 * 
	 * 增加余额 .
	 * <p>方法详细说明,如果要换行请使用<br>标签</p>
	 * <br>
	 * author: 施长成
	 *   date: 2015-10-30 下午3:44:30
	* @param member
	 *            会员
	 * @param amount
	 *            值
	 * @param type
	 *            类型
	 * @param operator
	 *            操作员
	 * @param memo
	 *            备注
	 */
	public void addGodMoney(Member member, BigDecimal amount, GodMoneyLog.Type type, Admin operator, String memo);

	Boolean userNamePhone(String userName, String phone);


}