package com.puyuntech.ycmall.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.puyuntech.ycmall.Page;
import com.puyuntech.ycmall.dao.OrganizationDao;
import com.puyuntech.ycmall.entity.Area;
import com.puyuntech.ycmall.entity.Organization;
import com.puyuntech.ycmall.entity.Organization.Type;
import com.puyuntech.ycmall.service.OrganizationService;
import com.puyuntech.ycmall.vo.ResultVO;

/**
 * 
 * . 
 * Created on 2015-9-24 下午4:25:47 
 * @author 施长成
 */
@Service("organizationServiceImpl")
public class OrganizationServiceImpl extends BaseServiceImpl<Organization, Long> implements OrganizationService {

	@Resource(name="organizationDaoImpl")
	private OrganizationDao orgainzationDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<Organization> findList(Long organizationId, String date,
			Area area) {
		return orgainzationDao.findList(organizationId , date , area );	
	}
	@Override
	public Page<Organization> findPage() {
		return orgainzationDao.findPage();
	}
	@Override
	public List<ResultVO> findStaffByOrganizationId(String organizationId) {
		return orgainzationDao.findStaffByOrganizationId(organizationId);
	}
	@Override
	public List<ResultVO> findAddressByOrganizationId(String organizationId) {
		return orgainzationDao.findAddressByOrganizationId(organizationId);
	}
	@Override
	public List<Organization> findOther(Long organizationId) {
		return orgainzationDao.findOther(organizationId);
	}
	@Override
	public List<Organization> findBistribution() {
		return orgainzationDao.findBistribution();
	}
	@Override
	public List<Organization> findZiTi(int type) {
		return orgainzationDao.findZiTi(type);
	}
	@Override
	public Organization findByName(String name) {
		return orgainzationDao.findByName(name);
	}
	
	
	
	
}
