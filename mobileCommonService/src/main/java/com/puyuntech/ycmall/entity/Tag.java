package com.puyuntech.ycmall.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.PreRemove;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * 
 * Entity - 标签. 
 * Created on 2015-8-26 下午4:39:46 
 * @author Liaozhen
 */
@Entity
@Table(name = "t_tag")
public class Tag extends OrderEntity<Long> {

	private static final long serialVersionUID = -2862087339060963041L;
	
	/**
	 * 类型
	 */
	public enum Type {

		/** 文章标签 */
		article,

		/** 商品标签 */
		goods
	}

	/** 名称 */
	private String name;

	/** 类型 */
	private Tag.Type type;

	/** 图标 */
	private String icon;

	/** 备注 */
	private String memo;

	/** 文章 */
	private Set<ArticleEntity> articles = new HashSet<ArticleEntity>();

	/** 商品 */
	private Set<Product> products = new HashSet<Product>();

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
	 * 获取类型
	 * 
	 * @return 类型
	 */
	@NotNull(groups = Save.class)
	@Column(name = "f_type", nullable = false, updatable = false)
	public Tag.Type getType() {
		return type;
	}

	/**
	 * 设置类型
	 * 
	 * @param type
	 *            类型
	 */
	public void setType(Tag.Type type) {
		this.type = type;
	}

	/**
	 * 获取图标
	 * 
	 * @return 图标
	 */
	@Column(name = "f_icon")
	@Length(max = 200)
	@Pattern(regexp = "^(?i)(http:\\/\\/|https:\\/\\/|\\/).*$")
	public String getIcon() {
		return icon;
	}

	/**
	 * 设置图标
	 * 
	 * @param icon
	 *            图标
	 */
	public void setIcon(String icon) {
		this.icon = icon;
	}

	/**
	 * 获取备注
	 * 
	 * @return 备注
	 */
	@Column(name = "f_memo")
	@Length(max = 200)
	public String getMemo() {
		return memo;
	}

	/**
	 * 设置备注
	 * 
	 * @param memo
	 *            备注
	 */
	public void setMemo(String memo) {
		this.memo = memo;
	}

	/**
	 * 获取文章
	 * 
	 * @return 文章
	 *  ManyToMany
	 */
	@ManyToMany(mappedBy = "tags", fetch = FetchType.LAZY)
	public Set<ArticleEntity> getArticles() {
		return articles;
	}

	/**
	 * 设置文章
	 * 
	 * @param articles
	 *            文章
	 */
	public void setArticles(Set<ArticleEntity> articles) {
		this.articles = articles;
	}

	public Tag() {
		super();
	}

	/**
	 * 获取商品
	 * 
	 * @return 商品
	 */
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "t_product_tag", joinColumns = { @JoinColumn(name = "f_tags", referencedColumnName = "f_id") }, inverseJoinColumns = { @JoinColumn(name = "f_product", referencedColumnName = "f_id") })
	public Set<Product> getProducts() {
		return products;
	}

	/**
	 * 设置商品
	 * 
	 * @param goods
	 *            商品
	 */
	public void setProducts(Set<Product> products) {
		this.products = products;
	}

	/**
	 * 删除前处理
	 *  涉及到了 货品
	 */
	@PreRemove
	public void preRemove() {
		Set<ArticleEntity> articles = getArticles();
		if (articles != null) {
			for (ArticleEntity article : articles) {
				article.getTags().remove(this);
			}
		}
		Set<Product> goodsList = getProducts();
		if (goodsList != null) {
			for (Product products : goodsList) {
				products.getTags().remove(this);
			}
		}
	}

	
}
