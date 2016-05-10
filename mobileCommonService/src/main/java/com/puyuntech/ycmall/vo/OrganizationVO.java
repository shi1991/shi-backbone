package com.puyuntech.ycmall.vo;

/**
 * 
 * 自提门店选择时 ， 门店VO . 
 * Created on 2015-9-25 下午2:20:50 
 * @author 施长成
 */
public class OrganizationVO {
	
	private long id ;
	
	private String name ;
	
	private String address;
	
	private String tel;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public OrganizationVO() {
		super();
	}
	
	

}
