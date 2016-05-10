package com.puyuntech.ycmall.vo;

import java.math.BigDecimal;

public class BindProductInfoVO {

	private String productId;
	
	private BigDecimal price;
	
	private String img;
	
	private String title;
	
	private String titleName;
	
	private String productName;
	
	private BigDecimal preferentialPrice;

	public BigDecimal getPreferentialPrice() {
		return preferentialPrice;
	}

	public void setPreferentialPrice(BigDecimal preferentialPrice) {
		this.preferentialPrice = preferentialPrice;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}
	
	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	

	public String getTitleName() {
		return titleName;
	}

	public void setTitleName(String titleName) {
		this.titleName = titleName;
	}

	public BindProductInfoVO(String productId, BigDecimal price, String img) {
		super();
		this.productId = productId;
		this.price = price;
		this.img = img;
	}
	
	public BindProductInfoVO(String productId, BigDecimal price, String img,
			String title, String titleName) {
		super();
		this.productId = productId;
		this.price = price;
		this.img = img;
		this.title = title;
		this.titleName = titleName;
	}

	public BindProductInfoVO(String productId, BigDecimal price, String img,
			String title, String titleName,BigDecimal preferentialPrice,String productName) {
		super();
		this.productId = productId;
		this.price = price;
		this.img = img;
		this.title = title;
		this.titleName = titleName;
		this.preferentialPrice=preferentialPrice;
		this.productName = productName;
	}
	
	
	
}
