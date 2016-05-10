package com.puyuntech.ycmall.dao;

import java.util.Date;
import java.util.List;

import com.puyuntech.ycmall.Page;
import com.puyuntech.ycmall.Pageable;
import com.puyuntech.ycmall.entity.Member;
import com.puyuntech.ycmall.entity.MemberAttribute;


/**
 * 
 * Dao - 会员 
 * Created on 2015-8-26 下午15:32:33 
 * @author 严志森
 */
public interface MemberDao extends BaseDao<Member, Long> {

	/**
	 * 判断用户名是否存在
	 * 
	 * @param username
	 *            用户名(忽略大小写)
	 * @return 用户名是否存在
	 */
	boolean usernameExists(String username);

	/**
	 * 判断phone是否存在
	 * 
	 * @param phone
	 *            phone(忽略大小写)
	 * @return phone是否存在
	 */
	
	boolean phoneExists(String phone);
	
	
	/**
	 * 判断nickname是否存在
	 * 
	 * @param nickname
	 *            nickname(忽略大小写)
	 * @return nickname是否存在
	 */
	
	boolean nicknameExists(String nickname);
	/**
	 * 判断E-mail是否存在
	 * 
	 * @param email
	 *            E-mail(忽略大小写)
	 * @return E-mail是否存在
	 */
	boolean emailExists(String email);

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
	 * 根据手机号查找会员
	 * 
	 * @param phone
	 *            手机号(忽略大小写)
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
	 * 查询会员注册数
	 * 
	 * @param beginDate
	 *            起始日期
	 * @param endDate
	 *            结束日期
	 * @return 会员注册数
	 */
	Long registerMemberCount(Date beginDate, Date endDate);

	/**
	 * 清空会员注册项值
	 * 
	 * @param memberAttribute
	 *            会员注册项
	 */
	void clearAttributeValue(MemberAttribute memberAttribute);

	boolean userNamePhone(String userName, String phone);

}