package com.puyuntech.ycmall.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.puyuntech.ycmall.dao.RebatesDao;
import com.puyuntech.ycmall.entity.Member;
import com.puyuntech.ycmall.entity.Product;
import com.puyuntech.ycmall.entity.Rebates;
import com.puyuntech.ycmall.service.RebatesService;

/**
 * 
 * Service 返利 . 
 * Created on 2015-10-19 下午7:17:35 
 * @author 严志森
 */
@Service("rebatesServiceImpl")
public class RebatesServiceImpl extends BaseServiceImpl<Rebates , Long> implements RebatesService {
	
	@Resource(name = "rebatesDaoImpl")
	private RebatesDao rebatesDao;
	
	@Transactional(readOnly = true)
	public List<Rebates> findList(Member member) {
		return rebatesDao.findList(member);
	}

//	@Override
//	public String findProduct(Rebates rebates2) {
//		Rebates rebates=rebatesDao.find(rebates2.getId());
//		return rebates.getProduct().getName();
//	}
}
