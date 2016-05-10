package com.puyuntech.ycmall.listener;

import java.util.Date;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import com.puyuntech.ycmall.entity.BaseEntity;

/**
 * 
 * Listener - 创建日期、修改日期、版本处理. 
 * Created on 2015-8-25 下午5:16:23 
 * @author 施长成
 */
public class EntityListener {

	/**
	 * 保存前处理
	 * 
	 * @param entity
	 *            实体对象
	 */
	@PrePersist
	public void prePersist(BaseEntity<?> entity) {
		entity.setCreateDate(new Date());
		entity.setModifyDate(new Date());
		entity.setVersion(null);
	}

	/**
	 * 更新前处理
	 * 
	 * @param entity
	 *            实体对象
	 */
	@PreUpdate
	public void preUpdate(BaseEntity<?> entity) {
		entity.setModifyDate(new Date());
	}

}