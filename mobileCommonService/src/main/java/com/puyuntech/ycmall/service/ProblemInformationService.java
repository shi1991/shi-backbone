
package com.puyuntech.ycmall.service;

import java.util.List;

import com.puyuntech.ycmall.entity.ProblemClassification;
import com.puyuntech.ycmall.entity.ProblemInformation;



/**
 * 
 * service 帮助中心 问题内容. 
 * Created on 2015-11-20 下午2:55:00 
 * @author 严志森
 */
public interface ProblemInformationService extends BaseService<ProblemInformation, Long> {

	List<ProblemInformation> findByParent(ProblemClassification problemClassification);

}