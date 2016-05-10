package com.puyuntech.ycmall.service.impl;

import java.math.BigDecimal;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.LockModeType;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import com.puyuntech.ycmall.Page;
import com.puyuntech.ycmall.Pageable;
import com.puyuntech.ycmall.Principal;
import com.puyuntech.ycmall.Setting;
import com.puyuntech.ycmall.dao.GodMoneyLogDao;
import com.puyuntech.ycmall.dao.MemberDao;
import com.puyuntech.ycmall.dao.MemberRankDao;
import com.puyuntech.ycmall.dao.PointLogDao;
import com.puyuntech.ycmall.entity.Admin;
import com.puyuntech.ycmall.entity.GodMoneyLog;
import com.puyuntech.ycmall.entity.Member;
import com.puyuntech.ycmall.entity.MemberRank;
import com.puyuntech.ycmall.entity.PointLog;
import com.puyuntech.ycmall.entity.PointLog.Type;
import com.puyuntech.ycmall.service.MemberService;
import com.puyuntech.ycmall.util.SystemUtils;

/**
 * 
 * Service - 会员 . 
 * Created on 2015-9-6 上午9:33:23 
 * @author 施长成
 */
@Service("memberServiceImpl")
public class MemberServiceImpl extends BaseServiceImpl<Member, Long> implements MemberService {

	@Resource(name = "memberDaoImpl")
	private MemberDao memberDao;
	@Resource(name = "memberRankDaoImpl")
	private MemberRankDao memberRankDao;
	@Resource(name = "pointLogDaoImpl")
	private PointLogDao pointLogDao;
	@Resource(name="godMoneyLogDaoImpl")
	private GodMoneyLogDao godMoneyDao;

	@Transactional(readOnly = true)
	public boolean usernameExists(String username) {
		return memberDao.usernameExists(username);
	}

	@Transactional(readOnly = true)
	public boolean usernameDisabled(String username) {
		Assert.hasText(username);

		Setting setting = SystemUtils.getSetting();
		if (setting.getDisabledUsernames() != null) {
			for (String disabledUsername : setting.getDisabledUsernames()) {
				if (StringUtils.containsIgnoreCase(username, disabledUsername)) {
					return true;
				}
			}
		}
		return false;
	}

	@Transactional(readOnly = true)
	public boolean emailExists(String email) {
		return memberDao.emailExists(email);
	}
	@Override
	public boolean nicknameExists(String nickname) {
		
		return memberDao.nicknameExists(nickname);
	}
	
	@Transactional(readOnly = true)
	public boolean nicknameUnique(String previousNickname, String currentNickname) {
		if (StringUtils.equalsIgnoreCase(previousNickname,currentNickname)) {
			return true;
		}
		return !memberDao.nicknameExists(currentNickname);
	}
	
	
	
	@Override
	public boolean phoneExists(String phone) {
		
		return memberDao.phoneExists(phone);
	}
	
	@Transactional(readOnly = true)
	public boolean phoneUnique(String previousPhone, String currentPhone) {
		if (StringUtils.equalsIgnoreCase(previousPhone,currentPhone)) {
			return true;
		}
		return !memberDao.phoneExists(currentPhone);
	}
	
	@Transactional(readOnly = true)
	public boolean emailUnique(String previousEmail, String currentEmail) {
		if (StringUtils.equalsIgnoreCase(previousEmail, currentEmail)) {
			return true;
		}
		return !memberDao.emailExists(currentEmail);
	}

	@Transactional(readOnly = true)
	public Member find(String loginPluginId, String openId) {
		return memberDao.find(loginPluginId, openId);
	}

	@Transactional(readOnly = true)
	public Member findByUsername(String username) {
		return memberDao.findByUsername(username);
	}

	@Transactional(readOnly = true)
	public Member findByPhone(String phone) {
		Member member = memberDao.findByPhone(phone);
		return member;
	}
	
	@Transactional(readOnly = true)
	public List<Member> findListByEmail(String email) {
		return memberDao.findListByEmail(email);
	}

	@Transactional(readOnly = true)
	public Page<Member> findPage(Member.RankingType rankingType, Pageable pageable) {
		return memberDao.findPage(rankingType, pageable);
	}

	@Transactional(readOnly = true)
	public boolean isAuthenticated() {
		RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
		return requestAttributes != null && requestAttributes.getAttribute(Member.PRINCIPAL_ATTRIBUTE_NAME, RequestAttributes.SCOPE_SESSION) != null;
	}

	@Transactional(readOnly = true)
	public Member getCurrent() {
		return getCurrent(false);
	}

	@Transactional(readOnly = true)
	public Member getCurrent(boolean lock) {
		RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
		Principal principal = requestAttributes != null ? (Principal) requestAttributes.getAttribute(Member.PRINCIPAL_ATTRIBUTE_NAME, RequestAttributes.SCOPE_SESSION) : null;
		Long id = principal != null ? principal.getId() : null;
		if (lock) {
			return memberDao.find(id, LockModeType.PESSIMISTIC_WRITE);
		} else {
			return memberDao.find(id);
		}
	}

	@Transactional(readOnly = true)
	public String getCurrentUsername() {
		RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
		Principal principal = requestAttributes != null ? (Principal) requestAttributes.getAttribute(Member.PRINCIPAL_ATTRIBUTE_NAME, RequestAttributes.SCOPE_SESSION) : null;
		return principal != null ? principal.getUsername() : null;
	}

