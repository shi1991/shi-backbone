package com.puyuntech.ycmall.service;

import org.springframework.stereotype.Service;

import com.puyuntech.ycmall.Page;
import com.puyuntech.ycmall.Pageable;
import com.puyuntech.ycmall.entity.GodMoneyLog;
import com.puyuntech.ycmall.entity.Member;
/**
 * 
 * Service 神币 . 
 * Created on 2015-10-12 下午15:55:50 
 * @author 南金豆
 */
@Service("godMoneyLogServiceImpl")
public interface GodMoneyLogService extends BaseService<GodMoneyLog, Long> {
	/**
	 * 查找神币分页
	 * 
	 * @return神币分页
	 */
	Page<GodMoneyLog> findPage(Member member, Pageable pageable);
	
}
