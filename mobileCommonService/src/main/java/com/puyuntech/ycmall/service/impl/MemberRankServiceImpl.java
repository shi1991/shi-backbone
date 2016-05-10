package com.puyuntech.ycmall.service.impl;

import java.math.BigDecimal;

import javax.annotation.Resource;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.puyuntech.ycmall.dao.MemberRankDao;
import com.puyuntech.ycmall.entity.MemberRank;
import com.puyuntech.ycmall.service.MemberRankService;

/**
 * 
 *  Service - 会员等级 . 
 * Created on 2015-10-18 下午2:46:47 
 * @author 南金豆
 */
@Service("memberRankServiceImpl")
public class MemberRankServiceImpl  extends BaseServiceImpl<MemberRank, Long> implements MemberRankService {

	@Resource(name = "memberRankDaoImpl")
	private MemberRankDao memberRankDao;

	@Transactional(readOnly = true)
	public boolean nameExists(String name) {
		return memberRankDao.nameExists(name);
	}

	@Transactional(readOnly = true)
	public boolean nameUnique(String previousName, String currentName) {
		return StringUtils.equalsIgnoreCase(previousName, currentName) || !memberRankDao.nameExists(currentName);
	}

	@Transactional(readOnly = true)
	public boolean amountExists(BigDecimal amount) {
		return memberRankDao.amountExists(amount);
	}

	@Transactional(readOnly = true)
	public boolean amountUnique(BigDecimal previousAmount, BigDecimal currentAmount) {
		return (previousAmount != null && previousAmount.compareTo(currentAmount) == 0) || !memberRankDao.amountExists(currentAmount);
	}

	@Transactional(readOnly = true)
	public MemberRank findDefault() {
		return memberRankDao.findDefault();
	}

	@Transactional(readOnly = true)
	public MemberRank findByAmount(BigDecimal amount) {
		return memberRankDao.findByAmount(amount);
	}

	@Override
	@Transactional
	public MemberRank save(MemberRank memberRank) {
		Assert.notNull(memberRank);

		if (BooleanUtils.isTrue(memberRank.getIsDefault())) {
			memberRankDao.setDefault(memberRank);
		}
		return super.save(memberRank);
	}

	@Override
	@Transactional
	public MemberRank update(MemberRank memberRank) {
		Assert.notNull(memberRank);

		if (BooleanUtils.isTrue(memberRank.getIsDefault())) {
			memberRankDao.setDefault(memberRank);
		}
		return super.update(memberRank);
	}

}
