package com.puyuntech.ycmall.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.puyuntech.ycmall.Page;
import com.puyuntech.ycmall.Pageable;
import com.puyuntech.ycmall.dao.BonusDao;
import com.puyuntech.ycmall.entity.BonusEntity;
import com.puyuntech.ycmall.entity.Member;
import com.puyuntech.ycmall.service.BonusService;


@Service("bonusServiceImpl")
public class BonusServiceImpl extends BaseServiceImpl<BonusEntity, Long> implements BonusService{
	
	@Resource(name = "bonusDaoImpl")
	private BonusDao bonusDao;

	@Override
	public Page<BonusEntity> findPage(Member member,Pageable pageable) {
		return bonusDao.findPage( member,pageable);
	}
}
