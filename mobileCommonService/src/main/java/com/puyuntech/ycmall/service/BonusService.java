package com.puyuntech.ycmall.service;

import org.springframework.stereotype.Service;

import com.puyuntech.ycmall.Page;
import com.puyuntech.ycmall.Pageable;
import com.puyuntech.ycmall.entity.BonusEntity;
import com.puyuntech.ycmall.entity.Member;

/**
 * 
 * Service 红包 . 
 * Created on 2015-11-05 下午15:55:50 
 * @author 南金豆
 */
@Service("bonusServiceImpl")
public interface BonusService extends BaseService<BonusEntity, Long> {

	/**
	 * 查找红包分页
	 * 
	 * @return红包分页
	 */
	Page<BonusEntity> findPage(Member member, Pageable pageable);
}
