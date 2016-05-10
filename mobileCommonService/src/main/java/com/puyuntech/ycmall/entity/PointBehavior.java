package com.puyuntech.ycmall.entity;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

/**
 * 
 *  积分获得场景. 
 * Created on 2015-12-11 下午1:17:37 
 * @author 王凯斌
 */
@Entity
@Table(name="t_point_behavior")
public class PointBehavior extends BaseEntity<Long> {

	private static final long serialVersionUID = -4030966034853116810L;

	/** 名字 */
	private String name;
	
	/** 关联限制 */
	private Set<PointRule> pointRules;

	@Column(name="f_name",nullable=false,updatable=false)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "t_point_behavior_point_rule", joinColumns = { @JoinColumn(name = "f_point_behavior", referencedColumnName = "f_id") }, inverseJoinColumns = { @JoinColumn(name = "f_point_rule", referencedColumnName = "f_id") })
	public Set<PointRule> getPointRules() {
		return pointRules;
	}

	public void setPointRules(Set<PointRule> pointRules) {
		this.pointRules = pointRules;
	}

}
