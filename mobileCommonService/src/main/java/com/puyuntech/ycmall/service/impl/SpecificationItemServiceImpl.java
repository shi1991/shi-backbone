
package com.puyuntech.ycmall.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.puyuntech.ycmall.entity.value.SpecificationItem;
import com.puyuntech.ycmall.service.SpecificationItemService;

/**
 * 
 * Service - 规格项. 
 * Created on 2015-10-14 下午2:38:28 
 * @author 王凯斌
 */
@Service("specificationItemServiceImpl")
public class SpecificationItemServiceImpl implements SpecificationItemService {

	public void filter(List<SpecificationItem> specificationItems) {
		
		/**
		 * 过滤规格项
		 */
		CollectionUtils.filter(specificationItems, new Predicate() {
			public boolean evaluate(Object object) {
				SpecificationItem specificationItem = (SpecificationItem) object;
				if (specificationItem == null || StringUtils.isEmpty(specificationItem.getName())) {
					return false;
				}
				CollectionUtils.filter(specificationItem.getEntries(), new Predicate() {
					private Set<Integer> idSet = new HashSet<Integer>();
					private Set<String> valueSet = new HashSet<String>();

					public boolean evaluate(Object object) {
						SpecificationItem.Entry entry = (SpecificationItem.Entry) object;
						return entry != null && entry.getId() != null && StringUtils.isNotEmpty(entry.getValue()) && entry.getIsSelected() != null && idSet.add(entry.getId()) && valueSet.add(entry.getValue());
					}
				});
				return CollectionUtils.isNotEmpty(specificationItem.getEntries()) && specificationItem.isSelected();
			}
		});
	}

}