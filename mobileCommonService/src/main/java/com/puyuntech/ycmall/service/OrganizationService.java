package com.puyuntech.ycmall.service;

import java.util.List;

import com.puyuntech.ycmall.Page;
import com.puyuntech.ycmall.entity.Area;
import com.puyuntech.ycmall.entity.Organization;
import com.puyuntech.ycmall.entity.Organization.Type;
import com.puyuntech.ycmall.vo.ResultVO;

/**
 * 
 * 门店Service . 
 * Created on 2015-9-24 下午4:22:54 
 * @author 施长成
 */
public interface OrganizationService extends BaseService<Organization, Long> {

	/**
	 * 
	 * 根据 门店 Id  , 门店营业时间 门店所在地区 查询门店列表 .
	 * <p>方法详细说明,如果要换行请使用<br>标签</p>
	 * <br>
	 * author: 施长成
	 *   date: 2015-9-24 下午4:34:43
	 * @param organizationId
	 * @param date
	 * @param area
	 * @return
	 */
	List<Organization> findList(Long organizationId, String date , Area area);
	
	
	/**
	 * 查找店面分页
	 * @return店面分页
	 */
	Page<Organization> findPage();
	
	/**
	 * 根据门店名称查找门店
	 * 
	 * @param name
	 *            门店名
	 * @return门店
	 */
	Organization  findByName(String name);
	
	/**
	 * 查询指定门店的工作人员.
	 * author: 严志森
	 *   date: 2015-10-22 下午5:34:43
	 * @param organizationId 门店id
	 * @return 
	 */
	List<ResultVO> findStaffByOrganizationId(String organizationId);
	
	/**
	 * 查询指定门店的地址信息.
	 * author: 严志森
	 *   date: 2015-10-22 下午5:34:43
	 * @param organizationId 门店id
	 * @return 
	 */
	List<ResultVO> findAddressByOrganizationId(String organizationId);

	/**
	 * 查询除当前门店外其他门店的信息.
	 * author: 严志森
	 *   date: 2015-10-22 下午5:34:43
	 * @param organizationId 门店id
	 * @return 
	 */	
	List<Organization> findOther(Long organizationId);


	List<Organization> findBistribution();


	List<Organization> findZiTi(int type);
	
}
