package com.puyuntech.ycmall.entity;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.AttributeConverter;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Converter;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.puyuntech.ycmall.BaseAttributeConverter;

/**
 * 
 * 评论 - Entity  . 
 * Created on 2015-8-27 下午2:22:08 
 * @author 施长成
 */
@Entity
@Table(name = "t_review")
public class Review extends BaseEntity<Long> {

	private static final long serialVersionUID = 4193114029718904441L;

	/** 路径前缀 */
	private static final String PATH_PREFIX = "/review/content";

	/** 路径后缀 */
	private static final String PATH_SUFFIX = ".jhtml";

	/**
	 * 类型
	 */
	public enum Type {

		/** 好评 */
		positive,

		/** 中评 */
		moderate,

		/** 差评 */
		negative
	}

	/** 评分 */
	private Integer score;

	/** 内容 */
	private String content;

	/** 是否显示 */
	private Boolean isShow;

	/** IP */
	private String ip;

	/** 会员 */
	private Member member;

	/** 商品 */
	private Product product;
	
	/** 商品型号 */
	private ProductModel productModel;
	
	/** 评论图片 */
	private List<String> images = new ArrayList<String>();
	
	/** 评论时间 */
	private Date reviewDate;
	
	/**
	 * 获取评分
	 * 
	 * @return 评分
	 */
	@NotNull
	@Min(1)
	@Max(5)
	@Column(name="f_score",nullable = false, updatable = false)
	public Integer getScore() {
		return score;
	}

	/**
	 * 设置评分
	 * 
	 * @param score
	 *            评分
	 */
	public void setScore(Integer score) {
		this.score = score;
	}

	/**
	 * 获取内容
	 * 
	 * @return 内容
	 */
	@NotEmpty
	@Length(max = 200)
	@Column(name="f_content",nullable = false, updatable = false)
	public String getContent() {
		return content;
	}

	/**
	 * 设置内容
	 * 
	 * @param content
	 *            内容
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * 获取是否显示
	 * 
	 * @return 是否显示
	 */
	@Column(name="f_is_show",nullable = false)
	public Boolean getIsShow() {
		return isShow;
	}

	/**
	 * 设置是否显示
	 * 
	 * @param isShow
	 *            是否显示
	 */
	public void setIsShow(Boolean isShow) {
		this.isShow = isShow;
	}

	/**
	 * 获取IP
	 * 
	 * @return IP
	 */
	@Column(name="f_ip",nullable = false, updatable = false)
	public String getIp() {
		return ip;
	}

	/**
	 * 设置IP
	 * 
	 * @param ip
	 *            IP
	 */
	public void setIp(String ip) {
		this.ip = ip;
	}

	/**
	 * 获取会员
	 * 
	 * @return 会员
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="f_member",updatable = false)
	public Member getMember() {
		return member;
	}

	/**
	 * 设置会员
	 * 
	 * @param member
	 *            会员
	 */
	public void setMember(Member member) {
		this.member = member;
	}

	/**
	 * 设置商品
	 * 
	 * @param product
	 *            商品
	 */
	public void setProduct(Product product) {
		this.product = product;
	}

	/**
	 * 获取商品
	 * 
	 * @return 商品
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="f_product",nullable = false, updatable = false)
	public Product getProduct() {
		return product;
	}
	
	/**
	 * 获取型号
	 * 
	 * @return 型号
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="f_product_model",nullable = false, updatable = false)
	public ProductModel getProductModel() {
		return productModel;
	}
	
	/**
	 * 设置型号
	 * 
	 * @param productModel
	 *            型号
	 */
	public void setProductModel(ProductModel productModel) {
		this.productModel = productModel;
	}

	/**
	 * 获取路径
	 * 
	 * @return 路径
	 */
	@Transient
	public String getPath() {
		return getProduct() != null && getProduct().getId() != null ? PATH_PREFIX + "/" + getProduct().getId() + PATH_SUFFIX : null;
	}

	/**
	 * 获取图片
	 * 
	 * @return 图片
	 */
	@Column(name="f_image", length = 4000)
	@Convert(converter = ImagesConverter.class)
	public List<String> getImages() {
		return images;
	}

	/**
	 * 
	 * 设置图片.
	 * 
	 * @param images 图片
	 */
	public void setImages(List<String> images) {
		this.images = images;
	}

	/**
	 * 类型转换 - 图片
	 */
	@Converter
	public static class ImagesConverter extends BaseAttributeConverter<List<String>> implements AttributeConverter<Object, String> {
	}
	
	/**
	 * 获取评论时间
	 * 
	 * @return 评论时间
	 */
	@Column(name="f_create_date",insertable= false , updatable = false)
	public Date getReviewDate() {
		return reviewDate;
	}
	
	/**
	 * 
	 * 设置评论时间.
	 * 
	 * @param reviewDate 评论时间
	 */
	public void setReviewDate(Date reviewDate) {
		this.reviewDate = reviewDate;
	}
}
