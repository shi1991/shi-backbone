package com.puyuntech.ycmall.vo;

import java.util.List;

/**
 * 
 * 商品规格相关的信息 VO ，主要用于商品详情页面 . 
 * Created on 2015-10-14 下午3:43:05
 * 
 * @author 施长成
 */
public class ProductSpecificationVO {

	/**
	 * 商品ID
	 */
	private long id;

	/**
	 * 商品编号
	 */
	private String sn;

	/**
	 * 规格值ID
	 */
	private List<Integer> specificationValueIds;

	/**
	 * 规格值
	 */
	private List<String> specifications;


	
	/**
	 * 运营商 ID
	 */
	private Long operatorId;
	
	/**
	 * 运营商 名称
	 */
	private String operatorName;

	/**
	 * 商品是否缺货
	 */
	private boolean isOutOfStock;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}
	
	public List<String> getSpecifications() {
		return specifications;
	}

	public void setSpecifications(List<String> specifications) {
		this.specifications = specifications;
	}

	
	public boolean isOutOfStock() {
		return isOutOfStock;
	}

	public void setOutOfStock(boolean isOutOfStock) {
		this.isOutOfStock = isOutOfStock;
	}

	public List<Integer> getSpecificationValueIds() {
		return specificationValueIds;
	}

	public void setSpecificationValueIds(List<Integer> specificationValueIds) {
		this.specificationValueIds = specificationValueIds;
	}

	public Long getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(Long operatorId) {
		this.operatorId = operatorId;
	}

	public String getOperatorName() {
		return operatorName;
	}

	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}
	
}