	//TODO积分没有添加成功，需要验证有没有到达次数上限，总数上限
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public void addPoint(Member member, long amount, PointLog.Type type, Admin operator, String memo) {
		Assert.notNull(member);
		Assert.notNull(type);

		if (amount == 0) {
			return;
		}
		Member member2=memberDao.find(member.getId());
		if (!LockModeType.PESSIMISTIC_WRITE.equals(memberDao.getLockMode(member2))) {
			memberDao.refresh(member2, LockModeType.PESSIMISTIC_WRITE);
		}

		Assert.notNull(member.getPoint());
		Assert.state(member.getPoint() + amount >= 0);

		member.setPoint(member.getPoint() + amount);
		this.update(member);
//		memberDao.merge( member );
		PointLog pointLog = new PointLog();
		pointLog.setType(type);
		pointLog.setCredit(amount > 0 ? amount : 0L);
		pointLog.setDebit(amount < 0 ? Math.abs(amount) : 0L);
		pointLog.setBalance(member.getPoint());
		pointLog.setOperator(operator);
		pointLog.setMemo(memo);
		pointLog.setMember(member);
		pointLogDao.persist(pointLog);
	}

	public void addAmount(Member member, BigDecimal amount) {
		Assert.notNull(member);
		Assert.notNull(amount);

		if (amount.compareTo(BigDecimal.ZERO) == 0) {
			return;
		}

		if (!LockModeType.PESSIMISTIC_WRITE.equals(memberDao.getLockMode(member))) {
			memberDao.refresh(member, LockModeType.PESSIMISTIC_WRITE);
		}

		Assert.notNull(member.getAmount());
		Assert.state(member.getAmount().add(amount).compareTo(BigDecimal.ZERO) >= 0);

		member.setAmount(member.getAmount().add(amount));
		MemberRank memberRank = member.getMemberRank();
		if (memberRank != null && BooleanUtils.isFalse(memberRank.getIsSpecial())) {
			MemberRank newMemberRank = memberRankDao.findByAmount(member.getAmount());
			if (newMemberRank != null && newMemberRank.getAmount() != null && newMemberRank.getAmount().compareTo(memberRank.getAmount()) > 0) {
				member.setMemberRank(newMemberRank);
			}
		}
		memberDao.flush();
	}

	
	@Override
	@Transactional
	public Member save(Member member) {
		Assert.notNull(member);
		Member pMember = super.save(member);
		/*TODO 邮件
		 * mailService.sendRegisterMemberMail(pMember);
		smsService.sendRegisterMemberSms(pMember);*/
		return pMember;
	}
	

	@Override
	public void addPointAndExperience(Member member, long pointAmount,
			long experienceAmount, Type exchange, Admin operator, String memo) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addGodMoney(Member member, BigDecimal amount, GodMoneyLog.Type type, Admin operator, String memo) {
		
		if (amount.compareTo(BigDecimal.ZERO) == 0) {
			return;
		}

		if (!LockModeType.PESSIMISTIC_WRITE.equals(memberDao.getLockMode(member))) {
			memberDao.refresh(member, LockModeType.PESSIMISTIC_WRITE);
		}

		member.setGodMoney( member.getGodMoney().add(amount) );
		memberDao.flush();

		GodMoneyLog godMoneyLog = new GodMoneyLog();
		godMoneyLog.setType(type);
		godMoneyLog.setCredit(amount.compareTo(BigDecimal.ZERO) > 0 ? amount : BigDecimal.ZERO);
		godMoneyLog.setDebit(amount.compareTo(BigDecimal.ZERO) < 0 ? amount.abs() : BigDecimal.ZERO);
		godMoneyLog.setBalance(member.getGodMoney());
		godMoneyLog.setOperator(operator);
		godMoneyLog.setMemo(memo);
		godMoneyLog.setMember(member);
		
		godMoneyDao.merge(godMoneyLog);
	
	}

	@Override
	public void addBalance(Member member, BigDecimal amount,
			com.puyuntech.ycmall.entity.GodMoneyLog.Type type, Admin operator,
			String memo) {

		if (amount.compareTo(BigDecimal.ZERO) == 0) {
			return;
		}

		if (!LockModeType.PESSIMISTIC_WRITE.equals(memberDao.getLockMode(member))) {
			memberDao.refresh(member, LockModeType.PESSIMISTIC_WRITE);
		}

		member.setGodMoney(member.getGodMoney().add(amount));
		memberDao.flush();

		GodMoneyLog godMoneyLog = new GodMoneyLog();
		godMoneyLog.setType(type);
		godMoneyLog.setCredit(amount.compareTo(BigDecimal.ZERO) > 0 ? amount : BigDecimal.ZERO);
		godMoneyLog.setDebit(amount.compareTo(BigDecimal.ZERO) < 0 ? amount.abs() : BigDecimal.ZERO);
		godMoneyLog.setBalance(member.getGodMoney());
		godMoneyLog.setOperator(operator);
		godMoneyLog.setMemo(memo);
		godMoneyLog.setMember(member);
		godMoneyDao.persist(godMoneyLog);
		
	}

	@Override
	public Boolean userNamePhone(String userName, String phone) {
		
		return memberDao.userNamePhone(userName,phone);
	}

}