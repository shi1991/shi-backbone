
package com.puyuntech.ycmall.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.puyuntech.ycmall.dao.ProblemInformationDao;
import com.puyuntech.ycmall.entity.ProblemClassification;
import com.puyuntech.ycmall.entity.ProblemInformation;
import com.puyuntech.ycmall.service.ProblemInformationService;

/**
 * 
 * service 帮助中心 问题内容. 
 * Created on 2015-11-20 下午2:55:47 
 * @author 严志森
 */
@Service("problemInformationServiceImpl")
public class ProblemInformationServiceImpl extends BaseServiceImpl<ProblemInformation, Long> implements ProblemInformationService {
	/**
	 * 引入dao层对象
	 */
	@Resource(name = "problemInformationDaoImpl")
	private ProblemInformationDao problemInformationDao;

	@Override
	public List<ProblemInformation> findByParent(ProblemClassification problemClassification) {
		return problemInformationDao.findByParent(problemClassification);
	}

}