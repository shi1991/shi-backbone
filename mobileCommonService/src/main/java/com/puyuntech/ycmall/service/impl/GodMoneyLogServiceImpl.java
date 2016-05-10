package com.puyuntech.ycmall.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.puyuntech.ycmall.Page;
import com.puyuntech.ycmall.Pageable;
import com.puyuntech.ycmall.entity.GodMoneyLog;
import com.puyuntech.ycmall.entity.Member;
import com.puyuntech.ycmall.service.GodMoneyLogService;
import com.puyuntech.ycmall.dao.GodMoneyLogDao;

/**
 * 
 * Service 神币 . 
 * Created on 2015-10-12 下午15:55:50 
 * @author 南金豆
 */

@Service("godMoneyLogServiceImpl")
public class GodMoneyLogServiceImpl extends BaseServiceImpl<GodMoneyLog, Long> implements GodMoneyLogService{
	@Resource(name = "godMoneyLogDaoImpl")
	private GodMoneyLogDao GodMoneyLogDao;

	@Override
	public Page<GodMoneyLog> findPage(Member member,Pageable pageable) {
		return GodMoneyLogDao.findPage( member,pageable);
	}
}
