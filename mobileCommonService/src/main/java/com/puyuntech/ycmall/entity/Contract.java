package com.puyuntech.ycmall.entity;

import java.util.Set;
import java.util.TreeSet;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * 
 * 合约套餐信息 Entity . Created on 2015-10-15 下午5:20:45
 * 
 * @author 施长成
 */
@Entity
@Table(name = "t_contract")
public class Contract extends BaseEntity<Long> {

	private static final long serialVersionUID = -6489268184120695862L;

	private String name;

	private Boolean state;

	private Area area;

	private Operator operator;

	private Set<ContractItem> contractItems = new TreeSet<ContractItem>();

	@Column(name = "f_name", updatable = true)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "f_state")
	public Boolean getState() {
		return state;
	}

	public void setState(Boolean state) {
		this.state = state;
	}

	@OneToOne(fetch = FetchType.LAZY ,targetEntity=Area.class)
	@JoinColumn(name = "f_area")
	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
	}

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "f_operator")
	public Operator getOperator() {
		return operator;
	}

	public void setOperator(Operator operator) {
		this.operator = operator;
	}

	@OneToMany(mappedBy="contract" , fetch = FetchType.LAZY , cascade = CascadeType.REMOVE )
	public Set<ContractItem> getContractItems() {
		return contractItems;
	}

	public void setContractItems(Set<ContractItem> contractItems) {
		this.contractItems = contractItems;
	}
	
	
	

}
