package com.puyuntech.ycmall.vo;

/**
 * 
 * 物流. 
 * Created on 2015-10-8 下午5:27:39 
 * @author 严志森
 */
public class LogisticsVO {
	//处理时间
	String time;
	
	String location;
	String context;
	String nu;
	String companytype;
	public String getCompanytype() {
		return companytype;
	}
	public void setCompanytype(String companytype) {
		this.companytype = companytype;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getContext() {
		return context;
	}
	public void setContext(String context) {
		this.context = context;
	}
	public String getNu() {
		return nu;
	}
	public void setNu(String nu) {
		this.nu = nu;
	}
	
}
