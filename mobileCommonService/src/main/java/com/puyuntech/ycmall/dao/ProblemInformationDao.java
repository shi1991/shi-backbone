package com.puyuntech.ycmall.dao;

import java.util.List;

import com.puyuntech.ycmall.entity.ProblemClassification;
import com.puyuntech.ycmall.entity.ProblemInformation;

/**
 * 
 * dao 帮助中心 问题内容. 
 * Created on 2015-11-20 下午2:56:52 
 * @author 严志森
 */
public interface ProblemInformationDao extends BaseDao<ProblemInformation, Long> {

	List<ProblemInformation> findByParent(ProblemClassification problemClassification);

}