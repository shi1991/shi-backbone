package com.puyuntech.ycmall.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import com.puyuntech.ycmall.Page;
import com.puyuntech.ycmall.Pageable;
import com.puyuntech.ycmall.dao.BonusLogDao;
import com.puyuntech.ycmall.entity.BonusLog;
import com.puyuntech.ycmall.entity.Member;
import com.puyuntech.ycmall.service.BonusLogService;
/**
 * 
 *  Service - 红包记录. 
 * Created on 2015-11-10 下午1:40:45 
 * @author 南金豆
 */
@Service("bonusLogServiceImpl")
public class BonusLogServiceImpl extends BaseServiceImpl<BonusLog, Long> implements BonusLogService{

	@Resource(name = "bonusLogDaoImpl")
	private BonusLogDao bonusLogDao;
	
	@Override
	public Page<BonusLog> findPage(Member member, Pageable pageable) {
		return bonusLogDao.findPage(member,pageable);
	}

	@Override
	public List<Object> find(String id) {
		return bonusLogDao.find(id);
	}

}
