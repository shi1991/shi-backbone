package com.puyuntech.ycmall.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.puyuntech.ycmall.Page;
import com.puyuntech.ycmall.dao.PointBehaviorDao;
import com.puyuntech.ycmall.entity.Member;
import com.puyuntech.ycmall.entity.PointBehavior;
import com.puyuntech.ycmall.entity.PointLog;
import com.puyuntech.ycmall.entity.PointRule;
import com.puyuntech.ycmall.service.PointBehaviorService;
import com.puyuntech.ycmall.service.PointLogService;

/**
 * 
 * Service - 退款单. 
 * Created on 2015-11-28 下午4:54:19 
 * @author 王凯斌
 */
@Service("pointBehaviorServiceImpl")
public class PointBehaviorServiceImpl extends BaseServiceImpl<PointBehavior, Long> implements PointBehaviorService {

	/**
	 * 积分列表 Service
	 */
	@Resource(name = "pointLogServiceImpl")
	private PointLogService pointLogService;
	
	@Resource(name="pointBehaviorDaoImpl")
	private PointBehaviorDao pointBehaviorDao;
	
	@Override
	public PointBehavior findByName(String name) {
		return pointBehaviorDao.findByName(name);
	}
	
	public Long addPoint(Member member,PointBehavior pointBehavior,PointLog.Type type){
		
		Long point=0l;
				
		if( pointBehavior==null ){
			return point;
		}
		
		//登录总积分
		Long		piontAll=null;
		//获得积分次数
		Integer	getPointTime= null;
		//计算当天某活动获得积分的次数	
		int time=0;
		//计算当天某活动的总积分	
		long pointForOneAll=	0l;
		//增加的积分
		long pointAdd=0l;
		
		PointBehavior pointBehavior2=pointBehaviorDao.find(pointBehavior.getId());
		
		
		Set<PointRule> pointRules=pointBehavior2.getPointRules();
		if(pointRules!=null){
			for (PointRule pointRule : pointRules) {
				if (pointRule.getType()==PointRule.Type.ValueEach) {
					point =	pointRule.getValue();
				}
				if(pointRule.getType()==PointRule.Type.MaxCountDaily){
					getPointTime=pointRule.getValue().intValue();
				}
				if(pointRule.getType()==PointRule.Type.MaxSumDaily){
					piontAll=pointRule.getValue();
				}
			}
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Page<PointLog>pointLogs=pointLogService.findPage(member, null);
			if(pointLogs!=null){
				for(PointLog pointLog:pointLogs.getContent()){
					if(sdf.format(pointLog.getCreateDate()).equals(sdf.format(new Date()))&&pointLog.getType().equals(type)){
						time=time+1;
						pointForOneAll=pointForOneAll+pointLog.getCredit();
					}
				}
				if((getPointTime==null||time<getPointTime)&&(piontAll==null||pointForOneAll<piontAll)){
					pointAdd=point;
				}
			}else{
				pointAdd=point;
			}
		}
		return pointAdd;
	}
	
	
}