package com.puyuntech.ycmall.constant;

/**
 * 
 * 常量 用于定义response请求的返回值信息 . 
 * Created on 2015-8-25 下午3:13:04 
 * @author 施长成
 */
public enum ResponseCode {
	
	SUCCESS("1","请求成功"),
	FAIL("0","请求失败"),
	ERROR("500", "崩溃性错误信息")
	;
	
	private String code;
	
	private String name;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	private ResponseCode(String code, String name) {
		this.code = code;
		this.name = name;
	}
	
	

}
