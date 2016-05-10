package com.puyuntech.ycmall.util.kuaidi;

import java.util.ArrayList;


import com.fasterxml.jackson.annotation.JsonIgnore;

public class Result {
	
	/**
	 * 无意义，请忽略
	 */
	private String message = "";
	/**
	 * 物流单号
	 */
	@JsonIgnore
	private String nu = "";
	/**
	 * 无意义，请忽略
	 */
	@JsonIgnore
	private String ischeck = "0";
	/**
	 * 物流公司编号
	 */
	@JsonIgnore
	private String com = "";
	/**
	 * 查询结果状态： 
		0：物流单暂无结果， 
		1：查询成功， 
		2：接口出现异常，
	 */
	private String status = "0";
	/**
	 * 11
	 */
	@JsonIgnore
	private ArrayList<ResultItem> data = new ArrayList<ResultItem>();
	/**
	 * 快递单当前的状态 ：　 
		0：在途，即货物处于运输过程中；
		1：揽件，货物已由快递公司揽收并且产生了第一条跟踪信息；
		2：疑难，货物寄送过程出了问题；
		3：签收，收件人已签收；
		4：退签，即货物由于用户拒签、超区等原因退回，而且发件人已经签收；
		5：派件，即快递正在进行同城派件；
		6：退回，货物正处于退回发件人的途中
	 */
	@JsonIgnore
	private String state = "0";
	/**
	 * 
	 */
	@JsonIgnore
	private String condition = "";

	@SuppressWarnings("unchecked")
	public Result clone() {
		Result r = new Result();
		r.setCom(this.getCom());
		r.setIscheck(this.getIscheck());
		r.setMessage(this.getMessage());
		r.setNu(this.getNu());
		r.setState(this.getState());
		r.setStatus(this.getStatus());
		r.setCondition(this.getCondition());
		r.setData((ArrayList<ResultItem>) this.getData().clone());

		return r;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getNu() {
		return nu;
	}

	public void setNu(String nu) {
		this.nu = nu;
	}

	public String getCom() {
		return com;
	}

	public void setCom(String com) {
		this.com = com;
	}

	public ArrayList<ResultItem> getData() {
		return data;
	}

	public void setData(ArrayList<ResultItem> data) {
		this.data = data;
	}

	public String getIscheck() {
		return ischeck;
	}

	public void setIscheck(String ischeck) {
		this.ischeck = ischeck;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	@Override
	public String toString() {
		return JacksonHelper.toJSON(this);
	}
}
