package com.puyuntech.ycmall.entity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * 
 * Entity - 商品分类 . Created on 2015-8-26 下午3:08:07
 * 
 * @author 施长成
 * 
 *         分类需要绑定 ： 商品参数
 * 
 */
@Entity
@Table(name = "t_product_category")
public class ProductCategory extends OrderEntity<Long> {

	private static final long serialVersionUID = 4641592383583156821L;

	/** 树路径分隔符 */
	public static final String TREE_PATH_SEPARATOR = ",";

	/** 路径前缀 */
	private static final String PATH_PREFIX = "/goods/list";

	/** 路径后缀 */
	private static final String PATH_SUFFIX = ".jhtml";

	/** 名称 */
	private String name;

	/** 页面标题 */
	private String seoTitle;

	/** 页面关键词 */
	private String seoKeywords;

	/** 页面描述 */
	private String seoDescription;

	/** 树路径 */
	private String treePath;

	/** 层级 */
	private Integer grade;

	/** 上级分类 */
	private ProductCategory parent;

	/** 下级分类 */
	private Set<ProductCategory> children = new HashSet<ProductCategory>();

	/** 是否需要自主成楼 **/
	private Boolean isFloor;

	/** 是否需要排行榜 **/
	private Boolean isRank;

	/** 参数 【参数和分类是多对一的关系】 */
	private Set<Parameter> parameters = new HashSet<Parameter>();

	/** 规格 【规格和分类是多对一的关系】 */
	private Set<Specification> specifications = new HashSet<Specification>();

	/** 关联品牌 多对多关系 */
	private Set<Brand> brands = new HashSet<Brand>();

	/** 属性 【属性和分类是多对一的关系】 */
	private Set<Attribute> attributes = new HashSet<Attribute>();

	/** 关联促销 */
	private Set<Promotion> promotions = new HashSet<Promotion>();

	/** 商品 */
	private Set<Product> product = new HashSet<Product>();

	/** 型号 */
	private Set<ProductModel> model = new HashSet<ProductModel>();

	/**
	 * 获取名称
	 * 
	 * @return 名称
	 */
	@NotEmpty
	@Length(max = 200)
	@Column(nullable = false)
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
	 * 获取页面标题
	 * 
	 * @return 页面标题
	 */
	@Length(max = 200)
	public String getSeoTitle() {
		return seoTitle;
	}

	/**
	 * 设置页面标题
	 * 
	 * @param seoTitle
	 *            页面标题
	 */
	public void setSeoTitle(String seoTitle) {
		this.seoTitle = seoTitle;
	}

	/**
	 * 获取页面关键词
	 * 
	 * @return 页面关键词
	 */
	@Length(max = 200)
	public String getSeoKeywords() {
		return seoKeywords;
	}

	/**
	 * 设置页面关键词
	 * 
	 * @param seoKeywords
	 *            页面关键词
	 */
	public void setSeoKeywords(String seoKeywords) {
		this.seoKeywords = seoKeywords;
	}

	/**
	 * 获取页面描述
	 * 
	 * @return 页面描述
	 */
	@Length(max = 200)
	public String getSeoDescription() {
		return seoDescription;
	}

	/**
	 * 设置页面描述
	 * 
	 * @param seoDescription
	 *            页面描述
	 */
	public void setSeoDescription(String seoDescription) {
		this.seoDescription = seoDescription;
	}

	/**
	 * 获取树路径
	 * 
	 * @return 树路径
	 */
	@Column(nullable = false)
	public String getTreePath() {
		return treePath;
	}

	/**
	 * 设置树路径
	 * 
	 * @param treePath
	 *            树路径
	 */
	public void setTreePath(String treePath) {
		this.treePath = treePath;
	}

	/**
	 * 获取层级
	 * 
	 * @return 层级
	 */
	@Column(nullable = false)
	public Integer getGrade() {
		return grade;
	}

	/**
	 * 设置层级
	 * 
	 * @param grade
	 *            层级
	 */
	public void setGrade(Integer grade) {
		this.grade = grade;
	}

	/**
	 * 获取上级分类
	 * 
	 * @return 上级分类
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	public ProductCategory getParent() {
		return parent;
	}

	/**
	 * 设置上级分类
	 * 
	 * @param parent
	 *            上级分类
	 */
	public void setParent(ProductCategory parent) {
		this.parent = parent;
	}

	/**
	 * 获取下级分类
	 * 
	 * @return 下级分类
	 */
	@OneToMany(mappedBy = "parent", fetch = FetchType.LAZY)
	@OrderBy("order asc")
	public Set<ProductCategory> getChildren() {
		return children;
	}

	/**
	 * 设置下级分类
	 * 
	 * @param children
	 *            下级分类
	 */
	public void setChildren(Set<ProductCategory> children) {
		this.children = children;
	}

	@Column(name = "f_is_floor")
	public Boolean getIsFloor() {
		return isFloor;
	}

