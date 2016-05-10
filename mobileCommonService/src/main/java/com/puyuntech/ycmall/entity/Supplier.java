package com.puyuntech.ycmall.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * 
 *  供应商信息表  . 
 * Created on 2015-8-28 上午9:54:36 
 * @author 施长成
 */
@Entity
@Table(name="t_supplier")
public class Supplier extends BaseEntity<Long> {

	private static final long serialVersionUID = -3825077387328040479L;
	
	/** 名称 **/
	private String name;
	
	/**地址 **/
	private String address;
	
	/** 联系电话 **/
	private String phone;
	
	/** 负责人 **/
	private String supervisor;
	
	/** 采购单 **/
	private Set<Purchase> purchases = new HashSet<Purchase>();
	
	/** 仓库退换记录 **/
	private Set<WareHouseChangeLog> wareHouseChangeLogs = new HashSet<WareHouseChangeLog>();

	@Column(name="f_name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name="f_address")
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Column(name="f_phone")
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Column(name="f_supervisor")
	public String getSupervisor() {
		return supervisor;
	}

	public void setSupervisor(String supervisor) {
		this.supervisor = supervisor;
	}
	
	@OneToMany(mappedBy = "supplier" ,fetch =FetchType.LAZY)
	public Set<Purchase> getPurchases() {
		return purchases;
	}

	public void setPurchases(Set<Purchase> purchases) {
		this.purchases = purchases;
	}
	
	@OneToMany(mappedBy = "supplier" ,fetch =FetchType.LAZY)
	public Set<WareHouseChangeLog> getWareHouseChangeLogs() {
		return wareHouseChangeLogs;
	}

	public void setWareHouseChangeLogs(Set<WareHouseChangeLog> wareHouseChangeLogs) {
		this.wareHouseChangeLogs = wareHouseChangeLogs;
	}

	private Supplier() {
		super();
	}

}
