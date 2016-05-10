
package com.puyuntech.ycmall.service;

import java.util.List;

import com.puyuntech.ycmall.entity.value.SpecificationItem;



/**
 * 
 * Service - 规格项. 
 * Created on 2015-10-14 下午2:06:33 
 * @author 王凯斌
 */
public interface SpecificationItemService {

	/**
	 * 规格项过滤
	 * 
	 * @param specificationItems
	 *            规格项
	 */
	void filter(List<SpecificationItem> specificationItems);

}