	public void setIsFloor(Boolean isFloor) {
		this.isFloor = isFloor;
	}

	@Column(name = "f_is_rank")
	public Boolean getIsRank() {
		return isRank;
	}

	public void setIsRank(Boolean isRank) {
		this.isRank = isRank;
	}

	/**
	 * 获取关联品牌 t_product_category_brand 【 商品分类 - 品牌 】
	 * 
	 * @return 关联品牌
	 */
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "t_product_category_brand", joinColumns = { @JoinColumn(name = "f_product_categories", referencedColumnName = "f_id") }, inverseJoinColumns = { @JoinColumn(name = "f_brands", referencedColumnName = "f_id") })
	@OrderBy("order asc")
	public Set<Brand> getBrands() {
		return brands;
	}

	/**
	 * 设置关联品牌
	 * 
	 * @param brands
	 *            关联品牌
	 */
	public void setBrands(Set<Brand> brands) {
		this.brands = brands;
	}

	/**
	 * 获取关联促销
	 * 
	 * @return 关联促销
	 */
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "t_product_category_promotion", joinColumns = { @JoinColumn(name = "f_product_categories", referencedColumnName = "f_id") }, inverseJoinColumns = { @JoinColumn(name = "f_promotions", referencedColumnName = "f_id") })
	@OrderBy("order asc")
	public Set<Promotion> getPromotions() {
		return promotions;
	}

	/**
	 * 设置关联促销
	 * 
	 * @param promotions
	 *            关联促销
	 */
	public void setPromotions(Set<Promotion> promotions) {
		this.promotions = promotions;
	}

	/**
	 * 获取参数
	 * 
	 * @return 参数
	 */
	@OneToMany(mappedBy = "productCategory", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	@OrderBy("order asc")
	public Set<Parameter> getParameters() {
		return parameters;
	}

	/**
	 * 设置参数
	 * 
	 * @param parameters
	 *            参数
	 */
	public void setParameters(Set<Parameter> parameters) {
		this.parameters = parameters;
	}

	/**
	 * 获取属性
	 * 
	 * @return 属性
	 */
	@OneToMany(mappedBy = "productCategory", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	@OrderBy("order asc")
	public Set<Attribute> getAttributes() {
		return attributes;
	}

	/**
	 * 设置属性
	 * 
	 * @param attributes
	 *            属性
	 */
	public void setAttributes(Set<Attribute> attributes) {
		this.attributes = attributes;
	}

	/**
	 * 获取规格
	 * 
	 * @return 规格
	 */
	@OneToMany(mappedBy = "productCategory", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	@OrderBy("order asc")
	public Set<Specification> getSpecifications() {
		return specifications;
	}

	/**
	 * 设置规格
	 * 
	 * @param specifications
	 *            规格
	 */
	public void setSpecifications(Set<Specification> specifications) {
		this.specifications = specifications;
	}

	/**
	 * 获取路径
	 * 
	 * @return 路径
	 */
	@Transient
	public String getPath() {
		return getId() != null ? PATH_PREFIX + "/" + getId() + PATH_SUFFIX
				: null;
	}

	/**
	 * 获取所有上级分类ID
	 * 
	 * @return 所有上级分类ID
	 */
	@Transient
	public Long[] getParentIds() {
		String[] parentIds = StringUtils.split(getTreePath(),
				TREE_PATH_SEPARATOR);
		Long[] result = new Long[parentIds.length];
		for (int i = 0; i < parentIds.length; i++) {
			result[i] = Long.valueOf(parentIds[i]);
		}
		return result;
	}

	/**
	 * 获取所有上级分类
	 * 
	 * @return 所有上级分类
	 */
	@Transient
	public List<ProductCategory> getParents() {
		List<ProductCategory> parents = new ArrayList<ProductCategory>();
		recursiveParents(parents, this);
		return parents;
	}

	/**
	 * 递归上级分类
	 * 
	 * @param parents
	 *            上级分类
	 * @param productCategory
	 *            商品分类
	 */
	private void recursiveParents(List<ProductCategory> parents,
			ProductCategory productCategory) {
		if (productCategory == null) {
			return;
		}
		ProductCategory parent = productCategory.getParent();
		if (parent != null) {
			parents.add(0, parent);
			recursiveParents(parents, parent);
		}
	}

	/**
	 * 获取商品
	 * 
	 * @return 商品
	 */
	@OneToMany(mappedBy = "productCategory", fetch = FetchType.LAZY)
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

	/**
	 * 获取型号
	 * 
	 * @return 型号
	 */
	@OneToMany(mappedBy = "productCategory", fetch = FetchType.LAZY)
	public Set<ProductModel> getProductModel() {
		return model;
	}

	/**
	 * 设置型号
	 * 
	 * @param model
	 *            型号
	 */
	public void setProductModel(Set<ProductModel> model) {
		this.model = model;
	}

}
