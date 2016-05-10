package com.puyuntech.ycmall.entity;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

/**
 * 
 * 积分获得限制. 
 * Created on 2015-12-11 下午1:17:37 
 * @author 王凯斌
 */
@Entity
@Table(name="t_point_rule")
public class PointRule extends BaseEntity<Long> {

	private static final long serialVersionUID = 8692647093247311628L;

	/**
	 * 类型
	 */
	public enum Type {

		/** 每日总额上限 */
		MaxSumDaily,

		/** 每日次数上限 */
		MaxCountDaily,

		/** 每次获得额度 */
		ValueEach,
	}
	
	private PointRule.Type type;
	
	/** 数值 */
	private Long value;
	
	//关联组织
	private Set<PointBehavior> pointBehaviors;

	@Column(name="f_type",nullable=false)
	public PointRule.Type getType() {
		return type;
	}

	public void setType(PointRule.Type type) {
		this.type = type;
	}

	@Column(name="f_value",nullable=false)
	public Long getValue() {
		return value;
	}

	public void setValue(Long value) {
		this.value = value;
	}

	@ManyToMany(mappedBy = "pointRules", fetch = FetchType.LAZY )
	public Set<PointBehavior> getPointBehaviors() {
		return pointBehaviors;
	}

	public void setPointBehaviors(Set<PointBehavior> pointBehaviors) {
		this.pointBehaviors = pointBehaviors;
	}

}
