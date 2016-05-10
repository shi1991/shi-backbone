package com.puyuntech.ycmall.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.puyuntech.ycmall.Page;
import com.puyuntech.ycmall.Pageable;
import com.puyuntech.ycmall.dao.PointLogDao;
import com.puyuntech.ycmall.entity.Member;
import com.puyuntech.ycmall.entity.PointLog;
import com.puyuntech.ycmall.service.PointLogService;
/**
 * 
 * Service 积分. 
 * Created on 2015-10-12 下午15:55:50 
 * @author 南金豆
 */
@Service("pointLogServiceImpl")
public class PointLogServiceImpl extends BaseServiceImpl<PointLog, Long> implements PointLogService{
	@Resource(name = "pointLogDaoImpl")
	private PointLogDao PointLogDao;

	@Override
	public Page<PointLog> findPage(Member member,Pageable pageable) {
		return PointLogDao.findPage( member,pageable);
	}

	@Override
	public Long findByMemberNow(Member member) {
		return PointLogDao.findByMemberNow( member);
	}
	
	@Override
	public Long findCountByMemberNow(Member member) {
		return PointLogDao.findCountByMemberNow( member);
	}
}
