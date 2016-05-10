package com.puyuntech.ycmall.entity;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;

import java.util.HashSet;
import java.util.Set;

/**
 * 
 * (运营商 operator). Created on 2015-10-8 下午1:29:49
 * 
 * @author 王凯斌
 */
@Entity
@Table(name = "t_operator")
public class Operator extends BaseEntity<Long> {

	private static final long serialVersionUID = 2740062287655305614L;

	/** 名称 */
	private String name;

	/** 号码 */
	private Set<PhoneNumber> phoneNumber = new HashSet<PhoneNumber>();

	/** 套餐名称 **/
	private Set<Contract> contract = new HashSet<Contract>();
	
	/** 商品 */
	private Set<Product> product = new HashSet<Product>();
	
	/**
	 * 获取名称
	 * 
	 * @return 名称
	 */
	@NotEmpty
	@Length(max = 200)
	@Column(name = "f_name", nullable = false)
	public String getName() {
		return name;
	}

	/**
	 * 设置名称
	 * 
	 * @param name
	 *            名称
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 获取号码
	 * 
	 * @return 号码
	 */
	@OneToMany(mappedBy = "operator", fetch = FetchType.LAZY)
	public Set<PhoneNumber> getPhoneNumber() {
		return phoneNumber;
	}

	/**
	 * 设置号码
	 * 
	 * @param phoneNumber
	 *            号码
	 */
	public void setPhoneNumber(Set<PhoneNumber> phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	/**
	 * 获取套餐
	 * 
	 * @return 套餐
	 */
	@OneToMany(mappedBy = "operator", fetch = FetchType.LAZY ,cascade = CascadeType.ALL)
	public Set<Contract> getContract() {
		return contract;
	}

	/**
	 * 设置套餐
	 * 
	 * @param contractPackage
	 *            套餐
	 */
	public void setContract(Set<Contract> contract) {
		this.contract = contract;
	}
	
	/**
	 * 获取商品
	 * 
	 * @return 商品
	 */
	@OneToMany(mappedBy = "operator", fetch = FetchType.LAZY)
	public Set<Product> getProduct() {
		return product;
	}
	
	/**
	 * 设置商品
	 * 
	 * @param product
	 *            商品
	 */
	public void setProduct(Set<Product> product) {
		this.product = product;
	}
	
}
